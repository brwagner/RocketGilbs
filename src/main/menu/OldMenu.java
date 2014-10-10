package main.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;

import main.Game;
import util.Saver;
import util.text.DrawableText;
import util.text.ExpandyBehavior;
import util.text.SimpleText;

import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * The main menu. This class is a mess and really should use some kind of state machine instead of
 * what i'm doing now.
 */
public class OldMenu {

  private ArrayList<String> currentOptions;
  private String[] menuOptions = {"Continue", "Level Select", "Settings"};
  private String[] settingsOptions = {"No Stars", "Some Stars", "More Stars", "Lots of Stars"};
  private String[] levelOptions;
  private float time;
  private int selected;
  private int menuState;
  private DrawableText drawableText;
  private DrawableText drawableText2;


  public OldMenu() {
    this.menuState = -1;
    this.selected = 0;
    this.time = 0;
    this.currentOptions = new ArrayList<>(Arrays.asList(this.menuOptions));

    this.levelOptions = new String[Saver.getInstance().getMaxLevel()];
    for (int i = 0; i < Saver.getInstance().getMaxLevel(); i++) {
      this.levelOptions[i] = "Level: " + Integer.valueOf(i + 1);
    }

    drawableText = new DrawableText("RocketGilbs",
                                    new Vector2f(
                                        Game.getGameScreen().getX() / 2 - 300,
                                        200),
                                    5);

    drawableText2 = new DrawableText("By Benjamin Rajs Wagner",
                                     new Vector2f(
                                         Game.getGameScreen().getX() / 2 - 600, 550),
                                     4, new ExpandyBehavior());
  }

  public void update() {
    this.time += 1.0f / 60.0f;

    switch (this.menuState) {
      case -1:
        this.currentOptions = new ArrayList<>(Arrays.asList(this.menuOptions));
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && this.time > 3.5) {
          this.menuState = this.selected;
          this.time = (float) Math.PI;
          this.selected = 0;
        }
        break;
      case 0:
        break;
      case 1:
        this.currentOptions = new ArrayList<>(Arrays.asList(this.levelOptions));
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && this.time > 3.5) {
          this.menuState = 0;
          Saver.getInstance().setCurrentLevel(this.selected + 1);
          Saver.getInstance().save();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
          this.menuState = -1;
        }
        break;
      case 2:
        this.currentOptions = new ArrayList<>(Arrays.asList(this.settingsOptions));
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && this.time > 3.5) {
          Saver.getInstance().setSettings(this.selected);
          Saver.getInstance().save();
          this.menuState = -1;
          this.time = (float) Math.PI;
          this.selected = 0;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
          this.menuState = -1;
        }
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && this.selected < this.currentOptions.size() - 1
        && this.time > 3.5) {
      this.selected++;
      this.time = (float) Math.PI;
    } else if (Keyboard.isKeyDown(Keyboard.KEY_UP) && this.selected > 0 && this.time > 3.5) {
      this.selected--;
      this.time = (float) Math.PI;
    }
  }

  public boolean canEnterGame() {
    return this.menuState == 0;
  }

  public void draw() {
    switch (this.menuState) {
      case -1:
        this.drawMenu();
        break;
      case 0:
        break;
      case 1:
        this.drawLevelSelect();
        break;
      case 2:
        this.drawSettings();
        break;
    }
  }

  private void drawAScreen(String title, String[] strings, int select) {
    drawableText.draw();

    for (int i = 0; i < strings.length; i++) {
      glLoadIdentity();
      glTranslatef((float) (Game.getGameScreen().getX() / 2 - 450 + (Math.floor(i / 5) * 200)),
                   300 + i % 5 * 100, 0.0f);
      glScalef(3.0f, 3.0f, 0.0f);
      glRotatef(180.0f, 1.0f, 0.0f, 0.0f);

      if (select == i) {
        glTranslatef(30f, 0f, 0f);
        glScalef((float) (Math.sin(this.time) + Math.PI / 1.5),
                 (float) (Math.sin(this.time) + Math.PI / 1.5), 0);
        glTranslatef(-30f, 0f, 0f);
        SimpleText.drawString(strings[i], 0, 0, 7);
      } else {
        SimpleText.drawString(strings[i], 0, 0, 7);
      }
    }

    Game.drawBackground();
  }

  private void drawLevelSelect() {
    this.drawAScreen("Level Select", this.levelOptions, this.selected);
  }

  private void drawMenu() {
    if (this.time > Math.PI) {
      this.drawAScreen("RocketGilbs", this.menuOptions, this.selected);
    } else {
      glLoadIdentity();
      glColor3d(1, 1, 1);

      glTranslatef(Game.getGameScreen().getX() / 2 - 300, 200, 0.0f);
      glScalef(5.0f, 5.0f, 0.0f);
      glRotatef(180.0f, 1.0f, 0.0f, 0.0f);

      SimpleText.drawString("RocketGilbs", 0, 0, (int) (Math.sin(this.time) * 11 + 9));

      /*glLoadIdentity();
      glColor3d(1, 1, 1);
      glTranslatef(Game.getGameScreen().getX() / 2 - 600, 550, 0.0f);
      glScalef(4.0f, 4.0f, 0.0f);
      glRotatef(180.0f, 1.0f, 0.0f, 0.0f);

      SimpleText.drawString("By Benjamin Rajs Wagner", 0, 0, (int) (Math.sin(this.time) * 11 + 9));*/
      drawableText2.draw();
      drawableText2.update();

      glLoadIdentity();
      glColor3d(1, 1, 1);
      glTranslatef(Game.getGameScreen().getX() / 2 - 600, 650, 0.0f);
      glScalef(4.0f, 4.0f, 0.0f);
      glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
      SimpleText.drawString("Music By Gilbert Yap", 0, 0, (int) (Math.sin(this.time) * 11 + 9));

      Game.drawBackground();
    }
  }

  private void drawSettings() {
    this.drawAScreen("Settings", this.settingsOptions, this.selected);
  }
}