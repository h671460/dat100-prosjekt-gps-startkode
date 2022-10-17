package no.hvl.dat100ptc.oppgave6;

import javax.swing.JOptionPane;

import easygraphics.*;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class CycleComputer extends EasyGraphics {

	private static int SPACE = 10;
	private static int MARGIN = 20;
	
	// FIXME: take into account number of measurements / gps points
	private static int ROUTEMAPXSIZE = 800; 
	private static int ROUTEMAPYSIZE = 400;
	private static int HEIGHTSIZE = 200;
	private static int TEXTWIDTH = 200;

	private GPSComputer gpscomp;
	private GPSPoint[] gpspoints;
	
	private int N = 0;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public CycleComputer() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");

		gpscomp = new GPSComputer(filename);
		gpspoints = gpscomp.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		N = gpspoints.length; // number of gps points

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = xstep();
		ystep = ystep();
		
		
		makeWindow("Cycle Computer", 
				2 * MARGIN + ROUTEMAPXSIZE,
				2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE);
		setColor(0,0,0);
		fillRectangle(0,0,2 * MARGIN + ROUTEMAPXSIZE,
				2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE);


		bikeRoute();
		
		
		
		

	}

	
	public void bikeRoute() {
		
		double avspeed =0;
		int ybase= ROUTEMAPXSIZE-TEXTWIDTH;
		
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double lat = gpspoints[0].getLatitude() - minlat;
		double lon = gpspoints[0].getLongitude() - minlon;

		int yOld = (int) (lat * ystep());
		int xOld = (int) (lon * xstep());

		for (int i = 1; i < gpspoints.length; i++) {
			
			int sasa = 0;
			if ( sasa > (gpspoints[i].getElevation()-gpspoints[i-1].getElevation())){
				
				setColor(0,200,0);
				
			}
			else {
		setColor(0,0,200);
			}

			lat = gpspoints[i].getLatitude() - minlat;
			lon = gpspoints[i].getLongitude() - minlon;

			int y = (int) (lat * ystep());
			int x = (int) (lon * xstep());

		
			fillCircle(MARGIN + x, ybase - y, 3);
			drawLine(MARGIN + xOld, ybase - yOld, MARGIN + x, ybase - y);

			xOld = x;
			yOld = y;

			
			
			
			int hbase = HEIGHTSIZE+30;
			
			
int tid =(int) (gpspoints[i].getTime())-(gpspoints[i-1].getTime());
			
			pause(tid*1000/1000);
			
			int alt = (int) Math.floor(gpspoints[i].getElevation());
	
			
			
			
	if ( sasa > (gpspoints[i].getElevation()-gpspoints[i-1].getElevation())){
		
		setColor(255,0,0);
		
	}
	else {
setColor(0,0,200);
	}
	
	drawLine(MARGIN + 2 * i, hbase, MARGIN + 2 * i, hbase - alt);
	//ShowRoute.showStatistics();	
	
	statistics(GPSUtils.speed(gpspoints[i-1], gpspoints[i]),gpspoints[i].getTime(),gpspoints[i].getElevation());
	
			
			

		
			
			
			
			
			
			
			/*
			 * int TEXTDISTANCE = 20;
			 * 
			 * setColor(0, 0, 0); setFont("Courier", 12);
			 * 
			 * int yy = TEXTDISTANCE; String [] stats = GPSComputer.gpscomp[i].stats();
			 * 
			 * 
			 * 
			 * drawString(s, 10, yy); yy += TEXTDISTANCE;
			 * 
			 * 
			 * 
			 * 
			 */
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}


	}
	
	
	
	public void statistics (double speed, int time, double elevation) {
	
	
	
		
		setColor (0,0,0);
		fillRectangle(20,20,500,80);
	
	setColor(40,170,0);
	int ypos = 20;
	drawString("Speed: "+ GPSUtils.formatDouble(speed) + " km/t",20,ypos+=20);
	drawString("Time: "+ GPSUtils.formatTime(time) ,20,ypos+=20);
	drawString("ELevation: "+ GPSUtils.formatDouble(elevation)  + " m",20,ypos+=20);
	
	}
	

	
	

	public double xstep() {


		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = (ROUTEMAPXSIZE) / (Math.abs(maxlon - minlon));

		return xstep;
	}

	public double ystep() {

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double ystep = (ROUTEMAPYSIZE-100) / (Math.abs(maxlat - minlat));

		return ystep;
	}

}
