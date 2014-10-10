package util.text;

import util.behavior.DynamicBehavior;

/**
 * In and out and in and out
 */
public class ExpandyBehavior extends DynamicBehavior<DrawableText> {

  private float time = 0;
  private float initialScale = -1;

  @Override
  public void visit(DrawableText visited) {
    if (initialScale == -1) {
      initialScale = visited.getScale();
    }
    super.visit(visited);
  }

  @Override
  protected void activeBehavior(DrawableText visited) {
    visited.setScale((float) (Math.sin(this.time) + 2.0) * 5);
    this.time += 1.0f / 60.0f;
  }

  @Override
  protected void inactiveBehavior(DrawableText visited) {
    visited.setScale(initialScale);
    this.time = 0;
  }
}
