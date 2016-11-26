package xyz.sidetrip.events;

import sx.blah.discord.api.events.Event;
import xyz.sidetrip.commands.InputWizzard;

public class WizzardEndEvent extends Event{
	
	private final InputWizzard wizzard;
	
	public WizzardEndEvent(InputWizzard wizzard){
		this.wizzard = wizzard;
	}
	
	public InputWizzard getWizzard(){
		return wizzard;
	}

}
