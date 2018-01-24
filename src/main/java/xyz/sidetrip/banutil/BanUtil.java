package xyz.sidetrip.banutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.DiscordException;
import xyz.sidetrip.banutil.commands.CommandHandler;
import xyz.sidetrip.banutil.commands.CommandListener;
import xyz.sidetrip.banutil.commands.general.Help;
import xyz.sidetrip.banutil.commands.general.Info;
import xyz.sidetrip.banutil.commands.moderation.*;
import xyz.sidetrip.banutil.commands.owner.Restart;
import xyz.sidetrip.banutil.commands.owner.Stop;
import xyz.sidetrip.banutil.commands.wizard.WizardListener;

public class BanUtil {
	private static IDiscordClient discordClient;
    public static final Logger LOGGER = LoggerFactory.getLogger(BanUtil.class);
    public static final Config CONFIG = new Config();

	public static IDiscordClient getClient() {
		return discordClient;
	}

	public static final String VERSION = "0.1";

    private static final String WELCOME =  String.join("\n",
            "\n  ____          _   _ _    _ _______ _____ _      ",
            " |  _ \\   /\\   | \\ | | |  | |__   __|_   _| |     ",
            " | |_) | /  \\  |  \\| | |  | |  | |    | | | |     ",
            " |  _ < / /\\ \\ | . ` | |  | |  | |    | | | |     ",
            " | |_) / ____ \\| |\\  | |__| |  | |   _| |_| |____ ",
            " |____/_/    \\_\\_| \\_|\\____/   |_|  |_____|______|",
            "                                                  ",
            "   ---------- Ban them, ban them all! ----------  ",
            "   [Version = "+VERSION+"]\n");

	public static void main(String[] args) {
	    try {
            CONFIG.load();
            discordClient = getClient(CONFIG.getToken());
            CONFIG.setClient(discordClient);
            discordClient.getDispatcher().registerListener(new BanUtil());
            discordClient.getDispatcher().registerListener(new CommandHandler());
            discordClient.getDispatcher().registerListener(new CommandListener());
            discordClient.getDispatcher().registerListener(new WizardListener());

        } catch (DiscordException e) {
	        LOGGER.error(String.join("\n",UtilDue.BIG_FLASHY_ERROR,
                    "Something is wrong with the client... Is your token correct?",
                    "If you're sure your token is correct your bot may be out of date.\n"),e);
	        discordClient.logout();
	        System.exit(1);
        }
	}

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        IDiscordClient client = event.getClient();
        client.changePlayingText("banning tards!");
        CONFIG.validate();
        addCommands();
        LOGGER.info(WELCOME);
    }

	private static void addCommands() {
		new Info();
		new Help();
		new Ban();
		new Kick();
		new Mute();
		new Warn();
		new RevokeWarn();
		new Unmute();
		new Unban();
		new Stop();
		new Restart();
	}

	private static IDiscordClient getClient(String botToken)
			throws DiscordException {
		return new ClientBuilder().withToken(botToken).login();
	}
}
