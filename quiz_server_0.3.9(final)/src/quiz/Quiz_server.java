package quiz;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Quiz_server implements Runnable {
	ServerSocket ss;
	Socket s;

	// 대기실 리스트
	ArrayList<QuizPlayer> p_list;
		
	// 퀴즈방 리스트
	ArrayList<QuizRoom> r_list;
	
	// 점수 리스트
	ArrayList<QuizScoreVO> qsvoList;
	
	public Quiz_server() {
		r_list = new ArrayList<>();
		p_list = new ArrayList<>();
		try {
			ss = new ServerSocket(7979);
			System.out.println("서버 대기중...");
			new Thread(this).start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void run() {
		try {
			while(true){
				s = ss.accept();
				QuizPlayer player2 = new QuizPlayer(this);
				player2.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				s.close();
				ss.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new Quiz_server();
	}
}
