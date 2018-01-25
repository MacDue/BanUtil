package xyz.sidetrip.banutil.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public abstract class Command {
	private final String name;

	public Command(String name) {
		this.name = name;
		CommandHandler.addCommand(this);
	}

	public String getName() {
		return name;
	}

	public abstract void execute(IMessage context, String... args);

	public boolean canUse(IUser user) {
		return true;
	}

	@Override
	public String toString() {
		return "[CMD_KEY] "+this.getName();
	}
}
