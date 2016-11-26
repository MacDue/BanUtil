package xyz.sidetrip;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.collections4.map.DefaultedMap;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class UtilDue {

	private static final Map<String, String> serverKeys = new DefaultedMap<String, String>(
			"!");

	private static final DecimalFormat numberFormatter = new DecimalFormat(
			"#,###");

	public static String formateFloat(float number) {
		if (number == (long) number) {
			return String.format("%d", (long) number);
		} else {
			return String.format("%.2f", number);
		}
	}

	public static String formatNumber(long number) {
		return numberFormatter.format(number);
	}

	public static String getServerKey(String serverID) {
		return serverKeys.get(serverID);
	}

	public static String getServerKey(IGuild server) {
		return serverKeys.get(server.getID());
	}
	
	public static void sendMessage(IChannel channel, String message){
		try {
			channel.sendMessage(message);
		} catch (MissingPermissionsException | RateLimitException
				| DiscordException e) {
			e.printStackTrace();
		}
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
