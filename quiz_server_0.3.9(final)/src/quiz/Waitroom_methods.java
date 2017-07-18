package quiz;

public class Waitroom_methods {
	Quiz_server quizServer;
	QuizRoom qr;
	public Waitroom_methods(Quiz_server q) {
//		System.out.println(q.p_list.size());
		this.quizServer = q;
		
	}
	// 대기실에 있는 사람들의 이름을 반환하는 메소드
	public String[] getUsers(){
		String[] arr = new String[quizServer.p_list.size()];
		int i = 0;
		for (QuizPlayer k : quizServer.p_list) {
			arr[i] = k.getNickname();
			i++;
		}
		return arr;
	}
	
	// 대기실에 만들어진 방 목록을 반환하는 메소드
	public String[] getRooms(){
		String[] arr = new String[quizServer.r_list.size()];
		int i = 0;
		for (QuizRoom k : quizServer.r_list) {
			if (k.isQuizPlaying()) {
				arr[i] = k.getRoomName()+"(게임중)";
			} else {
				arr[i] = k.getRoomName()+"("+k.qp_list.size()+"/4)";
			}
			i++;
		}
		return arr;
	}
	
	// 대기실에 있는 사람들에게 프로토콜 전달
	public void sendMsg(Protocol p){
		try {
			for (QuizPlayer k : quizServer.p_list) {
				k.oos.writeObject(p);
				k.oos.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 귓속말 보내기
	public void sendShortMsg(int usernum, Protocol p){
		try {
			QuizPlayer qp = quizServer.p_list.get(usernum);
			qp.oos.writeObject(p);
			qp.oos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
