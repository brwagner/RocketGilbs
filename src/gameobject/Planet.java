package gameobject;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

/**
 * Just your ordinary Planet with an atmosphere and weight
 */
public class Planet extends AGameObject implements NonPlayer {

  private float weight; // Gravitational modifier
  private float sphere; // Sphere of influence

  // Constructor
  public Planet(float x, float y, float weight, float sphere) {
    super(new Vector2f(x, y), weight / 40);
    this.setWeight(weight);
    this.setAngle(0);
    this.setSphere(sphere);
  }

  // Draws the planet and its atmosphere
  public void draw() {
    drawPlanet();
    drawAtmosphere();
  }

  // Draws a fancy star shape for the planet
  private void drawPlanet() {
    super.draw();
    {
      glBegin(GL_TRIANGLE_FAN);
      glColor3d(0, 0, 1);
      glVertex2d(0, 0);
      glColor3d(this.getPos().getX() / 1280, this.getPos().getY() / 720, 1);
      for (int i = 0; i < 360; i += 4) {
        glVertex2d(Math.sin(i) * this.getWeight() / 20, Math.cos(i) * this.getWeight() / 20);
      }
    }
    glEnd();
  }

  // Draws a blue-green circle for the planet's atmosphere
  private void drawAtmosphere() {
    super.draw();
    glBegin(GL_TRIANGLE_FAN);
    {
      glColor3d(0, 1, 1);
      glVertex2d(0, 0);
      glColor3d(0, 0.35, 0.35);
      for (int i = 0; i < 360; i++) {
        glVertex2d(Math.sin(i) * this.getSphere(), Math.cos(i) * this.getSphere());
      }
    }
    glEnd();
  }

  // Rotate the planet
  public void update() {
    this.setAngle(this.getAngle() + 1f);
  }

////////////////////////////////////////////////////////////////////////////////
//                        Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public float getSphere() {
    return this.sphere;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

  public void setSphere(float sphere) {
    this.sphere = sphere;
  }

}