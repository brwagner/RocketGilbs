package util.behavior;

/**
 * A behavior that can be turned on and off
 */
public abstract class DynamicBehavior<T> implements IBehavior<T> {

  private boolean isActive = true;

  @Override
  public void visit(T visited) {
    if (isActive()) {
      activeBehavior(visited);
    } else {
      inactiveBehavior(visited);
    }
  }

  protected abstract void activeBehavior(T visited);

  protected abstract void inactiveBehavior(T visited);

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }
}
