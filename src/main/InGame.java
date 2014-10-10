package main;

import org.lwjgl.input.Keyboard;

import util.Leveler;
import util.Saver;
import util.text.SimpleText;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 * Represents the state of the playable part of the game
 */
public class InGame {

  LevelState levelState; // The state of the GameObjects in the game
  int levelNumber; // Self explanatory

  // Fixed planet arrays for shader
  float[] pPosX = new float[10];
  float[] pPosY = new float[10];
  float[] pRad = new float[10];

  private float finalTime;

  // Constructor
  public InGame(int level) {
    this.levelNumber = level;
    this.levelState = Leveler.getLevel(level);
    this.finalTime = 0;
  }

  // Updates the state of the game every frame
  public void update() {
    // Populate the fixed planet arrays with information to be used in the shader
    getPlanetShaderInfo();

    // Update level state
    this.levelState.update();

    // Check if the player won the game
    if (playerWin()) {
      updateFinalTime();
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
        gotoNextLevel();
      }
    }
    // Check if the player wants to reset the level
    else if (playerRedo()) {
      reset();
    }

    // Check if the explosion transition should be done
    if (isEndOfRound()) {
      explodeGilbert();
    }
  }

  // Get information from planets for shaders
  private void getPlanetShaderInfo() {
    for (int i = 0; i < levelState.planets.size(); i++) {
      pPosX[i] = levelState.planets.get(i).getPos().getX();
      pPosY[i] = levelState.planets.get(i).getPos().getY();
      pRad[i] = levelState.planets.get(i).getSphere();
    }
  }

  // Get a new levelstate from the leveler using the current levelNumber
  private void reset() {
    pPosX = new float[10];
    pPosY = new float[10];
    pRad = new float[10];
    this.levelState = Leveler.getLevel(levelNumber);
    this.finalTime = 0;
  }

  // Should the level reset?
  private boolean playerRedo() {
    return Keyboard.isKeyDown(Keyboard.KEY_SPACE) && this.levelState.time > 0.5f;
  }

  // Did the player win?
  private boolean playerWin() {
    return this.levelState.collect.levelEnd();
  }

  // Update the save file with the next level number
  private void gotoNextLevel() {
    // Increase level number
    Saver.getInstance().setCurrentLevel(this.levelNumber + 1);

    // Increase max level and set a default time if a new max has been reached
    if (this.levelNumber == Saver.getInstance().getMaxLevel()) {
      Saver.getInstance().setMaxLevel(this.levelNumber + 1);
      Saver.getInstance().getTimes().add(1000f);
    }

    // If new best time then overwrite the current best
    if (this.finalTime < Saver.getInstance().getTimes().get(this.levelNumber - 1)) {
      Saver.getInstance().getTimes().set(this.levelNumber - 1, this.finalTime);
    }

    // Save the game
    Saver.getInstance().save();

    // Change the level
    this.levelNumber++;

    // Reset the gamestate using the new level
    this.reset();
  }

  // Change the final time to the level state's current time
  private void updateFinalTime() {
    if (this.finalTime == 0) {
      this.finalTime = this.levelState.time;
    }
  }

  // Increase the explosion aura around gilbert
  private void explodeGilbert() {
    levelState.gilbs.setDeathScaleFactor((float) (levelState.gilbs.getDeathScaleFactor() * 0.8));
  }

  // Has the current play of the level has ended?
  private boolean isEndOfRound() {
    return levelState.gilbs.isKillGill() || levelState.collect.levelEnd();
  }

  // Renders the state of the game and the UI elements
  void draw() {
    if (gilbertNotExploding()) {
      // draw gilbert and planets
      this.levelState.draw();

      // draw the number of signals collected
      this.levelState.displayCollected();

      // draw the current time
      displayTime();

      // draw best time
      drawBest();

      // draw background
      Game.drawBackground();
    } else if (this.levelState.gilbs.isKillGill()) {
      // deactivate shader and draw game over screen
      glUseProgram(0);
      drawGameOver();
    } else {
      // deactivate shader and draw game win screen
      glUseProgram(0);
      drawWin();
    }
  }

  // Is Gilbert exploding?
  private boolean gilbertNotExploding() {
    return this.levelState.gilbs.getDeathScaleFactor() > 0.00001;
  }

  // Display the level's current time
  private void displayTime() {
    glLoadIdentity();
    glColor3d(1, 1, 1);
    glTranslatef(0.0f, 30.0f, 0.0f);
    glScalef(3.0f, 3.0f, 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString(String.format("Time: %.1f", this.levelState.time), 0, 0, 3);
  }

  // Display the best time completed by the player
  private void drawBest() {
    if (Saver.getInstance().getTimes().get(this.levelNumber - 1) != 1000) {
      glLoadIdentity();
      glColor3d(1, 1, 1);
      glTranslatef(600.0f, 30.0f, 0.0f);
      glScalef(3.0f, 3.0f, 0.0f);
      glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
      SimpleText.drawString(
          String.format("Best: %.1f", Saver.getInstance().getTimes().get(this.levelNumber - 1)), 0,
          0, 3);
    }
  }

  // Display the game over screen
  private void drawGameOver() {
    glLoadIdentity();
    glColor3d(0, 0, 0);
    glTranslatef(Game.getScreen().getX() / 2 - 350, Game.getScreen().getY() / 2, 0.0f);
    glScalef(10.0f, 10.0f, 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString("Game Over", 0, 0, 20);

    glLoadIdentity();
    glColor3d(0, 0, 0);
    glTranslatef(Game.getScreen().getX() / 2 - 425, Game.getScreen().getY() / 2 + 200, 0.0f);
    glScalef(5.0f, 5.0f, 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString("Press SPACE To Restart", 0, 0, 10);

    glLoadIdentity();
    glBegin(GL_QUADS);
    glColor3d(1, 0, 0);
    glVertex2i(0, 0);
    glVertex2i(0, (int) Game.getScreen().getY());
    glVertex2i((int) Game.getScreen().getX(), (int) Game.getScreen().getY());
    glVertex2i((int) Game.getScreen().getX(), 0);
    glEnd();
  }

  // Display the you win screen
  private void drawWin() {
    glLoadIdentity();
    glColor3d(0, 0, 0);
    glTranslatef(Game.getScreen().getX() / 2 - 300, Game.getScreen().getY() / 2, 0.0f);
    glScalef(10.0f, 10.0f, 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString("You Win", 0, 0, 20);

    glLoadIdentity();
    glColor3d(0, 0, 0);
    glTranslatef(Game.getScreen().getX() / 2 - 450, Game.getScreen().getY() / 2 + 200, 0.0f);
    glScalef(5.0f, 5.0f, 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString("Press SPACE To Continue", 0, 0, 10);

    glLoadIdentity();
    glBegin(GL_QUADS);
    glColor3d(0, 1, 0);
    glVertex2i(0, 0);
    glVertex2i(0, (int) Game.getScreen().getY());
    glVertex2i((int) Game.getScreen().getX(), (int) Game.getScreen().getY());
    glVertex2i((int) Game.getScreen().getX(), 0);
    glEnd();
  }
}
