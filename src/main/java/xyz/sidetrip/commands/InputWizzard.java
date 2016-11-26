package xyz.sidetrip.commands;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.sidetrip.DueUtil;
import xyz.sidetrip.events.WizzardEndEvent;
/*
 * A simple wizzard to make long commands easy for new users.
 * Needs a link to what command handler it is for.
 * Needs an expire time.
 * Needs to block commands for user while in wizzard.
 */
public class InputWizzard{
	
	private final String[] questions;
	private final IUser target;
	private final IChannel channel;
	private String[] answers;
	private int questionNumber = 0;
	
	public InputWizzard(IChannel channel,IUser target, String[] questions){
		this.questions = questions;
		this.target = target;
		this.channel = channel;
		this.answers = new String[questions.length];
		askQuestion();
	}
	
	public String[] getAnswers(){
		return answers;
	}

	private void askQuestion(){
		if(questionNumber >= questions.length){
			endWizzard();
			return;
		}
		try {
			channel.sendMessage(questions[questionNumber]);
		} catch (MissingPermissionsException | RateLimitException
				| DiscordException e) {
			e.printStackTrace();
		}
	}
	
	private void endWizzard(){
		DueUtil.getClient().getDispatcher().dispatch(new WizzardEndEvent(this));
		DueUtil.getClient().getDispatcher().unregisterListener(this);
	}
	
	@EventSubscriber
	public void readInput(MessageReceivedEvent event){
		IMessage message = event.getMessage();
		String content = message.getContent();
		if(message.getAuthor() == target && message.getChannel() == channel){
			answers[questionNumber] = content;
			questionNumber++;
			askQuestion();
		}
	}
	
	public IChannel getChannel(){
		return channel;
	}
	
	public IUser getTarget(){
		return target;
	}

}
