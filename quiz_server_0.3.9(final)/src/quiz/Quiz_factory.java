package quiz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// 퀴즈 목록에서 q개의 문제를 뽑아내는 클래스

public class Quiz_factory {
	private int[] quiz;
	private ArrayList<Quiz_VO> qvoList;
	public Quiz_factory(int q) {
		Quiz_DAO qd = new Quiz_DAO();
		qvoList = qd.selectAll();
		
		quiz = new int[q];
		Set<Integer> quizSet = new HashSet<>();
		
		for (int i = 0; i < quiz.length; i++) {
			quiz[i] = (int)(Math.random()*qvoList.size());
			if (!quizSet.add(quiz[i])) i--;
		}
	}
	public int[] getQuiz() {
		return quiz;
	}
	
	public ArrayList<Quiz_VO> getQuizList(){
		ArrayList<Quiz_VO> quizList = new ArrayList<>();
		for (int k : quiz) {
			quizList.add(qvoList.get(k));
		}
		return quizList;
	}
}
