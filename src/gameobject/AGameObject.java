package gameobject;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import main.Game;

import org.lwjgl.util.vector.Vector2f;

/**
 * The abstract class which all visible objects in the game use.
 * Consists of position, rotation, and collision information.
 * Has basic information for detecting collision and for drawing to the 
 * screen.
 */
public abstract class AGameObject {
	private Vector2f pos; // Position
	private float collRadius; // Radius of the hitbox
	private float angle; // Angle of the object

	// Constructor
	public AGameObject(float x, float y, float collRadius) {
		this.setPos(new Vector2f(x, y));
		this.setCollRadius(collRadius);
	}

	// Draw code that needs to be placed before the displaying of a gameobject
	public void draw() {
		// Load new identity
		glLoadIdentity();
		// Stretch the object to fit the screen size specified by the player
		glScalef(Game.getScaleX(), Game.getScaleY(), Game.getScaleZ());
		// Placement
		glTranslatef(getX(), getY(), 0);
		// Rotation
		glRotatef(getAngle(), 0f, 0f, 1f);
	}

	// The distance from this to another gameobject
	float distanceTo(AGameObject g) {
		return (float) Math.sqrt(Math.pow((g.getX() - this.getX()), 2) + Math.pow((g.getY() - this.getY()), 2));
	}

	// Did this collide with that?
	public boolean didCollideWith(AGameObject g) {
		return this.distanceTo(g) < getCollRadius() + g.getCollRadius();
	}

////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////
	
	public float getX() {
		return this.getPos().x;
	}

	public float getY() {
		return this.getPos().y;
	}

	public void setX(float x) {
		this.getPos().x = x;
	}

	public void setY(float y) {
		this.getPos().y = y;
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	public float getCollRadius() {
		return collRadius;
	}

	public void setCollRadius(float collRadius) {
		this.collRadius = collRadius;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
}