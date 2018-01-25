package xyz.sidetrip.banutil;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.Map;

public class Config {

    private String botToken, prefix, restartCommand;
    private long modRoleId, canBanRoleId, canKickRoleId, warningRoleId,
            muteRoleId, logChannelId, serverId, ownerId;

    private IDiscordClient discord;
    private final BanUtil.Status status;

    public Config(BanUtil.Status status) {
        this.status = status;
    }

    protected void load() {
        Map<String, String> env = System.getenv();
        try {
            botToken = env.get("TOKEN");
            modRoleId = Long.parseLong(env.get("MOD_ROLE_ID"));
            canBanRoleId = Long.parseLong(env.get("CAN_BAN_ROLE_ID"));
            canKickRoleId = Long.parseLong(env.get("CAN_KICK_ROLE_ID"));
            warningRoleId = Long.parseLong(env.get("WARNING_ROLE_ID"));
            muteRoleId = Long.parseLong(env.get("MUTE_ROLE_ID"));
            logChannelId = Long.parseLong(env.get("LOG_CHANNEL_ID"));
            serverId = Long.parseLong(env.get("SERVER_ID"));
            ownerId = Long.parseLong(env.get("OWNER_ID"));
            prefix = env.getOrDefault("PREFIX", "canu");
            restartCommand = env.getOrDefault("RESTART_COMMAND", "sh run.sh");
        } catch (Exception e) {
            BanUtil.LOGGER.error(UtilDue.BIG_FLASHY_ERROR
                    + "\nBot configuration not or incorrectly set! Please check the read me!\n", e);
            status.allGood = false;
            status.lastError = e;
        }
    }


    protected boolean validate() {
        status.modRoleIncorrect = getModRole() == null;
        status.canBanRoleIncorrect = getCanBanRole() == null;
        status.canKickRoleIncorrect = getCanKickRole() == null;
        status.muteRoleIncorrect = getMuteRole() == null;
        status.warnRoleIncorrect = getWarningRole() == null;
        status.logChannelIncorrect = getLogChannel() == null;
        status.serverIncorrect = getServer() == null;
        status.ownerIncorrect = getOwner() == null;

        return !(status.modRoleIncorrect || status.canBanRoleIncorrect
                || status.canKickRoleIncorrect || status.muteRoleIncorrect
                || status.warnRoleIncorrect || status.logChannelIncorrect
                || status.serverIncorrect || status.ownerIncorrect);
    }

    public String getValidationErrors() {
        // Nothing to see here. Move along.
        String error = UtilDue.BIG_FLASHY_ERROR + "\nConfiguration not valid - errors:\n";
        if (status.modRoleIncorrect) {
            error += String.format("Mod role not found with id: %s\n", modRoleId);
        }
        if (status.canBanRoleIncorrect) {
            error += String.format("Can ban role not found with id: %s\n", canBanRoleId);
        }
        if (status.canKickRoleIncorrect) {
            error += String.format("Can kick not found with id: %s\n", canKickRoleId);
        }
        if (status.muteRoleIncorrect) {
            error += String.format("Mute role not found with id: %s\n", muteRoleId);
        }
        if (status.warnRoleIncorrect) {
            error += String.format("Warn role not found with id: %s\n", warningRoleId);
        }
        if (status.logChannelIncorrect) {
            error += String.format("Log channel not found with id: %s\n", logChannelId);
        }
        if (status.serverIncorrect) {
            error += String.format("Home server not found with id: %s\n", serverId);
        }
        if (status.ownerIncorrect) {
            error += String.format("Owner not found with id: %s\n", ownerId);
        }
        error += "Please fix these errors before starting the bot.\n";
        error += "If you're getting these errors after a Heroku deploy invite the bot to the server.\n";
        return error;
    }

    public String getToken() {
        return botToken;
    }

    public IRole getModRole() {
        return discord.getRoleByID(modRoleId);
    }

    public IRole getCanBanRole() {
        return discord.getRoleByID(canBanRoleId);
    }

    public IRole getCanKickRole() {
        return discord.getRoleByID(canKickRoleId);
    }

    public IRole getWarningRole() {
        return discord.getRoleByID(warningRoleId);
    }

    public IRole getMuteRole() {
        return discord.getRoleByID(muteRoleId);
    }

    public IChannel getLogChannel() {
        return discord.getChannelByID(logChannelId);
    }

    public IGuild getServer() {
        return discord.getGuildByID(serverId);
    }

    public IUser getOwner() {
        return discord.getUserByID(ownerId);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRestartCommand() {
        return restartCommand;
    }

    public void setClient(IDiscordClient client) {
        discord = client;
    }
}
