package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}

		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			double dh = ((gpspoints[i + 1].getElevation()) - gpspoints[i].getElevation());
			if (dh > 0) {
				elevation += dh;
			}

		}

		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int totalTime = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			totalTime += ((gpspoints[i + 1].getTime()) - (gpspoints[i].getTime()));

		}
		return totalTime;
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		double[] speedtabell = new double[gpspoints.length - 1];

		for (int i = 0; i < speedtabell.length; i++) {

			speedtabell[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}

		return speedtabell;

	}

	public double maxSpeed() {

		double maxspeed = 0;

		maxspeed = GPSUtils.findMax(speeds());

		return maxspeed;

	}

	public double averageSpeed() {

		double average = 0;

		double d = totalDistance();

		int t = totalTime();

		average = (d / t) * 3.6;

		return average;

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;
		if (speedmph < 10.0) {
			met = 4.0;
		} else if (speedmph < 12.0) {
			met = 6.0;
		} else if (speedmph < 14.0) {
			met = 8.0;
		} else if (speedmph < 16.0) {
			met = 10.0;
		} else if (speedmph < 20.0) {
			met = 12.0;
		} else {
			met = 16.0;
		}

		return weight * met * (secs / 3600.0);

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			double speed = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
			int time = gpspoints[i + 1].getTime() - gpspoints[i].getTime();

			double kcal = kcal(weight, time, speed);

			totalkcal += kcal;

		}

		return totalkcal;

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {
		
		

		System.out.println("==============================================");
		System.out.println("Total Time		:	" + GPSUtils.formatTime(totalTime()));
		System.out.println("Total Distance		:	" + GPSUtils.formatDouble(totalDistance() / 1000) + " km");
		System.out.println("Total Elevation		:	" + GPSUtils.formatDouble(totalElevation()) + " m");
		System.out.println("Max Speed		:	" + GPSUtils.formatDouble(maxSpeed()) + " km/t");
		System.out.println("Average Speed		:	" + GPSUtils.formatDouble(averageSpeed()) + " km/t");
		System.out.println("Energy			:	" + GPSUtils.formatDouble(totalKcal(WEIGHT) / 1000) + " kcal");
		System.out.println("==============================================");

		
		
		
	}
	
	public String[] stats() {
		
		String [] stats= new String [8];
		
		
		stats [0]="=============================================="+"\n";
		stats [1]=("Total Time     :    " + GPSUtils.formatTime(totalTime())+"\n");
		stats [2]=("Total Distance :    " + GPSUtils.formatDouble(totalDistance() / 1000) + " km")+"\n";
		stats [3]=("Total Elevation:    " + GPSUtils.formatDouble(totalElevation()) + " m")+"\n";
		stats [4]=("Max Speed      :    " + GPSUtils.formatDouble(maxSpeed()) + " km/t")+"\n";
		stats [5]=("Average Speed  :    " + GPSUtils.formatDouble(averageSpeed()) + " km/t")+"\n";
		stats [6]=("Energy         :    " + GPSUtils.formatDouble(totalKcal(WEIGHT) / 1000) + " kcal")+"\n";
		stats [7]=("==============================================");
		
		
		return stats;
	}
	
	
	  public double[] climbs() { //elevation/distance*100 
		  
		  double[]climbs =new double [gpspoints.length-1]; 
	  
		  int i = 0; 
		  for (i = 0; i<gpspoints.length-1; i++) {
	  
	   climbs[i] = (gpspoints[i + 1].getElevation() -
	  gpspoints[i].getElevation()) /
	  GPSUtils.distance(gpspoints[i],gpspoints[i+1])* 100;
	  
	  
	  
	  
	  } return climbs; // evt totalelevation/totaldistance*100 int totalClimb =
	 // (int)(totalElevation()/totalDistance()*100);
	  
	  }
	  public double maxClimb() {
	  
	
	  
	  //findmax climb[]
	  
	  return GPSUtils.findMax(climbs());
	  
	 
	  
	  }
	 
}
