package xyz.sidetrip.commands.general;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import xyz.sidetrip.UtilDue;
import xyz.sidetrip.commands.Command;

public class Test extends Command {

	public Test() {
		super("Test");
	}

	@Override
	public void execute(IMessage context, String... args) {
		IChannel channel = context.getChannel();
		UtilDue.sendMessage(channel, "Hello, world!");
		for (String arg: args) {
			UtilDue.sendMessage(channel, arg);
		}
	}

}
