package gameobject;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

/**
 * Our main character on this fabulous journey
 */
public class Gilbert extends AGameObject {
	private boolean killGill; // Is this dead?
	private float deathScaleFactor; // Determines how big the death explosion should be
	private Vector2f v; // Velocity
	private Vector2f a; // Acceleration

	// Constructor
	public Gilbert(float x, float y, float vx, float vy, float ax, float ay) {
		super(x, y, 10);
		this.setV(new Vector2f((float) vx, (float) vy));
		this.setA(new Vector2f((float) ax, (float) ay));
		this.setAngle(0);
		this.setKillGill(false);
		this.setDeathScaleFactor(1);
	}

	// Draws player to the screen
	public void draw() {
		super.draw();
		glBegin(GL_TRIANGLES);
		{
			glColor3d(1, 1, 0);
			glVertex2f(-10, -10);
			glVertex2f(13, 0);
			glVertex2f(-10, 10);
		}
		glEnd();
	}

	// Updates every frame
	public void update(ArrayList<Planet> planets) {
		this.updateAngle();
		this.updatePlanetForces(planets);
		this.updatePlayerInput(this.getInput());
		this.updateDeath(planets);
	}

	// Check for death
	private void updateDeath(ArrayList<Planet> planets) {
		// Check for collision with planets
		for (Planet p : planets) {
			if (this.didCollideWith(p)) {
				this.setKillGill(true);
			}
		}
		// Check if out of bounds
		if (this.isOutOfBounds()) {
			this.setKillGill(true);
		}
	}

	// Effect player acceleration based on input
	private void updatePlayerInput(float input) {
		if (!this.isKillGill()) {
			this.applyForces(input);
		}
	}

	// Effect the player's velocity based on their proximity to the planets
	private void updatePlanetForces(ArrayList<Planet> planets) {
		for (Planet p : planets) {
			Vector2f.sub(this.getV(), this.getForceOnPlanet(p), this.getV());
			if (this.didCollideWith(p)) {
				this.setKillGill(true);
			}
		}
	}

	// Set the player's angle to face their velocity vector
	private void updateAngle() {
		if (this.isPlaying()) {
			this.setAngle((float) (180 * Math.atan2(getV().getY(), getV().getX()) / Math.PI));
		} else {
			this.setAngle(this.getAngle() + 3);
		}
	}
	
	// Check if the game is still going
	private boolean isPlaying() {
		return this.getDeathScaleFactor() == 1;
	}

	// Update the players position, velocity, and acceleration
	private void applyForces(float jerk) {
		// Update acceleration based on jerk
		this.getA().setX((float) Math.cos(getAngle() * Math.PI / 180) * jerk);
		this.getA().setY((float) Math.sin(getAngle() * Math.PI / 180) * jerk);
		// Update velocity based on acceleration
		this.getV().setX(this.getV().getX() + getA().getX());
		this.getV().setY(this.getV().getY() + getA().getY());
		// Update position based on velocity
		this.getPos().setX(getPos().getX() + getV().getX());
		this.getPos().setY(getPos().getY() + getV().getY());
	}

	// Gets up or down input from user
	private float getInput() {
		float jerk = 0;
		float c = 0.01f;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
			jerk = c;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
			jerk = -c;
		}
		return jerk;
	}

	// Is this outside of the screen
	private boolean isOutOfBounds() {
		return this.getPos().getX() > 1280 || this.getPos().getX() < 0 || this.getPos().getY() > 720 || this.getPos().getY() < 0;
	}

	// Get the force that should be applied to Gilbert from a Planet
	private Vector2f getForceOnPlanet(Planet p) {
		float distX = (float) (this.getX() - p.getX());
		float distY = (float) (this.getY() - p.getY());

		float force = (float) (p.getWeight() / (Math.pow(p.distanceTo(this), 2)));

		Vector2f forceVector = new Vector2f(force * (distX / (Math.abs(distX) + Math.abs(distY))), force * (distY / (Math.abs(distX) + Math.abs(distY))));

		return forceVector;
	}

	// Determine if Gilbert is in the planet's sphere of influence
	public boolean isInSphere(Planet p) {
		return this.distanceTo(p) < getCollRadius() + p.getSphere();
	}

////////////////////////////////////////////////////////////////////////////////
//                        Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////
	
	public boolean isKillGill() {
		return killGill;
	}

	public void setKillGill(boolean killGill) {
		this.killGill = killGill;
	}

	public float getDeathScaleFactor() {
		return deathScaleFactor;
	}

	public void setDeathScaleFactor(float deathScaleFactor) {
		this.deathScaleFactor = deathScaleFactor;
	}

	public Vector2f getV() {
		return v;
	}

	public void setV(Vector2f v) {
		this.v = v;
	}

	Vector2f getA() {
		return a;
	}

	void setA(Vector2f a) {
		this.a = a;
	}
}