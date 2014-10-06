package main;

import java.util.ArrayList;

import gameobject.Collectable;
import gameobject.Gilbert;
import gameobject.Planet;
import util.SimpleText;

import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * Represents the state of the current round of a level being played by the player
 */
public class LevelState {

  float time; // The current round time
  Gilbert gilbs; // The player object
  ArrayList<Planet> planets; // The planets
  Collectable collect; // The distress signals

  // Constructor
  public LevelState(Gilbert gilbs, ArrayList<Planet> planets, Collectable collect) {
    this.gilbs = gilbs;
    this.planets = planets;
    this.collect = collect;
    this.time = 0;
  }

  // Updates the state of the level every frame
  public void update() {
    updateGilbs();
    updatePlanets();
    updateCollectables();
    updateTime();
  }

  // Updates the game time every frame (Assuming 60fps)
  private void updateTime() {
    this.time += 1.0 / 60.0;
  }

  // Moves the collectables and checks if Gilbert collided with a collectable
  private void updateCollectables() {
    this.collect.update();
    if (this.gilbs.didCollideWith(this.collect)) {
      this.collect.moveNext();
    }
  }

  // Rotates the planets on every frame
  private void updatePlanets() {
    for (Planet planet : this.planets) {
      planet.update();
    }
  }

  // Updates Gilbert's position based on player input and planets
  private void updateGilbs() {
    if (!this.collect.levelEnd()) {
      this.gilbs.update(this.planets);
    }
  }

  // Draws the state of the levels
  public void draw() {
    this.gilbs.draw();
    this.collect.draw();
    for (Planet p : planets) {
      p.draw();
    }
  }

  // Draws how many collectables are in the level and how many have been collected already
  public void displayCollected() {
    glLoadIdentity();
    glColor3d(1, 1, 1);
    glTranslatef(300.0f, 30.0f, 0.0f);
    glScalef(3.0f, 3.0f, 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString(
        "Signals: " + (this.collect.getTotalPoints() - this.collect.getPoints().size()) + "/"
        + this.collect.getTotalPoints(), 0, 0, 3);
  }
}