package main.menu.states;

import main.menu.MenuContext;

/**
 * A terminating node in the menu tree
 */
public abstract class AMenuTerminal extends AMenuState implements IMenuState {

  // Constructor
  protected AMenuTerminal(String title) {
    super(title);
  }

  @Override // runs some code and then goes back
  public void update(MenuContext context) {
    init(context);
    context.changeState(getParent());
  }

  // the code to run
  protected abstract void init(MenuContext context);

  @Override // is this a terminating node?
  public boolean isTerminal() {
    return true;
  }
}
