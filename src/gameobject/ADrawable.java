package gameobject;

import org.lwjgl.util.vector.Vector2f;

import main.Game;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * Anything with a position that is drawn
 */
public abstract class ADrawable {

  private Vector2f pos; // Position
  private float angle; // Angle of the object

  // Constructor
  protected ADrawable(Vector2f pos) {
    this.pos = new Vector2f(pos);
    this.angle = 0;
  }

  // Draw code that needs to be placed before the displaying of a gameobject
  public void draw() {
    // Load new identity
    glLoadIdentity();
    // Stretch the object to fit the screen size specified by the player
    glScalef(Game.getScaleX(), Game.getScaleY(), Game.getScaleZ());
    // Placement
    glTranslatef(getPos().getX(), getPos().getY(), 0);
    // Rotation
    glRotatef(getAngle(), 0f, 0f, 1f);
  }

  ////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public Vector2f getPos() {
    return pos;
  }

  public void setPos(Vector2f pos) {
    this.pos = pos;
  }

  public float getAngle() {
    return angle;
  }

  public void setAngle(float angle) {
    this.angle = angle;
  }

  public void setAngle(double angle) {
    this.angle = (float) angle;
  }
}
