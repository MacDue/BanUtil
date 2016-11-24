package xyz.sidetrip;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.Status;

public class EventHandler {
	
	@EventSubscriber
	public void onReadyEvent(ReadyEvent event){
		IDiscordClient client = event.getClient();
		client.changeStatus(Status.game("@Due4J helpme"));
	}

}
