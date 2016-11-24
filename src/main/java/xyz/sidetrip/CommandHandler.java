package xyz.sidetrip;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

public class CommandHandler {
	private final static String KEY = "d!"; // for testing

	@EventSubscriber
	public void checkForCommand(MessageReceivedEvent event) {

		String content = event.getMessage().getContent();
		if (!content.startsWith(KEY))
			return;
		try {
			String[] args = getArgs(content);
		} catch (CommandMalformedException e) {
			//hmm idk yet. Might nemove so each command can try and give it's own error.
		}

	}

	/**
	 * Creates an string array of arguments for a DueUtil command. supports char
	 * escapes and strings in the command.
	 * 
	 * @param command
	 * @note args[0] is the command name followed by the arguments.
	 */
	private String[] getArgs(String command) throws CommandMalformedException {
		System.out.println(command);
		boolean escaped = false;
		boolean breakArg = false;
		boolean isString = false;
		List<String> args = new ArrayList<String>();
		String currentArg = "";
		for (int charPos = 0; charPos <= command.length(); charPos++) {
			char currentChar;
			char nextChar = charPos + 1 < command.length()
					? command.charAt(charPos + 1)
					: ' ';
			if (charPos < command.length() && (!Character
					.isWhitespace(currentChar = command.charAt(charPos))
					|| isString) && !breakArg) {
				if (!escaped) {
					if (currentChar == '\\'
							&& !(Character.isWhitespace(nextChar)
									|| Character.isAlphabetic(nextChar))) {
						escaped = true;
						continue;
					} else if (currentChar == '"') {
						isString = !isString;
						breakArg = !isString;
						continue;
					}
				} else {
					escaped = false;
				}
				currentArg += currentChar;
			} else if (currentArg.length() > 0) {
				args.add(currentArg);
				currentArg = "";
				breakArg = false;
			}
		}
		if(isString)
			throw new CommandMalformedException("Unclosed string in command!");
		return args.toArray(new String[0]);
	}

}
