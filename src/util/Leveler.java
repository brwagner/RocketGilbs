package util;

// Copyright 2014 Benjamin Wagner using a GPL license

import java.util.ArrayList;

import gameobject.Collectable;
import gameobject.Gilbert;
import gameobject.Planet;
import main.LevelState;

/**
 * A Factory for the different LevelStates used in each level
 */
public class Leveler {

  private static final int MAX_X = 1280;
  private static final int MAX_Y = 720;
  private static final int MID_X = MAX_X / 2;
  private static final int MID_Y = MAX_Y / 2;
  private static final int EIGHTH1_X = MAX_X / 8;
  private static final int EIGHTH2_X = EIGHTH1_X * 2;
  private static final int EIGHTH3_X = EIGHTH1_X * 3;
  private static final int EIGHTH4_X = EIGHTH1_X * 4;
  private static final int EIGHTH5_X = EIGHTH1_X * 5;
  static final int EIGHTH6_X = EIGHTH1_X * 6;
  static final int EIGHTH7_X = EIGHTH1_X * 7;
  private static final int QUART1_X = MAX_X / 4;
  static final int QUART2_X = QUART1_X * 2;
  static final int QUART3_X = QUART1_X * 3;
  private static final int THIRD1_X = MAX_X / 3;
  static final int THIRD2_X = THIRD1_X * 2;
  static final int THIRD3_X = THIRD1_X * 3;
  private static final int EIGHTH1_Y = MAX_Y / 8;
  private static final int EIGHTH2_Y = EIGHTH1_Y * 2;
  private static final int EIGHTH3_Y = EIGHTH1_Y * 3;
  private static final int EIGHTH4_Y = EIGHTH1_Y * 4;
  private static final int EIGHTH5_Y = EIGHTH1_Y * 5;
  static final int EIGHTH6_Y = EIGHTH1_Y * 6;
  static final int EIGHTH7_Y = EIGHTH1_Y * 7;
  private static final int QUART1_Y = MAX_Y / 4;
  static final int QUART2_Y = QUART1_Y * 2;
  static final int QUART3_Y = QUART1_Y * 3;
  private static final int THIRD1_Y = MAX_Y / 3;
  static final int THIRD2_Y = THIRD1_Y * 2;
  static final int THIRD3_Y = THIRD1_Y * 3;

  // Returns the specified level
  public static LevelState getLevel(int level) {
    Gilbert gilbs;
    ArrayList<Planet> planetsArray;
    Collectable collect;

    switch (level) {
      case 1:
        // gilbert
        gilbs = new Gilbert(800, 600, -1, 0);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(640, 360, 1000f, 100f));

        // collectable
        collect = new Collectable(Collectable.makePoints(740, 260, 540, 460));

        return new LevelState(gilbs, planetsArray, collect);
      case 2:
        // gilbert
        gilbs = new Gilbert(620, 360, 1, 1);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(220, 360, 1000f, 100f));
        planetsArray.add(new Planet(1020, 360, 1000f, 100f));

        // collectable
        collect = new Collectable(Collectable.makePoints(150, 500, 1160, 400));

        return new LevelState(gilbs, planetsArray, collect);
      case 3:
        // gilbert
        gilbs = new Gilbert(800, 600, -1, 0);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(800, 400, 1000f, 100f));
        planetsArray.add(new Planet(500, 300, 1000f, 100f));

        // collectable
        collect = new Collectable(Collectable.makePoints(300, 300, 1000, 600));

        return new LevelState(gilbs, planetsArray, collect);
      case 4:
        // gilbert
        gilbs = new Gilbert(800, 600, 2, 0);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(900, 400, 1500f, 150f));
        planetsArray.add(new Planet(400, 200, 1000f, 100f));

        // collectable
        collect = new Collectable(Collectable.makePoints(480, 360, 740, 260, 1100, 400));

        return new LevelState(gilbs, planetsArray, collect);

      case 5:
        // gilbert
        gilbs = new Gilbert(800, 600, 2, 0);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(350, 450, 1000f, 100f));
        planetsArray.add(new Planet(650, 350, 1500f, 150f));
        planetsArray.add(new Planet(950, 250, 1000f, 100f));

        // collectable
        collect = new Collectable(Collectable.makePoints(150, 500, 820, 260));

        return new LevelState(gilbs, planetsArray, collect);
      case 6:
        // gilbert
        gilbs = new Gilbert(800, 600, 2, 0);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(150, 150, 500f, 50f));
        planetsArray.add(new Planet(250, 350, 500f, 50f));
        planetsArray.add(new Planet(350, 250, 500f, 50f));
        planetsArray.add(new Planet(450, 450, 500f, 50f));
        planetsArray.add(new Planet(550, 350, 500f, 50f));
        planetsArray.add(new Planet(650, 250, 500f, 50f));
        planetsArray.add(new Planet(750, 450, 500f, 50f));
        planetsArray.add(new Planet(850, 350, 500f, 50f));
        planetsArray.add(new Planet(950, 250, 500f, 50f));

        // collectable
        collect = new Collectable(Collectable.makePoints(350, 400, 175, 250));

        return new LevelState(gilbs, planetsArray, collect);
      case 7:
        // gilbert
        gilbs = new Gilbert(MID_X + 150, MID_Y + 10, 2, 3);

        // planets array
        planetsArray = new ArrayList<>();

        for (float t = 0.0f; t < 2.0f * Math.PI; t += Math.PI * 0.25f) {
          planetsArray.add(new Planet((float) (MID_X + THIRD1_Y * Math.sin(t)),
                                      (float) (MID_Y + THIRD1_Y * Math.cos(t)), 500.0f, 50.0f));
        }

        planetsArray.remove(0);

        // collectable
        collect =
            new Collectable(
                Collectable.makePoints(MID_X, MID_Y, EIGHTH2_X, EIGHTH2_Y, MID_X, MID_Y));

        return new LevelState(gilbs, planetsArray, collect);
      case 8:
        // gilbert
        gilbs = new Gilbert(800, 550, 2, 1);

        // planets array
        planetsArray = new ArrayList<>();
        planetsArray.add(new Planet(EIGHTH5_X, EIGHTH5_Y, 500f, 50f));
        planetsArray.add(new Planet(EIGHTH4_X, EIGHTH4_Y, 500f, 50f));
        planetsArray.add(new Planet(EIGHTH3_X, EIGHTH3_Y, 500f, 50f));
        planetsArray.add(new Planet(EIGHTH3_X, EIGHTH5_Y, 500f, 50f));
        planetsArray.add(new Planet(EIGHTH5_X, EIGHTH3_Y, 500f, 50f));
        // collectable
        collect =
            new Collectable(Collectable
                                .makePoints(EIGHTH3_X, MID_Y, MID_X, EIGHTH3_Y, EIGHTH5_X, MID_Y,
                                            MID_X, EIGHTH5_Y));

        return new LevelState(gilbs, planetsArray, collect);
      default:
        return null;
    }
  }

}
