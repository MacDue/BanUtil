package xyz.sidetrip.commands.wizard;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;
import xyz.sidetrip.events.WizardEndEvent;
/*
 * A simple wizzard to make long commands easy for new users.
 * Needs a link to what command handler it is for.
 * Needs an expire time.
 * Needs to block commands for user while in wizzard.
 * Needs to have target for it's answers.
 */
public class InputWizard {

	private final WizardQuestion[] questions;
	private final IUser target;
	private final IChannel channel;
	private String[] answers;
	private int questionNumber = 0;

	public InputWizard(IChannel channel, IUser target,
			WizardQuestion[] questions) {
		this.questions = questions;
		this.target = target;
		this.channel = channel;
		this.answers = new String[questions.length];
		askQuestion();
	}

	public String[] getAnswers() {
		return answers;
	}

	private void askQuestion() {
		if (questionNumber >= questions.length) {
			endWizzard();
			return;
		}
		UtilDue.sendMessage(channel, questions[questionNumber].getQuestion());
	}

	private void endWizzard() {
		BanUtil.getClient().getDispatcher().dispatch(new WizardEndEvent(this));
		BanUtil.getClient().getDispatcher().unregisterListener(this);
	}

	@EventSubscriber
	public void readInput(MessageReceivedEvent event) {
		IMessage message = event.getMessage();
		String content = message.getContent();
		if (message.getAuthor() == target && message.getChannel() == channel) {
			String answer = content;
			if (questions[questionNumber].validAnswer(answer))
				addAnswer(answer);
			else
				invalidAnswer(answer);
		}
	}

	private void invalidAnswer(String answer) {
		UtilDue.sendMessage(channel,
				":bangbang:**" + questions[questionNumber].getError(answer));
		askQuestion();
	}

	private void addAnswer(String answer) {
		answers[questionNumber++] = answer;
		askQuestion();
	}

	public IChannel getChannel() {
		return channel;
	}

	public IUser getTarget() {
		return target;
	}

}
