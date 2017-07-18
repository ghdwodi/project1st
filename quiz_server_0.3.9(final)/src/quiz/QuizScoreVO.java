package quiz;

import java.io.Serializable;

public class QuizScoreVO implements Serializable {
	private int score;
	private double time;
	private QuizMember_VO qmvo;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public QuizMember_VO getQmvo() {
		return qmvo;
	}
	public void setQmvo(QuizMember_VO qmvo) {
		this.qmvo = qmvo;
	}
}
