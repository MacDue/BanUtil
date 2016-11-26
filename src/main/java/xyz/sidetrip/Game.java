package xyz.sidetrip;

import java.util.concurrent.ConcurrentHashMap;

import sx.blah.discord.handle.obj.IUser;

public class Game {

	// ConcurrentHashMap might better here.
	//Unsure if I should use hash maps or some sort of database.
	private static ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	private static ConcurrentHashMap<String, Weapon> weapons = new ConcurrentHashMap<String, Weapon>();
	
	public static Player findPlayer(String userID) {
		return players.get(userID);
	}

	public static Player findPlayer(IUser user) {
		return findPlayer(user.getID());
	}
	
	public static boolean playerExists(String userID){
		return players.containsKey(userID);
	}

}
