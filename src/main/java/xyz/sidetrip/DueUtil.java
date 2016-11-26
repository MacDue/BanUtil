package xyz.sidetrip;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import xyz.sidetrip.commands.CommandEventListener;
import xyz.sidetrip.commands.WizardListener;
import xyz.sidetrip.events.CommandHandler;
import xyz.sidetrip.events.EventHandler;

public class DueUtil {
	private static IDiscordClient discordClient;
	private static final String BOT_ID = "MjUxODI4NzUxOTc1ODQxNzkz.CxpFUg._maTN2y73Ke1Ce8xQXOMRUmZ3JY";
	
	public static IDiscordClient getClient(){
		return discordClient;
	}
	
	public static void main(String[] args) throws DiscordException{
		discordClient = getClient(BOT_ID);	
		discordClient.getDispatcher().registerListener(new EventHandler());
		discordClient.getDispatcher().registerListener(new CommandHandler());
		discordClient.getDispatcher().registerListener(new CommandEventListener());
		discordClient.getDispatcher().registerListener(new WizardListener());
	}
	
	private static IDiscordClient getClient(String botToken) throws DiscordException
	{
		return new ClientBuilder().withToken(botToken).login();
	}

}
