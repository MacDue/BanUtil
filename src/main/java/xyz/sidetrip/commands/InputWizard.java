package xyz.sidetrip.commands;

import org.apache.commons.lang3.math.NumberUtils;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.DueUtil;
import xyz.sidetrip.UtilDue;
import xyz.sidetrip.commands.WizardQuestion.AnswerType;
import xyz.sidetrip.events.WizardEndEvent;
/*
 * A simple wizzard to make long commands easy for new users.
 * Needs a link to what command handler it is for.
 * Needs an expire time.
 * Needs to block commands for user while in wizzard.
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
		DueUtil.getClient().getDispatcher().dispatch(new WizardEndEvent(this));
		DueUtil.getClient().getDispatcher().unregisterListener(this);
	}

	@EventSubscriber
	public void readInput(MessageReceivedEvent event) {
		IMessage message = event.getMessage();
		String content = message.getContent();
		if (message.getAuthor() == target && message.getChannel() == channel) {
			String answer = content;
			switch (questions[questionNumber].getAnswerType()) {
				case NUMBER :
					if (UtilDue.isDouble(answer)) {
						addAnswer(answer);
						return;
					}
					break;
				case STRING :
					if (answer.length() > 0) {
						addAnswer(answer);
						return;
					}
					break;
				case IMAGE :
					break;// TODO url image return check.
				case LONGNUMBER :
					if (NumberUtils.isParsable(answer)) {
						addAnswer(answer);
						return;
					}
					break;
				default :
					break;
			}
			invalidAnswer(questions[questionNumber].getAnswerType());
		}
	}

	private void invalidAnswer(AnswerType type) {
		UtilDue.sendMessage(channel, ":bangbang:**That's not a valid " + type
				+ "!**\nCould you try again?");
		askQuestion();
	}

	private void addAnswer(String answer) {
		answers[questionNumber] = answer;
		questionNumber++;
		askQuestion();
	}

	public IChannel getChannel() {
		return channel;
	}

	public IUser getTarget() {
		return target;
	}

}
