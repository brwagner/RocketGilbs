package main.menu.states.main.settings;

import main.menu.MenuContext;
import main.menu.states.AMenuState;
import main.menu.states.AMenuTerminal;
import main.menu.states.main.MainMenuState;
import util.Saver;

/**
 * Created by User on 10/8/2014.
 */
public class MSettingState extends AMenuTerminal {

  int setting;

  protected MSettingState(String name, int setting) {
    super(name);
    this.setting = setting;
  }

  @Override
  public AMenuState getParent() {
    return new MainMenuState();
  }

  @Override
  protected void init(MenuContext context) {
    Saver.getInstance().setSettings(setting);
    Saver.getInstance().save();
  }
}
