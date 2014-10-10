package particle;

import org.lwjgl.util.vector.Vector2f;

import gameobject.ADrawable;

/**
 * A little speck of dust or engine exhaust
 */
public abstract class AParticle extends ADrawable {

  private Vector2f vel; // Velocity
  private float decay; // Seconds particle should be alive

  // Constructs an Abstract particle object
  protected AParticle(Vector2f pos, Vector2f vel, float decay) {
    super(pos);
    this.setDecay(decay);
    this.setVel(vel);
  }

  // Updates this on every frame
  public abstract void update();

////////////////////////////////////////////////////////////////////////////////
//                        Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public Vector2f getVel() {
    return vel;
  }

  public void setVel(Vector2f vel) {
    this.vel = vel;
  }

  public float getDecay() {
    return decay;
  }

  public void setDecay(float decay) {
    this.decay = decay;
  }
}
