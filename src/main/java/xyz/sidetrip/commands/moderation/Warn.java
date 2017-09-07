package xyz.sidetrip.commands.moderation;


import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.BanUtil;
import xyz.sidetrip.UtilDue;

public class Warn extends ModCommand{

	public Warn() {
		super("warn");
	}
	
	protected Warn(String name) {
		super(name);
	}

	@Override
	protected String getAction() {
		return ":anger: | Warn User";
	}

	@Override
	protected int getColour() {
		return 0x580f41;
	}

	@Override
	protected boolean performAction(IGuild server, IUser user, String reason) {
		return UtilDue.addRole(server, user, BanUtil.CONFIG.getWarningRole());
	}

}
