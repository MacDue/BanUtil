package xyz.sidetrip.banutil.commands.moderation;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.PermissionUtils;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.Emojis;
import xyz.sidetrip.banutil.UtilDue;

public class Ban extends ModCommand {

	public Ban() {
		super("ban", true);
	}

	protected Ban(String name) {
		super(name, true);
	}

	@Override
	protected String getAction() {
		return Emojis.BAN + " | Banne User";
	}

	@Override
	protected int getColour() {
		return 0xff6961;
	}

	@Override
	protected boolean performAction(IGuild server, IUser user, String reason) {
		PermissionUtils.requireHierarchicalPermissions(server,
				BanUtil.getClient().getOurUser(), user, Permissions.BAN);
		server.banUser(user, reason);
        BanUtil.STATUS.bansSinceLastRestart++;
        return true;
	}

	@Override
	public boolean canUse(IUser user) {
		return super.canUse(user) && UtilDue.hasRole(BanUtil.CONFIG.getServer(),
				user, BanUtil.CONFIG.getCanBanRole());
	}

}
