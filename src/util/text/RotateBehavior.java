package util.text;

import util.behavior.DynamicBehavior;

/**
 * Created by User on 10/10/2014.
 */
public class RotateBehavior extends DynamicBehavior<DrawableText> {

  private float time = 0;

  @Override
  protected void activeBehavior(DrawableText visited) {
    visited.setAngle(Math.cos(time * 5));
    time += 1f / 60f;
  }

  @Override
  protected void inactiveBehavior(DrawableText visited) {
  }
}
