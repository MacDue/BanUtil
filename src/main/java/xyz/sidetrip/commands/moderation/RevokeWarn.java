package xyz.sidetrip.commands.moderation;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;

public class RevokeWarn extends Warn {

	public RevokeWarn() {
		super("revokewarn");
	}

	@Override
	protected String getAction() {
		return ":clap: | Revoke Warning";
	}

	@Override
	protected boolean performAction(IGuild server, IUser user, String reason) {
		return UtilDue.removeRole(server, user,
				BanUtil.CONFIG.getWarningRole());
	}

}
