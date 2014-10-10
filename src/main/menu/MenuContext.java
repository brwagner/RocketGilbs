package main.menu;

import main.menu.states.AMenuState;
import main.menu.states.IMenuState;

/**
 * The menu state machine context
 */
public class MenuContext {

  private IMenuState state;
  private boolean canStart;
  private int selectedLevel;
  private MenuStateView view;

  public MenuContext(AMenuState state) {
    this.setState(state);
    this.setCanStart(false);
    this.selectedLevel = -1;
    this.setView(new MenuStateView(state));
  }

  // set the state to the given state
  // push it to the view if it is non-terminal
  public void changeState(AMenuState state) {
    this.state = state;
    if (!state.isTerminal()) {
      this.getView().setMenuState(state);
    }
  }

  // update the state
  public void update() {
    state.update(this);
  }

  // draw the state
  public void draw() {
    view.draw();
  }

////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public IMenuState getState() {
    return state;
  }

  private void setState(AMenuState state) {
    this.state = state;
  }

  public boolean isCanStart() {
    return canStart;
  }

  public void setCanStart(boolean canStart) {
    this.canStart = canStart;
  }

  public int getSelectedLevel() {
    return selectedLevel;
  }

  public void setSelectedLevel(int selectedLevel) {
    this.selectedLevel = selectedLevel;
  }

  public MenuStateView getView() {
    return view;
  }

  public void setView(MenuStateView view) {
    this.view = view;
  }
}
