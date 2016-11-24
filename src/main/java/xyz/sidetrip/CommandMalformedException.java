package xyz.sidetrip;

public class CommandMalformedException extends Exception{
	public CommandMalformedException(String message){
		super(message);
	}
}
