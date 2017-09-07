package xyz.sidetrip.commands.moderation;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.PermissionUtils;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;

public class Kick extends ModCommand {

	public Kick() {
		super("kick");
	}

	@Override
	protected String getAction() {
		return ":boot: | Kick User!";
	}

	@Override
	protected int getColour() {
		return 0xaec6cf;
	}

	@Override
	protected boolean performAction(IGuild server, IUser user, String reason) {
		PermissionUtils.requireHierarchicalPermissions(server,
				BanUtil.getClient().getOurUser(), user, Permissions.KICK);
		server.kickUser(user, reason);
		return true;
	}

	@Override
	public boolean canUse(IUser user) {
		return super.canUse(user) && UtilDue.hasRole(BanUtil.CONFIG.getServer(),
				user, BanUtil.CONFIG.getCanKickRole());
	}

}
