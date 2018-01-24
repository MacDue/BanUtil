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
            discord.logout();
            System.exit(1);
        }
    }

    protected boolean validate() {
	    boolean modRole = getModRole() != null;
        boolean canBanRole = getCanBanRole() != null;
        boolean canKickRole = getCanKickRole() != null;
	    boolean muteRole = getMuteRole() != null;
        boolean warnRole = getWarningRole() != null;
        boolean logChannel =  getLogChannel() != null;
        boolean server =  getServer() != null;
        boolean owner = getOwner() != null;
        if (modRole
                && canBanRole
                && canKickRole
                && muteRole
                && warnRole
                && logChannel
                && server
                && owner) {
            return true;
        }
        String error = UtilDue.BIG_FLASHY_ERROR +"\nConfiguration not valid - errors:\n";
        if (!modRole) {
            error += String.format("Mod role not found with id: %s\n", modRoleId);
        }
        if (!canBanRole) {
            error += String.format("Can ban role not found with id: %s\n", canBanRoleId);
        }
        if (!canKickRole) {
            error += String.format("Can kick not found with id: %s\n", canKickRoleId);
        }
        if (!muteRole) {
            error += String.format("Mute role not found with id: %s\n", muteRoleId);
        }
        if (!warnRole) {
            error += String.format("Warn role not found with id: %s\n", warningRoleId);
        }
        if (!logChannel) {
            error += String.format("Log channel not found with id: %s\n", logChannelId);
        }
        if (!server) {
            error += String.format("Home server not found with id: %s\n", serverId);
        }
        if (!owner) {
            error += String.format("Owner not found with id: %s\n", ownerId);
        }
        error += "Please fix these errors before starting the bot.\n";
        BanUtil.LOGGER.error(error);
        discord.logout();
        System.exit(1);
        return false;
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
