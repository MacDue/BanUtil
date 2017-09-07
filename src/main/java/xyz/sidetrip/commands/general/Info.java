package xyz.sidetrip.commands.general;

import sx.blah.discord.handle.obj.IMessage;
import xyz.sidetrip.Emojis;
import xyz.sidetrip.UtilDue;
import xyz.sidetrip.commands.Command;

public class Info extends Command {

	public Info() {
		super("info");
	}

	@Override
	public void execute(IMessage context, String... args) {
		UtilDue.sendMessage(context.getChannel(),
				Emojis.BAN + " I'm **BanUtil** by MacDue#4453\n"
						+ "I have come here to chew bubblegum and ban tards..."
						+ " and I'm all out of bubblegum.");
	}

}
