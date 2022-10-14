package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();

		bevegeligPrikk(MARGIN + MAPYSIZE);

	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double ystep = MAPXSIZE / (Math.abs(maxlat - minlat));

		return ystep;

	}

	public void showRouteMap(int ybase) {

		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double lat = gpspoints[0].getLatitude() - minlat;
		double lon = gpspoints[0].getLongitude() - minlon;

		int yOld = (int) (lat * ystep());
		int xOld = (int) (lon * xstep());

		for (int i = 0; i < gpspoints.length; i++) {

			lat = gpspoints[i].getLatitude() - minlat;
			lon = gpspoints[i].getLongitude() - minlon;

			int y = (int) (lat * ystep());
			int x = (int) (lon * xstep());

			setColor(0, 255, 0);
			fillCircle(MARGIN + x, ybase - y, 3);
			drawLine(MARGIN + xOld, ybase - yOld, MARGIN + x, ybase - y);

			xOld = x;
			yOld = y;

		}

	}

	public void bevegeligPrikk(int ybase) {

		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double lat = gpspoints[0].getLatitude() - minlat;
		double lon = gpspoints[0].getLongitude() - minlon;

		int yOld = (int) (lat * ystep());
		int xOld = (int) (lon * xstep());

		setColor(0, 0, 255);
		int nodeId = fillCircle(MARGIN + xOld, ybase - yOld, 6);

		for (int i = 1; i < gpspoints.length; i++) {

			lat = gpspoints[i].getLatitude() - minlat;
			lon = gpspoints[i].getLongitude() - minlon;

			int y = (int) (lat * ystep());
			int x = (int) (lon * xstep());

			double deltaSpeed = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
			deltaSpeed = deltaSpeed / gpscomputer.maxSpeed();
			deltaSpeed *= 9;
			deltaSpeed += 1;

			setSpeed((int) deltaSpeed);
			move(nodeId, MARGIN + x, ybase - y);

			xOld = x;
			yOld = y;

		}

	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		setFont("Courier", 12);
		int y = TEXTDISTANCE;
		for (String s : gpscomputer.stats()) {

			drawString(s, 10, y);
			y += TEXTDISTANCE;

		}

		// TODO - START

		// throw new UnsupportedOperationException(TODO.method());

		// TODO - SLUTT;
	}

}
