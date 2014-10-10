package main.menu.states.main.levelselect;

import main.menu.MenuContext;
import main.menu.states.AMenuState;
import main.menu.states.AMenuTerminal;
import main.menu.states.main.MainMenuState;

/**
 * Created by User on 10/9/2014.
 */
public class MLevelSelectState extends AMenuTerminal {

  int level;

  protected MLevelSelectState(String name, int level) {
    super(name);
    this.level = level;
  }

  @Override
  public AMenuState getParent() {
    return new MainMenuState();
  }

  @Override
  protected void init(MenuContext context) {
    context.setCanStart(true);
    context.setSelectedLevel(level);
  }
}
