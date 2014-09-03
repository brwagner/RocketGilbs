package main;

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

import org.lwjgl.input.Keyboard;

import util.Leveler;
import util.Saver;
import util.SimpleText;

/**
 * Represents the state of the playable part of the game
 */
public class InGame {
	Saver saver; // Saves the game
	LevelState levelState; // The state of the GameObjects in the game
	int levelNumber; // Self explanatory

	// fixed planet arrays for shader
	float[] pPosX = new float[10];
	float[] pPosY = new float[10];
	float[] pRad = new float[10];

	float finalTime;

	public InGame(int level, Saver saver) {
		this.saver = saver;
		this.levelNumber = level;
		this.levelState = Leveler.getLevel(level);
		this.finalTime = 0;
	}

	void update() {
		// populate the fixed planet arrays with information to be used in the shader
		getPlanetShaderInfo();

		// update level state
		this.levelState.update();

		if (playerWin()) {
			updateFinalTime();
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				gotoNextLevel();
			}
		}
		else if (playerRedo()) {
			reset();
		}

		if (isEndOfRound()) {
			explodeGilbert();
		}
	}

	private boolean playerRedo() {
		return Keyboard.isKeyDown(Keyboard.KEY_SPACE) && this.levelState.time > 0.5f;
	}

	private boolean playerWin() {
		return this.levelState.collect.levelEnd();
	}

	private void gotoNextLevel() {
		this.saver.setCurrentLevel(this.levelNumber + 1);

		if (this.levelNumber == this.saver.getMaxLevel()) {
			this.saver.setMaxLevel(this.levelNumber + 1);
			this.saver.getTimes().add(1000f);
		}

		if (this.finalTime < this.saver.getTimes().get(this.levelNumber - 1)) {
			this.saver.getTimes().set(this.levelNumber - 1, this.finalTime);
		}

		this.saver.save();
		this.levelNumber++;
		this.reset();
	}

	private void updateFinalTime() {
		if (this.finalTime == 0) {
			this.finalTime = this.levelState.time;
		}
	}

	private void explodeGilbert() {
		levelState.gilbs.setDeathScaleFactor((float) (levelState.gilbs.getDeathScaleFactor() * 0.8));
	}

	private boolean isEndOfRound() {
		return levelState.gilbs.isKillGill() || levelState.collect.levelEnd();
	}

	void draw() {
		if (gilbertNotDead()) {
			// draw gilbert and planets
			this.levelState.draw();

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

	private boolean gilbertNotDead() {
		return this.levelState.gilbs.getDeathScaleFactor() > 0.00001;
	}
	
	void displayTime() {
		glLoadIdentity();
		glColor3d(1,1,1);
		glTranslatef(0.0f, 30.0f, 0.0f);
		glScalef(3.0f,3.0f,0.0f);
		glRotatef(180.0f,1.0f,0.0f,0.0f);
		SimpleText.drawString(String.format("Time: %.1f", this.levelState.time), 0, 0, 3);
	}

	void reset() {
		pPosX = new float[10];
		pPosY = new float[10];
		pRad = new float[10];
		this.levelState = Leveler.getLevel(levelNumber);
		this.finalTime = 0;
	}

	void getPlanetShaderInfo() {
		for (int i = 0; i < levelState.planets.size(); i++) {
			pPosX[i] = levelState.planets.get(i).getX();
			pPosY[i] = levelState.planets.get(i).getY();
			pRad[i] = levelState.planets.get(i).getSphere();
		}
	}

	void drawBest() {
		if (this.saver.getTimes().get(this.levelNumber - 1) != 1000) {
			glLoadIdentity();
			glColor3d(1, 1, 1);
			glTranslatef(600.0f, 30.0f, 0.0f);
			glScalef(3.0f, 3.0f, 0.0f);
			glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
			SimpleText.drawString(String.format("Best: %.1f", this.saver.getTimes().get(this.levelNumber - 1)), 0, 0, 3);
		}
	}

	void drawGameOver() {
		glLoadIdentity();
		glColor3d(0, 0, 0);
		glTranslatef(Game.screen.getX() / 2 - 350, Game.screen.getY() / 2, 0.0f);
		glScalef(10.0f, 10.0f, 0.0f);
		glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		SimpleText.drawString("Game Over", 0, 0, 20);

		glLoadIdentity();
		glColor3d(0, 0, 0);
		glTranslatef(Game.screen.getX() / 2 - 425, Game.screen.getY() / 2 + 200, 0.0f);
		glScalef(5.0f, 5.0f, 0.0f);
		glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		SimpleText.drawString("Press SPACE To Restart", 0, 0, 10);

		glLoadIdentity();
		glBegin(GL_QUADS);
		glColor3d(1, 0, 0);
		glVertex2i(0, 0);
		glVertex2i(0, (int) Game.screen.getY());
		glVertex2i((int) Game.screen.getX(), (int) Game.screen.getY());
		glVertex2i((int) Game.screen.getX(), 0);
		glEnd();
	}

	void drawWin() {
		glLoadIdentity();
		glColor3d(0, 0, 0);
		glTranslatef(Game.screen.getX() / 2 - 300, Game.screen.getY() / 2, 0.0f);
		glScalef(10.0f, 10.0f, 0.0f);
		glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		SimpleText.drawString("You Win", 0, 0, 20);

		glLoadIdentity();
		glColor3d(0, 0, 0);
		glTranslatef(Game.screen.getX() / 2 - 450, Game.screen.getY() / 2 + 200, 0.0f);
		glScalef(5.0f, 5.0f, 0.0f);
		glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		SimpleText.drawString("Press SPACE To Continue", 0, 0, 10);

		glLoadIdentity();
		glBegin(GL_QUADS);
		glColor3d(0, 1, 0);
		glVertex2i(0, 0);
		glVertex2i(0, (int) Game.screen.getY());
		glVertex2i((int) Game.screen.getX(), (int) Game.screen.getY());
		glVertex2i((int) Game.screen.getX(), 0);
		glEnd();
	}
}
