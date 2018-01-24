package xyz.sidetrip.banutil.commands.general;

import sx.blah.discord.handle.obj.IMessage;
import xyz.sidetrip.banutil.UtilDue;
import xyz.sidetrip.banutil.commands.Command;

public class Help extends Command {

	public Help() {
		super("help");
	}

	@Override
	public void execute(IMessage context, String... args) {
		UtilDue.sendMessage(context.getChannel(),
				"If you need help (and are a mod) ask MacDue!");
	}

}
