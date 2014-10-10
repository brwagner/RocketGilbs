package gameobject;

import org.lwjgl.util.vector.Vector2f;

/**
 * The abstract class which all visible objects in the game use. Consists of position, rotation,
 * and collision information. Has basic information for detecting collision and for drawing to the
 * screen.
 */
public abstract class AGameObject extends ADrawable {

  private float collRadius; // Radius of the hitbox

  // Constructor
  public AGameObject(Vector2f pos, float collRadius) {
    super(pos);
    this.setCollRadius(collRadius);
  }

  // The distance from this to another gameobject
  float distanceTo(AGameObject g) {
    Vector2f dest = new Vector2f();
    Vector2f.sub(g.getPos(), this.getPos(), dest);
    return dest.length();
  }

  // Did this collide with that?
  public boolean didCollideWith(AGameObject g) {
    return this.distanceTo(g) < getCollRadius() + g.getCollRadius();
  }

////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public float getCollRadius() {
    return collRadius;
  }

  public void setCollRadius(float collRadius) {
    this.collRadius = collRadius;
  }
}