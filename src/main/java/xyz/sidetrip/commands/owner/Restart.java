package xyz.sidetrip.commands.owner;

import sx.blah.discord.handle.obj.IMessage;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;

public class Restart extends OwnerCommand {

	public Restart() {
		super("restart");
	}

	@Override
	public void execute(IMessage context, String... args) {
		UtilDue.sendMessage(context.getChannel(), ":ferris_wheel: Restarting...");
		BanUtil.getClient().logout();
		System.exit(1);
	}

}
