package quiz;

import java.io.Serializable;

public class Quiz_VO implements Serializable {
	private int quiz_number, quiz_answer;
	private String quiz;
	private String quiz_item1,quiz_item2,quiz_item3,quiz_item4;
	
	public int getQuiz_number() {
		return quiz_number;
	}
	public void setQuiz_number(int quiz_number) {
		this.quiz_number = quiz_number;
	}
	public int getQuiz_answer() {
		return quiz_answer;
	}
	public void setQuiz_answer(int quiz_answer) {
		this.quiz_answer = quiz_answer;
	}
	public String getQuiz() {
		return quiz;
	}
	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}
	public String getQuiz_item1() {
		return quiz_item1;
	}
	public void setQuiz_item1(String quiz_item1) {
		this.quiz_item1 = quiz_item1;
	}
	public String getQuiz_item2() {
		return quiz_item2;
	}
	public void setQuiz_item2(String quiz_item2) {
		this.quiz_item2 = quiz_item2;
	}
	public String getQuiz_item3() {
		return quiz_item3;
	}
	public void setQuiz_item3(String quiz_item3) {
		this.quiz_item3 = quiz_item3;
	}
	public String getQuiz_item4() {
		return quiz_item4;
	}
	public void setQuiz_item4(String quiz_item4) {
		this.quiz_item4 = quiz_item4;
	}

}
