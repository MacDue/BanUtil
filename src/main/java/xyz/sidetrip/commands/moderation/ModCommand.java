package xyz.sidetrip.commands.moderation;

import java.util.Arrays;
import java.util.Calendar;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;
import xyz.sidetrip.commands.Command;

public abstract class ModCommand extends Command{
	
	private boolean findUserGlobal = false;
	
	public ModCommand(String name) {
		super(name);
	}
	
	public ModCommand(String name, boolean findUserGlobal)
	{
		super(name);
		this.findUserGlobal = findUserGlobal;
	}
	
	protected abstract String getAction();
	protected abstract int getColour();
	protected abstract boolean performAction(IGuild server, IUser user, String reason);
	protected boolean performAction(IGuild server, long user, String reason) { return false; }

	@Override
	public boolean canUse(IUser user) {
		return UtilDue.hasRole(BanUtil.CONFIG.getServer(), user, BanUtil.CONFIG.getModRole());
	}
	
	@Override
	public void execute(IMessage context, String... args) {
		IChannel channel = context.getChannel();
		IGuild server = context.getGuild();
		
		if (args.length < 2) {
			UtilDue.sendMessage(channel, "Please give a user and reason.");
		} else {
			String userIdString = args[0].replaceAll("[^\\d]", "");
			if (userIdString.length() == 0) {
				UtilDue.sendMessage(channel, "Please give a valid user ID.");
				return;
			}
			long userID = Long.parseLong(userIdString);
			IUser user = findUserGlobal ? BanUtil.getClient().fetchUser(userID)
										: server.getUserByID(userID);

			if (user == null) {
				UtilDue.sendMessage(channel, "User not found.");
				return;
			}
			String[] extraArgs = Arrays.copyOfRange(args, 1, args.length);
			String reason = String.join(" ", extraArgs);
			String[] actionInfo = getAction().split("\\|");
			if (performAction(server, user, reason)) {
				String[] actionParts = actionInfo[1].trim().split(" ");
				UtilDue.sendMessage(channel, actionInfo[0]
											 + UtilDue.passedTense(actionParts[0])
											 + " " +actionParts[1].toLowerCase().replace("user", user.mention())+"!");
				logAction(context.getAuthor(), user, reason);
			} else {
				UtilDue.sendMessage(channel, "Could not"+actionInfo[1].toLowerCase()
											 + "! (probably already have/don't have role)");
			}
		}
	}
	
	private void logAction(IUser moderator, IUser user, String reason) {
		Calendar calendar = Calendar.getInstance();
		IChannel logChannel = BanUtil.CONFIG.getLogChannel();
	    EmbedBuilder logEmbed = new EmbedBuilder();
	    logEmbed.withTitle(getAction());
	    logEmbed.withColor(getColour());
	    String userInfo = String.format("%s#%s (%s)", user.getName(), user.getDiscriminator(), user.mention());
	    logEmbed.appendField("User",userInfo, true);
	    logEmbed.appendField("Moderator", moderator.toString(), true);
	    logEmbed.appendField("Reason", reason, false);
	    logEmbed.withFooterText(calendar.getTime().toString());
	    logChannel.sendMessage(logEmbed.build());
	}
}

