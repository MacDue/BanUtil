package xyz.sidetrip.banutil.commands;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.UtilDue;
import xyz.sidetrip.banutil.events.OnCommandEvent;

public class CommandListener implements IListener<OnCommandEvent> {

	@Override
	public void handle(OnCommandEvent event) {
		IMessage message = event.getMessage();
		IChannel channel = message.getChannel();
		IUser author = message.getAuthor();

		if (message.getGuild().getLongID() != BanUtil.CONFIG.getServer()
				.getLongID()) {
			UtilDue.sendMessage(channel, "I only work for Due!");
			return;
		}

		Command command = CommandHandler.getCommand(event.getCommand());
		if (command != null) {
			RequestBuffer.request(() -> {
				try {
					if (author.getPermissionsForGuild(message.getGuild())
							.contains(Permissions.ADMINISTRATOR)
							|| command.canUse(author)) {
						command.execute(message, event.getArgs());
					} else {
						UtilDue.sendMessage(channel,
								"You cannot use that command.");
					}
				} catch (MissingPermissionsException missingPerms) {
					UtilDue.sendMessage(channel,
							":confounded: I don't have permission to do that!");
				} catch (Exception exeption) {
					UtilDue.sendMessage(channel,
							":interrobang: **Something went wrong!**");
					BanUtil.LOGGER.error(
							"Something went wrong in command '{}':",
							command.getName(), exeption);
					// exeption.printStackTrace();
				}
			});
		}
	}
}
