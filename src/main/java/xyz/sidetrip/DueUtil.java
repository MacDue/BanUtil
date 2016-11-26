package xyz.sidetrip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import xyz.sidetrip.commands.CommandHandler;
import xyz.sidetrip.commands.CommandListener;
import xyz.sidetrip.commands.wizard.WizardListener;
import xyz.sidetrip.events.EventHandler;

public class DueUtil {
	private static IDiscordClient discordClient;
	private static final String BOT_ID = "MjUxODI4NzUxOTc1ODQxNzkz.CxpFUg._maTN2y73Ke1Ce8xQXOMRUmZ3JY";
	public static final Logger LOGGER = LoggerFactory.getLogger(DueUtil.class);
	
	public static IDiscordClient getClient(){
		return discordClient;
	}
	
	public static void main(String[] args) throws DiscordException{
		discordClient = getClient(BOT_ID);	
		discordClient.getDispatcher().registerListener(new EventHandler());
		discordClient.getDispatcher().registerListener(new CommandHandler());
		discordClient.getDispatcher().registerListener(new CommandListener());
		discordClient.getDispatcher().registerListener(new WizardListener());
	}
	
	private static IDiscordClient getClient(String botToken) throws DiscordException
	{
		return new ClientBuilder().withToken(botToken).login();
	}

}
