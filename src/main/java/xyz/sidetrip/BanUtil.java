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

public class BanUtil {
	private static IDiscordClient discordClient;
	public static final Config CONFIG = new Config();
	public static final Logger LOGGER = LoggerFactory.getLogger(BanUtil.class);

	public static IDiscordClient getClient() {
		return discordClient;
	}

	public static void main(String[] args) throws DiscordException {
		discordClient = getClient(CONFIG.getToken());
		CONFIG.setClient(discordClient);
		discordClient.getDispatcher().registerListener(new EventHandler());
		discordClient.getDispatcher().registerListener(new CommandHandler());
		discordClient.getDispatcher().registerListener(new CommandListener());
		discordClient.getDispatcher().registerListener(new WizardListener());
		addCommands();
	}

	private static void addCommands() {
		new xyz.sidetrip.commands.general.Info();
		new xyz.sidetrip.commands.general.Help();
		new xyz.sidetrip.commands.moderation.Ban();
		new xyz.sidetrip.commands.moderation.Kick();
		new xyz.sidetrip.commands.moderation.Mute();
		new xyz.sidetrip.commands.moderation.Warn();
		new xyz.sidetrip.commands.moderation.RevokeWarn();
		new xyz.sidetrip.commands.moderation.Unmute();
		new xyz.sidetrip.commands.moderation.Unban();
		new xyz.sidetrip.commands.owner.Stop();
		new xyz.sidetrip.commands.owner.Restart();
	}

	private static IDiscordClient getClient(String botToken)
			throws DiscordException {
		return new ClientBuilder().withToken(botToken).login();
	}
}
