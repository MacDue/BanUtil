package xyz.sidetrip.banutil.commands.wizard;

import xyz.sidetrip.banutil.commands.wizard.WizardQuestion.AnswerType;

public class WizardQuestions {

	public static final WizardQuestion[] TEST_QUESTIONS = new WizardQuestion[]{
			new WizardQuestion("What is your monsters name?", "Monster name",
					32),
			new WizardQuestion(
					"What you like the quest proposition to be?\nE.g. ``Battle a``",
					"Quest proposition", 32),
			new WizardQuestion(
					"The following questions are for the base stats of your quest.\n"
							+ "These are the quests stats at level 1.\n"
							+ "What is the base attack of your monster?",
					"Base attack", AnswerType.NUMBER),
			new WizardQuestion("What is the base strength of your monster?",
					"Base strength", AnswerType.NUMBER),
			new WizardQuestion("What is the base HP of your monster?",
					"Base HP", AnswerType.NUMBER),
			new WizardQuestion("What is the base accuracy of your monster?",
					"Base accuracy", AnswerType.NUMBER),
			new WizardQuestion(
					"What weapon should your monster have?\nType 'none' for no weapon!",
					"Weapon name", 32),
			new WizardQuestion(
					"What is your monsters spawn rate?\nThis a percentage from **1-25%**.",
					"Spawn rate", 1, 25),
			new WizardQuestion(
					"Could you give a link to a image of your monster?",
					"Image link", AnswerType.STRING)};
}
