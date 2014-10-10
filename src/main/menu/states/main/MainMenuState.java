package main.menu.states.main;

import main.menu.states.AMenuState;
import main.menu.states.IMenuState;
import main.menu.states.main.levelselect.LevelSelectState;
import main.menu.states.main.settings.SettingState;

/**
 * Created by User on 10/8/2014.
 */
public class MainMenuState extends AMenuState implements IMenuState {

  public MainMenuState() {
    super("RocketGilbs", new ContinueState(), new LevelSelectState(), new SettingState());
  }

  @Override
  public AMenuState getParent() {
    return this;
  }
}
