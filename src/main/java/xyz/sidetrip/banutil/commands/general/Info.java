package xyz.sidetrip.banutil.commands.general;

import sx.blah.discord.handle.obj.IMessage;
import xyz.sidetrip.banutil.Emojis;
import xyz.sidetrip.banutil.UtilDue;
import xyz.sidetrip.banutil.commands.Command;

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
