package main.menu.states;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;

import main.menu.MenuContext;

/**
 * Abstract non-terminal state
 */
public abstract class AMenuState implements IMenuState {

  private String title; // title of state
  private ArrayList<AMenuState> options = new ArrayList<>(); // sub-menus
  private int selected = 0; // index of current option
  private float coolDown = 0; // seconds since last keyboard movement

  public AMenuState(String title, AMenuState... options) {
    this.setTitle(title);
    Collections.addAll(this.getOptions(), options);
  }

  @Override // Handle player input
  public void update(MenuContext context) {
    if (coolDown >= 0.3f) {
      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
        moveDown();
        coolDown = 0;
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
        moveUp();
        coolDown = 0;
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
        context.changeState(getOptions().get(getSelected()));
        coolDown = 0;
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
        context.changeState(getParent());
        coolDown = 0;
      }
    }
    coolDown += 1f / 60f;
  }

  // Gets the parent for retuning to after backspace
  public abstract AMenuState getParent();

  @Override // Is this a terminating node
  public boolean isTerminal() {
    return false;
  }

  // move down in the menu
  private void moveDown() {
    setSelected((getSelected() + 1) % getOptions().size());
  }

  // move up in the menu
  private void moveUp() {
    setSelected(getSelected() == 0 ? getOptions().size() - 1 : getSelected() - 1);
  }

////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArrayList<AMenuState> getOptions() {
    return options;
  }

  public void setOptions(ArrayList<AMenuState> options) {
    this.options = options;
  }

  public int getSelected() {
    return selected;
  }

  public void setSelected(int selected) {
    this.selected = selected;
  }
}
