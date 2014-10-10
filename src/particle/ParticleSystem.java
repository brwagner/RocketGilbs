package particle;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

import gameobject.ADrawable;
import gameobject.AGameObject;

/**
 * Manages particles
 */
public class ParticleSystem extends ADrawable {

  private ArrayList<AParticle> particles;
  private boolean paused;

  private ParticleSystem(Vector2f pos) {
    super(pos);
    this.particles = new ArrayList<>();
    this.setPaused(false);
  }

  // Creates exhaust system
  public static ParticleSystem createExhaustSystemOn(AGameObject gameObject) {
    ParticleSystem ps = new ParticleSystem(gameObject.getPos());
    ps.setPos(gameObject.getPos());
    for (int i = 0; i < 100; i++) {
      ps.particles.add(new EngineFire(ps.getPos()));
    }
    return ps;
  }

  // Draws a particle system
  public void draw() {
    particles.forEach(ADrawable::draw);
  }

  // Updates the state of each particle
  public void update() {
    particles.forEach(AParticle::update);
    particles.forEach(p -> {
      if (p.getDecay() < 0 && !isPaused()) {
        p.setPos(new Vector2f(this.getPos()));
        p.setDecay((float) Math.random());
      }
    });
  }

////////////////////////////////////////////////////////////////////////////////
//                        Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public boolean isPaused() {
    return paused;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }
}