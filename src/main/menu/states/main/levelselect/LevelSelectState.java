package main.menu.states.main.levelselect;

import main.menu.states.AMenuState;
import main.menu.states.main.MainMenuState;
import util.Saver;

/**
 * Created by User on 10/8/2014.
 */
public class LevelSelectState extends AMenuState {

  static AMenuState[] getLevels() {
    AMenuState[] states = new AMenuState[Saver.getInstance().getMaxLevel()];
    for (int i = 0; i < states.length; i++) {
      states[i] = new MLevelSelectState("Level " + (i + 1), i + 1);
    }
    return states;
  }

  public LevelSelectState() {
    super("Level Select", getLevels());
  }

  @Override
  public AMenuState getParent() {
    return new MainMenuState();
  }
}
