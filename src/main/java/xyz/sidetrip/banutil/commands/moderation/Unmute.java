package xyz.sidetrip.banutil.commands.moderation;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.UtilDue;

public class Unmute extends Mute {

	public Unmute() {
		super("unmute");
	}

	@Override
	protected String getAction() {
		return ":speaker: | Unmute User";
	}

	@Override
	protected boolean performAction(IGuild server, IUser user, String reason) {
		return UtilDue.removeRole(server, user, BanUtil.CONFIG.getMuteRole());
	}

	@Override
	public String toString() {
		return super.toString().replace("adds", "removes");
	}
}
