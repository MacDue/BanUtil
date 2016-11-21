package xyz.sidetrip;

import java.text.DecimalFormat;

public class UtilDue {
	private static final DecimalFormat numberFormatter = new DecimalFormat("#,###");
	
	public static String formateFloat(float number){
		if(number == (long) number){
			return String.format("%d",(long)number);
		}else{
			return String.format("%.2f", number);
		}
	}
	
	public static String formatNumber(long number){
		return numberFormatter.format(number);
	}
}
