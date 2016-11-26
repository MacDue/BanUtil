package xyz.sidetrip.commands;

public class WizardQuestion {

	private final String question;
	
	private final AnswerType answerType;
	
	public static enum AnswerType{
		STRING,NUMBER,IMAGE,LONGNUMBER;
	}
	
	public WizardQuestion(String question,AnswerType answerType){
		this.question = question;
		this.answerType = answerType;
	}
	
	public String getQuestion(){
		return question;
	}
	
	public AnswerType getAnswerType(){
		return answerType;
	}
	
}
