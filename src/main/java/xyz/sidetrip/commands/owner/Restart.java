package xyz.sidetrip.commands.owner;

import java.io.IOException;

import sx.blah.discord.handle.obj.IMessage;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;

public class Restart extends OwnerCommand {

	public Restart() {
		super("restart");
	}

	@Override
	public void execute(IMessage context, String... args) {
		UtilDue.sendMessage(context.getChannel(),
				":ferris_wheel: Restarting...");
		String[] command = BanUtil.CONFIG.getRestartCommand().split(" ");
		ProcessBuilder builder = new ProcessBuilder(command);
		try {
			builder.start();
			BanUtil.getClient().logout();
			System.exit(0);
		} catch (IOException exception) {
			BanUtil.LOGGER.error("Could to restart: ", exception);
		}
	}

}
