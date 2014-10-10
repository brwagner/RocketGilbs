package util.text;

import org.lwjgl.util.vector.Vector2f;

import gameobject.ADrawable;
import util.behavior.DynamicBehavior;

import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;

/**
 * Text that can be drawn by the user
 */
public class DrawableText extends ADrawable {

  private float scale;
  private DynamicBehavior<DrawableText> behavior;
  private String text;
  private int size = 10;

  public DrawableText(String text, Vector2f pos, int scale) {
    super(pos);
    this.setText(text);
    this.setScale(scale);
    this.setBehavior(null);
  }

  public DrawableText(String text, Vector2f pos, int scale,
                      DynamicBehavior<DrawableText> behavior) {
    this(text, pos, scale);
    this.setBehavior(behavior);
  }

  public void update() {
    if (getBehavior() != null) {
      getBehavior().visit(this);
    }
  }

  public void draw() {
    super.draw();
    glColor3d(1, 1, 1);
    glScalef(getScale(), getScale(), 0.0f);
    glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    SimpleText.drawString(getText(), 0, 0, getSize());
  }

////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public float getScale() {
    return scale;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public DynamicBehavior<DrawableText> getBehavior() {
    return behavior;
  }

  public void setBehavior(DynamicBehavior<DrawableText> behavior) {
    this.behavior = behavior;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
