package task.tracker;

public class TimeFormatter{

	public static String timeFormat(long mil){

		if(mil > 60*60*1000){
			return String.format("%01d:%02d:%02d.%02d", mil/3600000, (mil%360000)/60000, (mil%60000)/1000, mil%100);
		}
		else if(mil > 60*1000){
			return String.format("%01d:%02d.%02d", mil/60000, mil%60000/1000, mil%100);
		}
		else {
			return String.format("%01d.%02d", mil/1000, mil%100);
		}

	}
}