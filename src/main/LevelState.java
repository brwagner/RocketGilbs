package main;

import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import gameobject.Collectable;
import gameobject.Gilbert;
import gameobject.Planet;

import java.util.ArrayList;

import util.SimpleText;

public class LevelState
{
	int score;
	float time;
	Gilbert gilbs;
	ArrayList<Planet> planets;
	Collectable collect;

	public LevelState(Gilbert gilbs, ArrayList<Planet> planets, Collectable collect)
	{
		this.gilbs = gilbs;
		this.planets = planets;
		this.collect = collect;
		this.score = 0;
		this.time = 0;
	}

	void update()
	{
		this.updateGilbs();
		this.updatePlanets();
		this.updateCollectables();
		this.updateTime();
	}

	private void updateTime() {
		this.time += 1.0/60.0;
	}

	private void updateCollectables() {
		this.collect.update();
		if(this.gilbs.didCollideWith(this.collect))
		{
			this.score++;
			this.collect.moveNext();
		}
	}

	private void updatePlanets() {
		for(Planet planet: this.planets) { planet.update(); }
	}

	private void updateGilbs() {
		if (!this.collect.levelEnd()) {
			this.gilbs.update(this.planets);
		}
	}

	void draw()
	{
		this.displayCollected();
		this.gilbs.draw();
		this.collect.draw();
		for (Planet p : planets) {
			p.draw();
		}
	}

	void displayCollected()
	{
		glLoadIdentity();
		glColor3d(1,1,1);
		glTranslatef(300.0f, 30.0f, 0.0f);
		glScalef(3.0f,3.0f,0.0f);
		glRotatef(180.0f,1.0f,0.0f,0.0f);
		SimpleText
				.drawString(
						"Signals: "
								+ (this.collect.getTotalPoints() - this.collect
										.getPoints().size()) + "/"
								+ this.collect.getTotalPoints(), 0, 0, 3);
	}
}