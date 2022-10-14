package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {
	
	

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // posisjon for start av tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		
		
		String times = timestr.substring(11,19);
		String timet[] = times.split(":");
		
		int hr = java.lang.Integer.parseInt(timet[0]);
		int min = java.lang.Integer.parseInt(timet[1]);
		int sec = java.lang.Integer.parseInt(timet[2]);
		
		
		int secs =(hr*60*60)+(min*60)+sec;
		
		return secs;
	
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		GPSPoint gpspoint = new GPSPoint(
				toSeconds(timeStr), 
				java.lang.Double.parseDouble(latitudeStr), 
				java.lang.Double.parseDouble(longitudeStr), 
				java.lang.Double.parseDouble(elevationStr));
	
		
		return gpspoint;

		  
	}
	
}
