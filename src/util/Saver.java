package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

/**
 * A singleton class used to save data across the application
 */
public class Saver {

	private static Saver instance = null;
	
	private int maxLevel; // Highest level ever reached by the player
	private int currentLevel; // The level the player is currently at
	private int settings; // The performance settings for the shaders
	private Vector2f screen; // The screen size
	private ArrayList<Float> times; // The best times for each level

	// Gets the singleton's instance
	public static Saver getInstance() {
		if(instance == null) {
			instance = new Saver();
		}
		return instance;
	}
	
	// Constructor
	private Saver() {
		load();
	}

	// Uses the save file to initialize variables
	public void load() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/save.txt"));
			String line = new String();
			while ((line = br.readLine()) != null) {
				if (line.equals("max")) {
					this.setMaxLevel(Integer.parseInt(br.readLine()));
				}
				if (line.equals("current")) {
					this.setCurrentLevel(Integer.parseInt(br.readLine()));
				}

				if (line.equals("settings")) {
					this.setSettings(Integer.parseInt(br.readLine()));
				}

				if (line.equals("screen")) {
					this.setScreen(new Vector2f(Float.parseFloat(br.readLine()), Float.parseFloat(br.readLine())));
				}
				if (line.equals("times")) {
					this.setTimes(new ArrayList<Float>());
					for (int i = 0; i < this.getMaxLevel(); i++) {
						br.readLine();
						this.getTimes().add(Float.parseFloat(br.readLine()));
					}
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	// Overwrites the save file with new data
	public void save() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/save.txt"));
			StringBuilder file = new StringBuilder();
			file.append("max\n" + this.getMaxLevel() + "\n");
			file.append("current\n" + this.getCurrentLevel() + "\n");
			file.append("settings\n" + this.getSettings() + "\n");
			file.append("screen\n" + this.getScreen().x + "\n" + this.getScreen().y + "\n");
			file.append("times\n");

			for (int i = 0; i < this.getTimes().size(); i++) {
				file.append("t" + i + "\n");
				file.append(this.getTimes().get(i) + "\n");
			}

			bw.write(file.toString());
			bw.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

////////////////////////////////////////////////////////////////////////////////
//                           Accessors and Mutators
////////////////////////////////////////////////////////////////////////////////
	
	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public Vector2f getScreen() {
		return screen;
	}

	public void setScreen(Vector2f screen) {
		this.screen = screen;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getSettings() {
		return settings;
	}

	public void setSettings(int settings) {
		this.settings = settings;
	}

	public ArrayList<Float> getTimes() {
		return times;
	}

	public void setTimes(ArrayList<Float> times) {
		this.times = times;
	}
}