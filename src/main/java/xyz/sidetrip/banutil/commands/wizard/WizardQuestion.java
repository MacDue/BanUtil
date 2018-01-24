package xyz.sidetrip.banutil.commands.wizard;

import org.apache.commons.lang3.math.NumberUtils;
import xyz.sidetrip.banutil.UtilDue;

public class WizardQuestion {

	private final String question;

	private final AnswerType answerType;

	public static enum AnswerType {
		STRING, NUMBER, IMAGE, LONGNUMBER;
	}

	private boolean constrained = false;
	private double maxNumber;
	private double minNumber;
	private int maxStringLength;
	private final String answerValueName;

	public WizardQuestion(String question, String answerValueName,
			AnswerType answerType) {
		this.question = question;
		this.answerType = answerType;
		this.answerValueName = answerValueName;
	}

	public WizardQuestion(String question, String answerValueName,
			int maxStringLength) {
		this.question = question;
		this.answerType = AnswerType.STRING;
		this.answerValueName = answerValueName;
		this.maxStringLength = maxStringLength;
		this.constrained = true;
	}

	public WizardQuestion(String question, String answerValueName,
			double minNumber, double maxNumber) {
		this.question = question;
		this.answerType = AnswerType.NUMBER;
		this.answerValueName = answerValueName;
		this.minNumber = minNumber;
		this.maxNumber = maxNumber;
		this.constrained = true;

	}

	public boolean validAnswer(String answer) {
		switch (answerType) {
			case NUMBER :
				return validNumber(answer);
			case STRING :
				return validString(answer);
			case IMAGE :
				return vaildImage(answer);
			case LONGNUMBER :
				return validLong(answer);
		}
		return false;
	}

	public String getError(String answer) {
		switch (answerType) {
			case NUMBER :
				if (!UtilDue.isDouble(answer))
					return "'" + answerValueName + "' must be a number!";
				else
					return "'" + answerValueName + "' must be in the range "
							+ minNumber + " to " + maxNumber + "!";
			case STRING :
				if (answer.length() == 0)
					return "'" + answerValueName
							+ "' must be at least one character!";
				else
					return "'" + answerValueName + "' must be less than "
							+ maxStringLength + " characters!";
			case IMAGE :
				return "The link given does not return an image!";
			case LONGNUMBER :
				return "'" + answerValueName + "' must be a number!";
		}
		return null;
	}

	private boolean validNumber(String numberString) {
		if (UtilDue.isDouble(numberString)) {
			if (!constrained)
				return true;
			return inRange(Double.parseDouble(numberString));
		}
		return false;

	}
	private boolean validLong(String numberString) {
		return NumberUtils.isParsable(numberString);
	}

	private boolean vaildImage(String imageURL) {
		return false;// TODO image checking
	}

	private boolean validString(String string) {
		if (!constrained)
			return string.length() > 0;
		else
			return string.length() > 0 && string.length() <= maxStringLength;
	}

	public String getQuestion() {
		return question;
	}

	private boolean inRange(double value) {
		return value >= minNumber && value <= maxNumber;
	}

	public AnswerType getAnswerType() {
		return answerType;
	}

}
