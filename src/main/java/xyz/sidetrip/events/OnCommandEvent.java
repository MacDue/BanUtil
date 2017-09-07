package xyz.sidetrip.events;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class OnCommandEvent extends Event {

	private final IMessage message;
	private final String command;
	private final IUser sender;
	private final String[] args;
	/**
	 * 
	 * @param command
	 * @param args
	 * @param message
	 * @param sender
	 */
	public OnCommandEvent(String command, String[] args, IMessage message,
			IUser sender) {
		this.command = command;
		this.args = args;
		this.message = message;
		this.sender = sender;
	}

	public IUser getSender() {
		return sender;
	}

	public String getCommand() {
		return command;
	}

	public IMessage getMessage() {
		return message;
	}

	public boolean matchesCommand(String command) {
		return this.command.equalsIgnoreCase(command);
	}

	public String[] getArgs() {
		return args;
	}
}
