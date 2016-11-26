package xyz.sidetrip;

import java.util.Map;

import org.apache.commons.collections4.map.DefaultedMap;

import sx.blah.discord.handle.obj.IUser;

public class Game {
	
	private static Map<String,Player> players = new DefaultedMap<String,Player>((Player)null);
	public static final String[] TEST_QUESTIONS = new String[]{"What is your name?","Do you like test questions?","Is this a cool placeholder?"};
	
	
	public static Player findPlayer(String userID){
		return players.get(userID);
	}
	
	public static Player findPlayer(IUser user){
		return players.get(user.getID());
	}

}
