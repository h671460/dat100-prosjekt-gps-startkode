package no.hvl.dat100ptc.oppgave1;

import no.hvl.dat100ptc.TODO;

public class GPSPoint {

	private int time = 0;
	private double latitude = 0;
	private double longitude = 0;
	private double elevation = 0;



	public GPSPoint(int time, double latitude, double longitude, double elevation) {

		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;

	}

	
	public int getTime() {

		return time;

	}

	public void setTime(int time) {

		this.time = time;

		

	}

	public double getLatitude() {

		return latitude;

	}

	public void setLatitude(double latitude) {

		this.latitude = latitude;

	}

	public double getLongitude() {

		return longitude;

	}

	public void setLongitude(double longitude) {

		this.longitude = longitude;

	}

	public double getElevation() {

		return elevation;

	}

	public void setElevation(double elevation) {

		this.elevation = elevation;

	}

	public String toString() {

		String str;

		str = getTime() + " (" + getLatitude() + "," + getLongitude() + ") " + getElevation() + "\n";
		return str;

		
	}
}
