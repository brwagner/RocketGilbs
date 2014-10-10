package main.menu.states.main.settings;

import main.menu.states.AMenuState;
import main.menu.states.main.MainMenuState;

/**
 * Created by User on 10/9/2014.
 */
public class SettingState extends AMenuState {

  public SettingState() {
    super("Settings",
          new MSettingState("No Stars", 0),
          new MSettingState("Some Stars", 1),
          new MSettingState("More Stars", 2),
          new MSettingState("All The Stars", 3));
  }

  @Override
  public AMenuState getParent() {
    return new MainMenuState();
  }
}
