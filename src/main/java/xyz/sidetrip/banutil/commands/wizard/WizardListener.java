package xyz.sidetrip.banutil.commands.wizard;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.sidetrip.banutil.events.WizardEndEvent;

public class WizardListener implements IListener<WizardEndEvent> {

	@Override
	public void handle(WizardEndEvent event) {
		// Messy test code!
		String[] answers = event.getWizard().getAnswers();
		String testReply = "**Wizard Answers:**\n";

		int count = 0;
		for (String answer : answers) {
			testReply += "``" + answer + "``\n";
			count++;
		}
		if (count == 0)
			testReply += "None";
		try {
			event.getWizard().getChannel().sendMessage(testReply);
		} catch (MissingPermissionsException | RateLimitException
				| DiscordException e) {
			e.printStackTrace();
		}
	}

}
