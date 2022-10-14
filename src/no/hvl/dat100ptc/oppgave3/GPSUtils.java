package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		
		min= da[0];
		
		
		for (double d : da) {
			if (min>d) {
				min = d;
			}
		}
		
		return min;
			


	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		
		double[] latitudes  = new double [gpspoints.length];
		
		
		for (int i = 0; i<latitudes.length; i++) {
			
			latitudes[i]= gpspoints[i].getLatitude();
		}
		

	return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		
		
		
		double[] longitudes = new double [gpspoints.length];
		
		for (int i = 0; i<longitudes.length; i++) {
			
			longitudes[i] = gpspoints[i].getLongitude();
			
		}

		
return longitudes;
	}

	private static int R = 6371000; // jordens radius
	
	

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = gpspoint1.getLatitude();
		latitude2 = gpspoint2.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		longitude2 = gpspoint2.getLongitude();

		
		
		double radlatitude1 = toRadians(latitude1);
		double radlatitude2 = toRadians(latitude2);
		
		double radlongitude1 = toRadians(longitude1);
		double radlongitude2 = toRadians(longitude2);
	
		
		double deltaLat =radlatitude2 - radlatitude1 ;
		double deltaLong = radlongitude2 - radlongitude1;
		
		double a = pow(sin(deltaLat/2),2) +(cos(radlatitude1))*(cos(radlatitude2))*pow((sin(deltaLong/2)),2);
		double c = 2 * atan2 (sqrt(a),sqrt(1-a));
		d = R * c; 

		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		secs= (gpspoint2.getTime())-(gpspoint1.getTime());
		
		speed = ((distance(gpspoint1, gpspoint2))/1000)/secs*3600;
		
		
		

		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		
		
		int hh, mm, ss;
		
		hh= secs/(60*60);
		mm = (secs%(60*60))/60;
		ss = secs%60;
		
		timestr = String.format("  %02d:%02d:%02d", hh, mm, ss); //	
		
return timestr;
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		
		
		
		str = String.format("% 10.2f", d);

		return str;
		
	}
}
