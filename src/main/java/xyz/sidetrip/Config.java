package xyz.sidetrip;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class Config {

	private final String botToken, prefix, restartCommand;
	private final long modRoleId, canBanRoldId, canKickRoleId, warningRoleId,
			muteRoleId, logChannelId, serverId, ownerId;

	private IDiscordClient discord;

	public Config() {
		Properties config = openConfig();
		botToken = config.getProperty("token");
		modRoleId = Long.parseLong(config.getProperty("modRoleId"));
		canBanRoldId = Long.parseLong(config.getProperty("canBanRoldId"));
		canKickRoleId = Long.parseLong(config.getProperty("canKickRoldId"));
		warningRoleId = Long.parseLong(config.getProperty("warningRoleId"));
		muteRoleId = Long.parseLong(config.getProperty("muteRoleId"));
		logChannelId = Long.parseLong(config.getProperty("logChannelId"));
		serverId = Long.parseLong(config.getProperty("serverId"));
		ownerId = Long.parseLong(config.getProperty("ownerId"));
		prefix = config.getProperty("prefix");
		restartCommand = config.getProperty("restartCommand");
	}

	public String getToken() {
		return botToken;
	}

	public IRole getModRole() {
		return discord.getRoleByID(modRoleId);
	}

	public IRole getCanBanRole() {
		return discord.getRoleByID(canBanRoldId);
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

	private Properties openConfig() {
		Properties config = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			config.load(input);
			return config;
		} catch (IOException exception) {
			System.err.println("Failed to load config!");
			exception.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException exception) {
					System.err.println("Failed to close config!");
				}
		}
		return null;
	}
}
