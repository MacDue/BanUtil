package xyz.sidetrip.banutil.events;

import sx.blah.discord.api.events.Event;
import xyz.sidetrip.banutil.commands.wizard.InputWizard;

public class WizardEndEvent extends Event {

	private final InputWizard wizard;

	public WizardEndEvent(InputWizard wizard) {
		this.wizard = wizard;
	}

	public InputWizard getWizard() {
		return wizard;
	}

}
