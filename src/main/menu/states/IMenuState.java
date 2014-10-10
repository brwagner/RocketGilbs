package main.menu.states;

import main.menu.MenuContext;

/**
 * Created by User on 10/8/2014.
 */
public interface IMenuState {

  public void update(MenuContext context);

  public boolean isTerminal();
}
