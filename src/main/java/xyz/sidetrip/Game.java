package xyz.sidetrip;

import java.util.Map;

import org.apache.commons.collections4.map.DefaultedMap;
import xyz.sidetrip.commands.WizardQuestion.AnswerType;
import sx.blah.discord.handle.obj.IUser;
import xyz.sidetrip.commands.WizardQuestion;

public class Game {

	//ConcurrentHashMap maybe better here.
	private static Map<String, Player> players = new DefaultedMap<String, Player>(
			(Player) null);
	
	public static final WizardQuestion[] TEST_QUESTIONS = new WizardQuestion[]{
			new WizardQuestion("What is your monsters name?",
					AnswerType.STRING),
			new WizardQuestion(
					"What you like the quest proposition to be?\nE.g. ``Battle a``",
					AnswerType.STRING),
			new WizardQuestion(
					"The following questions are for the base stats of your quest.\nThese are the quests stats at level 1.\nWhat is the base attack of your monster?",
					AnswerType.NUMBER),
			new WizardQuestion("What is the base strength of your monster?",
					AnswerType.NUMBER),
			new WizardQuestion("What is the base HP of your monster?",
					AnswerType.NUMBER),
			new WizardQuestion("What is the base accuracy of your monster?",
					AnswerType.NUMBER),
			new WizardQuestion(
					"What weapon should your monster have?\nType 'none' for no weapon!",
					AnswerType.STRING),
			new WizardQuestion(
					"What is your monsters spawn rate?\nThis a percentage from **1-25%**.",
					AnswerType.NUMBER),
			new WizardQuestion(
					"Could you give a link to a image of your monster?",
					AnswerType.STRING)};
	
	public static Player findPlayer(String userID) {
		return players.get(userID);
	}

	public static Player findPlayer(IUser user) {
		return players.get(user.getID());
	}

}
