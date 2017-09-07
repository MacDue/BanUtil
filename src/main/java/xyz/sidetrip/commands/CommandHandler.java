package xyz.sidetrip.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.events.OnCommandEvent;

public class CommandHandler {

	private static final String KEY = BanUtil.CONFIG.getPrefix();
	private static Map<String, Command> commands = new HashMap<String, Command>();

	@EventSubscriber
	public void checkForCommand(MessageReceivedEvent event) {
		IMessage message = event.getMessage();
		IUser author = event.getMessage().getAuthor();
		String content = message.getContent();
		if (!content.toLowerCase().startsWith(KEY))
			return;
		String commandString = content.substring(KEY.length());
		if (commandString.length() == 0)
			return;
		String[] args = getArgs(commandString);
		OnCommandEvent commandEvent = new OnCommandEvent(args[0].toLowerCase(),
				Arrays.copyOfRange(args, 1, args.length), message, author);
		event.getClient().getDispatcher().dispatch(commandEvent);
	}

	public static void addCommand(Command command) {
		String commandName = command.getName().toLowerCase();
		if (commands.get(commandName) == null)
			commands.put(commandName, command);
	}

	public static Command getCommand(String commandName) {
		return commands.get(commandName);
	}

	/**
	 * Creates an string array of arguments for a DueUtil command. supports char
	 * escapes and strings in the command.
	 * 
	 * @param command
	 * @note args[0] is the command name followed by the arguments.
	 * @note This attempt to parse commands even if they contain errors.
	 *       Allowing the command to deal with the exceptions.
	 */
	private String[] getArgs(String command) {
		// flag if character should be escaped.
		boolean escaped = false;
		// true if argument should be treated as a string
		// (allow spaces).
		boolean isString = false;
		List<String> argsList = new ArrayList<String>();
		String currentArg = "";
		for (int charPos = 0; charPos <= command.length(); charPos++) {
			char currentChar;
			char nextChar = charPos + 1 < command.length()
					? command.charAt(charPos + 1)
					: ' ';
			if (charPos < command.length() && (!Character
					.isWhitespace(currentChar = command.charAt(charPos))
					|| isString)) {// Not end of command or
									// whitespace.
				if (!escaped) {
					// only none alphabetic/none whitespace characters can be
					// escaped
					if (currentChar == '\\'
							&& !(Character.isWhitespace(nextChar)
									|| Character.isAlphabetic(nextChar))) {
						escaped = true;
						continue;
					} else if (currentChar == '"') {
						// start or end a string when a double quote is found.
						isString = !isString;
						// all strings are their own argument.
						currentArg = addArg(argsList, currentArg);
						continue;
					}
				} else
					escaped = false;
				currentArg += currentChar;
			} else
				// donzo!
				// add argument at whitespace
				currentArg = addArg(argsList, currentArg);
		}
		return argsList.toArray(new String[0]);
	}

	private String addArg(List<String> argsList, String argument) {
		if (argument.length() > 0)
			argsList.add(argument);
		return "";
	}

}
