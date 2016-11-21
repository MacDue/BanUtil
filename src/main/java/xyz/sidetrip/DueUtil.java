package xyz.sidetrip;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class DueUtil {
	private static IDiscordClient discordClient;
	private static final String BOT_ID = "MjEyNzA3MDYzMzY3ODYwMjI1.CxTfGg.Fh7BmeQpsOwwsOy6X4LuAkHAjgk";
	
	public static void main(String[] args) throws DiscordException{
		discordClient = getClient(BOT_ID);	
	}
	
	private static IDiscordClient getClient(String botToken) throws DiscordException
	{
		return new ClientBuilder().withToken(botToken).login();
	}

}
