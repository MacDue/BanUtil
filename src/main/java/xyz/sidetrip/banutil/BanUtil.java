package xyz.sidetrip.banutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.ShardImpl;
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent;
import sx.blah.discord.util.DiscordException;
import xyz.sidetrip.banutil.commands.CommandHandler;
import xyz.sidetrip.banutil.commands.CommandListener;
import xyz.sidetrip.banutil.commands.general.Help;
import xyz.sidetrip.banutil.commands.general.Info;
import xyz.sidetrip.banutil.commands.moderation.*;
import xyz.sidetrip.banutil.commands.owner.Restart;
import xyz.sidetrip.banutil.commands.owner.Stop;
import xyz.sidetrip.banutil.commands.wizard.WizardListener;
import xyz.sidetrip.banutil.web.BanUtilStatusPage;
import sx.blah.discord.api.internal.DiscordWS;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BanUtil implements Runnable {
    private static IDiscordClient discordClient;
    public static final Logger LOGGER = LoggerFactory.getLogger(BanUtil.class);
    public static IDiscordClient getClient() {
        return discordClient;
    }

    public static final String VERSION = BanUtil.class.getPackage().getImplementationVersion();
    // Silly
    public static final int BANNING_COLOUR = 0xFF6961;

    public static class Status {
        public volatile boolean apiError;
        public volatile boolean modRoleIncorrect;
        public volatile boolean canBanRoleIncorrect;
        public volatile boolean canKickRoleIncorrect;
        public volatile boolean muteRoleIncorrect;
        public volatile boolean warnRoleIncorrect;
        public volatile boolean logChannelIncorrect;
        public volatile boolean serverIncorrect;
        public volatile boolean ownerIncorrect;
        public volatile boolean allGood;
        public volatile boolean dead = true;
        public volatile Throwable lastError = null;
        /* fun stats */
        public volatile int bansSinceLastRestart = 0;
    }

    public static final Status STATUS = new Status();
    public static final Config CONFIG = new Config(STATUS);

    public static final String WELCOME = String.join("\n",
            "\n  ____          _   _ _    _ _______ _____ _      ",
            " |  _ \\   /\\   | \\ | | |  | |__   __|_   _| |     ",
            " | |_) | /  \\  |  \\| | |  | |  | |    | | | |     ",
            " |  _ < / /\\ \\ | . ` | |  | |  | |    | | | |     ",
            " | |_) / ____ \\| |\\  | |__| |  | |   _| |_| |____ ",
            " |____/_/    \\_\\_| \\_|\\____/   |_|  |_____|______|",
            "                                                  ",
            "   ---------- Ban them, ban them all! ----------  ",
            "   [Version = " + VERSION + "]\n");

    public static final int CONFIG_RECHECK_TIMEOUT = 10000;

    public static final long REQUIRED_PERMISSIONS = 298077382;

    public static final String REPO = "https://github.com/MacDue/BanUtil";

    /*
    BanUtil! A very simple stateless moderation bot.
    Includes a simple status page to aid with setup.
     */

    public void run() {
        try {
            CONFIG.load();
            discordClient = getClient(CONFIG.getToken());
            CONFIG.setClient(discordClient);
            discordClient.getDispatcher().registerListener(new BanUtil());
            discordClient.getDispatcher().registerListener(new CommandHandler());
            discordClient.getDispatcher().registerListener(new CommandListener());
            discordClient.getDispatcher().registerListener(new WizardListener());
            STATUS.apiError = false;
            STATUS.dead = false;
        } catch (DiscordException e) {
            STATUS.apiError = true;
            discordClient.logout();
            STATUS.dead = true;
            STATUS.lastError = e;
            LOGGER.error(String.join("\n", UtilDue.BIG_FLASHY_ERROR,
                    "Something is wrong with the client... Is your token correct?",
                    "If you're sure your token is correct your bot may be out of date.",
                    "The bot needs to restart once this is fixed.\n"), e);
        }
    }

    @EventSubscriber
    public void onLoginEvent(ShardReadyEvent event) throws InterruptedException{
        IDiscordClient client = event.getClient();
        client.changePlayingText("banning tards!");
        checkBot();
    }

    private void checkBot() {
        CONFIG.load();
        if (!CONFIG.validate()) {
            STATUS.allGood = false;
            // If the config fails there is a chance that it's because the WS died.
            List<IShard> shards = discordClient.getShards();
            DiscordWS ws = shards.size() > 0 ? ((ShardImpl)shards.get(0)).ws : null;
            if (ws == null || ws.isNotConnected()) {
                LOGGER.error("Somehow not connected to Discord... (attempting restart)");
                discordClient.logout();
                start();
                return;
            }
            LOGGER.error(CONFIG.getValidationErrors());
            LOGGER.info("Checking again in 10 seconds...");
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    checkBot();
                }

            }, CONFIG_RECHECK_TIMEOUT);
            return;
        }
        addCommands();
        LOGGER.info(WELCOME);
        STATUS.allGood = true;
    }

    private void addCommands() {
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



    public static void main(String[] args) {
        if (System.getenv().getOrDefault("ENABLE_WEB", "false").equals("true")) {
            new BanUtilStatusPage(STATUS);
        }
        start();
    }

    private static void start() {
        Thread botThread = new Thread(new BanUtil(), "BOT");
        botThread.start();
    }

}
