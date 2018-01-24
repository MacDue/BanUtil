package xyz.sidetrip.banutil.commands.owner;

import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.banutil.BanUtil;
import xyz.sidetrip.banutil.commands.Command;

public abstract class OwnerCommand extends Command {

	public OwnerCommand(String name) {
		super(name);
	}

	@Override
	public boolean canUse(IUser user) {
		return user.getLongID() == BanUtil.CONFIG.getOwner().getLongID();
	}
}
