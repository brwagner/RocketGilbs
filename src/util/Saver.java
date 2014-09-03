package util;
// Copyright 2014 Benjamin Wagner using a GPL license

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class Saver {

	BufferedReader br;
	BufferedWriter bw;

	private int maxLevel;
	private int currentLevel;
	private int settings;
	private Vector2f screen;
	private ArrayList<Float> times;
	
	String source;

	public Saver(String source) {

		this.source = source;
		this.br = null;
		this.bw = null;

		try
		{
			this.br = new BufferedReader(new FileReader(source));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.setTimes(new ArrayList<Float>());
		
		this.load();
	}

	void load()
	{
		try{
			String line = new String();

			while ((line = this.br.readLine()) != null)
			{
				if(line.equals("max"))
				{
					this.setMaxLevel(Integer.parseInt(this.br.readLine()));
				}

				if(line.equals("current"))
				{
					this.setCurrentLevel(Integer.parseInt(this.br.readLine()));
				}

				if(line.equals("settings"))
				{
					this.setSettings(Integer.parseInt(this.br.readLine()));
				}
				
				if(line.equals("screen")) {
					this.setScreen(new Vector2f(Float.parseFloat(this.br.readLine()),Float.parseFloat(this.br.readLine())));
				}
				
				if(line.equals("times"))
				{
					for(int i = 0; i < this.getMaxLevel(); i++)
					{
						this.br.readLine();
						this.getTimes().add(Float.parseFloat(this.br.readLine()));
					}
				}
			}
			
			this.br.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void save()
	{
		try{
			this.bw = new BufferedWriter(new FileWriter(this.source));
			StringBuilder file = new StringBuilder();
			file.append("max\n" + this.getMaxLevel() + "\n");
			file.append("current\n" + this.getCurrentLevel() + "\n");
			file.append("settings\n" + this.getSettings() + "\n");
			file.append("screen\n" + this.getScreen().x + "\n" + this.getScreen().y + "\n");
			file.append("times\n");
			
			for(int i = 0; i < this.getTimes().size(); i++)
			{
				file.append("t" + i + "\n");
				file.append(this.getTimes().get(i) + "\n");
			}
			
			this.bw.write(file.toString());
			this.bw.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}

	}

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