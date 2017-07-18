package quiz;

import java.util.ArrayList;

public class QuizRoom {
	// 방 이름
	String roomName;
	
	// 참여자
	ArrayList<QuizPlayer> qp_list;
	
	// 게임 시작 여부
	private boolean quizPlaying;
	
	public boolean isQuizPlaying() {
		return quizPlaying;
	}


	public void setQuizPlaying(boolean quizPlaying) {
		this.quizPlaying = quizPlaying;
	}


	public QuizRoom() {
		qp_list = new ArrayList<>();
	}

	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getRoomName(){
		return roomName;
	}
	
	// 대기실에서 방으로 참여하는 메소드
	public void joinRoom(QuizPlayer qp){
		qp_list.add(qp);
	}
	
	public void exitRoom(QuizPlayer qp){
		qp_list.remove(qp);
	}
	
	// 방에 들어온 사람 수 반환
	public int getRoomMember(){
		return qp_list.size();
	}
	
	// 방에 들어온 사람 목록 반환
	public String[] getRoomUsers(){
		String[] arr = new String[qp_list.size()];
		int i = 0;
		for (QuizPlayer k : qp_list) {
			arr[i] = k.getNickname();
			i++;
		}
		return arr;
	}
	
	// 대기실에 있는 사람들에게 프로토콜 전송
	public void sendMsg(Protocol protocol){
		for(QuizPlayer k : qp_list ){
			try {
				k.oos.writeObject(protocol);
				k.oos.flush();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
