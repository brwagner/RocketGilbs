package main.menu;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

import main.Game;
import main.menu.states.AMenuState;
import util.text.DrawableText;
import util.text.ExpandyBehavior;
import util.text.RotateBehavior;

/**
 * Draws the state of the menu
 */
public class MenuStateView {

  AMenuState menuState;
  DrawableText titleView;
  ArrayList<DrawableText> optionsView;

  public MenuStateView(AMenuState menuState) {
    setMenuState(menuState);
  }

  public void setMenuState(AMenuState menuState) {
    this.menuState = menuState;
    Vector2f pos = new Vector2f(Game.getGameScreen().getX() / 2 - 500, 150);
    this.titleView = new DrawableText(menuState.getTitle(), pos, 10, new RotateBehavior());
    initOptionsView();
  }

  private void initOptionsView() {
    this.optionsView = new ArrayList<>();
    for (int i = 0; i < this.menuState.getOptions().size(); i++) {
      AMenuState option = this.menuState.getOptions().get(i);
      Vector2f p = new Vector2f(
          (float) (Game.getGameScreen().getX() / 2 - 450 + (Math.floor(i / 5) * 500)),
          300 + i % 5 * 100);
      DrawableText text = new DrawableText(option.getTitle(), p, 7, new ExpandyBehavior());
      text.getBehavior().setActive(false);
      optionsView.add(text);
    }
  }

  public void draw() {
    titleView.draw();
    titleView.update();
    optionsView.forEach(DrawableText::draw);
    optionsView.forEach(DrawableText::update);
    optionsView.forEach(option -> option.getBehavior().setActive(false));
    optionsView.get(menuState.getSelected()).getBehavior().setActive(true);
  }
}
