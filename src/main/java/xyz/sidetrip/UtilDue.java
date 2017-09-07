package xyz.sidetrip;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.DefaultedMap;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.DiscordClientImpl;
import sx.blah.discord.api.internal.DiscordEndpoints;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.PermissionUtils;
import sx.blah.discord.util.RequestBuffer;

public class UtilDue {

	private static final Map<Long, String> serverKeys = new DefaultedMap<Long, String>(
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
		return serverKeys.get(server.getLongID());
	}

	public static IMessage sendMessage(IChannel channel, String message) {
		RequestBuffer.RequestFuture<IMessage> future = RequestBuffer
				.request(() -> {
					try {
						return channel.sendMessage(message);
					} catch (MissingPermissionsException e) {
						BanUtil.LOGGER
								.error("Something has gone horribly wrong!", e);
					} catch (DiscordException e) {
						return sendMessage(channel, message);
					}
					return null;
				});
		return future.get();
	}

	public static boolean hasRole(IGuild server, IUser user, IRole role) {
		List<IRole> roles = user.getRolesForGuild(server);
		for (IRole userRole : roles) {
			if (userRole.getLongID() == role.getLongID()) {
				return true;
			}
		}
		return false;
	}

	public static boolean addRole(IGuild server, IUser user, IRole role) {
		if (!hasRole(server, user, role)) {
			IDiscordClient client = BanUtil.getClient();
			PermissionUtils.requireHierarchicalPermissions(server,
					client.getOurUser(), user, Permissions.MANAGE_ROLES);
			((DiscordClientImpl) client).REQUESTS.PUT
					.makeRequest(DiscordEndpoints.GUILDS + server.getStringID()
							+ "/members/" + user.getStringID() + "/roles/"
							+ role.getStringID());
			return true;
		}
		return false;
	}

	public static boolean removeRole(IGuild server, IUser user, IRole role) {
		if (hasRole(server, user, role)) {
			IDiscordClient client = BanUtil.getClient();
			PermissionUtils.requireHierarchicalPermissions(server,
					client.getOurUser(), user, Permissions.MANAGE_ROLES);
			((DiscordClientImpl) client).REQUESTS.DELETE
					.makeRequest(DiscordEndpoints.GUILDS + server.getStringID()
							+ "/members/" + user.getStringID() + "/roles/"
							+ role.getStringID());
			return true;
		}
		return false;
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String passedTense(String str) {
		if (str.endsWith("e"))
			return str + "d";
		else
			return str + "ed";
	}

	public static boolean userBannedOnServer(IGuild server, IUser user) {
		for (IUser bannedUser : server.getBannedUsers()) {
			if (bannedUser.getLongID() == user.getLongID())
				return true;
		}
		return false;
	}
}
