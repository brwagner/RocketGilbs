package main.menu.states.main;

import main.menu.MenuContext;
import main.menu.states.AMenuState;
import main.menu.states.AMenuTerminal;
import util.Saver;

/**
 * Created by User on 10/8/2014.
 */
public class ContinueState extends AMenuTerminal {

  protected ContinueState() {
    super("Continue");
  }

  @Override
  protected void init(MenuContext context) {
    context.setCanStart(true);
    context.setSelectedLevel(Saver.getInstance().getCurrentLevel());
  }

  @Override
  public AMenuState getParent() {
    return new MainMenuState();
  }
}
