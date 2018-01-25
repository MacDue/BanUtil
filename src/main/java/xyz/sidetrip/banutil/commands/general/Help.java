package xyz.sidetrip.banutil.commands.general;

import sx.blah.discord.Discord4J;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.Emojis;
import xyz.sidetrip.banutil.UtilDue;
import xyz.sidetrip.banutil.commands.Command;
import xyz.sidetrip.banutil.commands.CommandHandler;

public class Help extends Command {

	public Help() {
		super("help");
	}

	@Override
	public void execute(IMessage context, String... args) {
		EmbedBuilder help = new EmbedBuilder();
		help.withTitle(Emojis.BAN + " | BanUtil | Help");
		help.withColor(BanUtil.BANNING_COLOUR);
		help.withDescription("Here's all the commands right now:");
		StringBuilder commandList = new StringBuilder("```");
		for (Command command: CommandHandler.getCommands().values()) {
			commandList.append((command+"\n").replace("[CMD_KEY]", CommandHandler.KEY));
		}
		commandList.append("```");
		help.appendField("Command list", commandList.toString(), false);
		context.getChannel().sendMessage(help.build());
	}

	@Override
	public String toString() {
		return super.toString()+" - shows bot help";
	}
}
