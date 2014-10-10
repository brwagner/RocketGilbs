package particle;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Gilbert's rocket exhaust
 */
public class EngineFire extends AParticle {

  public EngineFire(Vector2f pos) {
    super(pos, new Vector2f((float) Math.random() - 0.5f, (float) Math.random() - 0.5f),
          (float) Math.random());
  }

  @Override
  public void update() {
    Vector2f.add(this.getPos(), this.getVel(), this.getPos());
    this.setDecay(this.getDecay() - 1f / 60f);
  }

  @Override
  public void draw() {
    if (this.getDecay() > 0) {
      super.draw();
      glBegin(GL_QUADS);
      {
        glColor3d(1, 1, Math.random());
        glVertex2f(-1, 1);
        glVertex2f(1, 1);
        glVertex2f(1, -1);
        glVertex2f(-1, -1);
      }
      glEnd();
    }
  }
}
