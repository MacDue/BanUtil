package xyz.sidetrip;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class OnCommandEvent {

	private final IMessage message;
	private final String command;
	private final IUser sender;
	private final String[] args;
	
	public OnCommandEvent(String command, String[] args, IMessage message, IUser sender){
		this.command = command;
		this.args = args;
		this.message = message;
		this.sender = sender;
	}

	public IUser getSender(){
		return sender;
	}
	
	public IMessage getMessage(){
		return message;
	}
	
	public boolean matchesCommand(String command){
		return this.command.equalsIgnoreCase(command);
	}
	
	public String[] getArgs(){
		return args;
	}
}
