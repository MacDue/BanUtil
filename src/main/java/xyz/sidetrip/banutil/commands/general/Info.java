package xyz.sidetrip.banutil.commands.general;

import sx.blah.discord.Discord4J;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.Emojis;
import xyz.sidetrip.banutil.UtilDue;
import xyz.sidetrip.banutil.commands.Command;

import java.util.Calendar;

public class Info extends Command {

    public Info() {
        super("info");
    }

    @Override
    public void execute(IMessage context, String... args) {
        EmbedBuilder info = new EmbedBuilder();
        info.withTitle(Emojis.BAN + " | BanUtil");
        info.withColor(BanUtil.BANNING_COLOUR);
        info.withDescription("I'm **BanUtil** (a stateless single server moderation bot) by MacDue#4453\n"
                + "I have come here to chew bubblegum and ban tards..."
                + " and I'm all out of bubblegum.");
        info.appendField("BanUtil version", BanUtil.VERSION, true);
        info.appendField("Discord4J version", Discord4J.VERSION, true);
        info.appendField("Deploy in a click to Heroku!", "https://github.com/MacDue/BanUtil", false);
        info.withThumbnail(BanUtil.getClient().getApplicationIconURL());
        context.getChannel().sendMessage(info.build());
    }

    @Override
    public String toString() {
        return super.toString()+" - shows bot info";
    }

}
