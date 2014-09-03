package gameobject;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.LinkedList;
import java.util.Queue;

import org.lwjgl.util.vector.Vector2f;

/**
 * A GameObject which the player collects in order to get to the next level
 */
public class Collectable extends AGameObject implements NonPlayer {
	private Queue<Vector2f> points; // A Queue of positions for distress signals
	private int totalPoints; // The number of points in the level

	// Constructor
	public Collectable(Queue<Vector2f> q) {
		// sets the x and y coordinates to the first element of the Queue
		super(q.peek().getX(), q.peek().getY(), 20);
		this.setPoints(q);
		this.setAngle(0);
		this.setTotalPoints(this.getPoints().size());
	}

	// Draws active signal to the screen
	public void draw() {
		super.draw();
		glBegin(GL_QUADS); {
			glColor3d(1, 0.5, 0.0);
			glVertex2f(-10, 10);
			glVertex2f(10, 10);
			glVertex2f(10, -10);
			glVertex2f(-10, -10);
		}
		glEnd();
	}

	// Updates every frame
	public void update() {
		// Rotate the active signal
		this.setAngle(getAngle() + 2);
	}

	// Goto the next signal
	public void moveNext() {
		this.getPoints().poll();
		if (!this.levelEnd()) {
			this.setPos(new Vector2f(this.getPoints().peek().getX(), this.getPoints().peek().getY()));
		}
	}

	// Do we have any more signals?
	public boolean levelEnd() {
		return this.getPoints().peek() == null;
	}

	// Easy way to make a bunch of points in level design
	public static Queue<Vector2f> makePoints(int... x) {
		Queue<Vector2f> q = new LinkedList<Vector2f>();
		for (int i = 0; i < x.length; i += 2) {
			q.add(new Vector2f(x[i], x[i + 1]));
		}
		return q;
	}

////////////////////////////////////////////////////////////////////////////////
//                         Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////
	
	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Queue<Vector2f> getPoints() {
		return points;
	}

	public void setPoints(Queue<Vector2f> points) {
		this.points = points;
	}
}
