package util;
// Copyright 2014 Benjamin Wagner using a GPL license

public class Settings {
	int iterations;
	int volsteps;
	float stepsize;

	Settings(int setting) {

		switch(setting)
		{
		case 0:
			this.iterations = 0;
			this.volsteps = 0;
			this.stepsize = 0;
			break;
		case 1:
			this.iterations = 6;
			this.volsteps = 6;
			this.stepsize = 0.3f;
			break;
		case 2:
			this.iterations = 10;
			this.volsteps = 5;
			this.stepsize = 0.2f;
			break;
		case 3:
			this.iterations = 10;
			this.volsteps = 9;
			this.stepsize = 0.175f;
			break;
		case 4:
			this.iterations = 13;
			this.volsteps = 11;
			this.stepsize = 0.15f;
			break;
		default:
			this.iterations = 13;
			this.volsteps = 11;
			this.stepsize = 0.15f;
			break;
		}
	}

	public String getSettings() {
		return "#define iterations " + this.iterations + "\n" +
				"#define volsteps " + this.volsteps + "\n" +
				"#define stepsize " + this.stepsize + "\n";
	}
}
