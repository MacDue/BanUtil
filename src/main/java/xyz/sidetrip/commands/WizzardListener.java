package xyz.sidetrip.commands;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.sidetrip.events.WizzardEndEvent;

public class WizzardListener implements IListener<WizzardEndEvent> {

	@Override
	public void handle(WizzardEndEvent event) {
		//Messy test code!
		String[] answers = event.getWizzard().getAnswers();
		String testReply = "**Wizard Answers:**\n";
				
		int count = 0;
		for (String answer : answers) {
			testReply += "``" + answer + "``\n";
			count++;
		}
		if (count == 0)
			testReply += "None";
		try {
			event.getWizzard().getChannel().sendMessage(testReply);
		} catch (MissingPermissionsException | RateLimitException
				| DiscordException e) {
			e.printStackTrace();
		}
	}

}
