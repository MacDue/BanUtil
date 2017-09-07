package xyz.sidetrip.commands.moderation;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;

public class Mute extends ModCommand {

	public Mute() {
		super("mute");
	}
	
	protected Mute(String name) {
		super(name);
	}

	@Override
	protected String getAction() {
		return ":mute: | Mute User";
	}

	@Override
	protected int getColour() {
		return 0xfdfd96;
	}

	@Override
	protected boolean performAction(IGuild server, IUser user, String reason) {
		return UtilDue.addRole(server, user, BanUtil.CONFIG.getMuteRole());
	}

}
