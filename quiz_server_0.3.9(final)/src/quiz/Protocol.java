package quiz;

import java.io.Serializable;
import java.util.ArrayList;

public class Protocol implements Serializable {
	public static final long serialVersionUID = 125L;
	private int cmd;	// 커맨드
	private int score;	// 퀴즈 점수
	private int userNum;// 유저 번호
	private int quizNum;// 퀴즈 번호
	private int index;	// 기타 숫자
	private int correct;	// 0:정답,1:오답,2:시간초과
	private boolean userChange;
	private String msg;
	private String style;
	private Quiz_VO quizVO;
	private QuizMember_VO quizMemVO;
	private QuizScoreVO qsvo;
	private ArrayList<QuizScoreVO> qsvoList;
	private ArrayList<Quiz_VO> quizList;
	private ArrayList<QuizMember_VO> memberList;
	private String[] users,rooms,roomUsers;
	
	// 커맨드 목록
	// 100 = 회원가입
	// 101 = ID중복체크
	// 200 = 로그인
	// 300 = 퀴즈 추가
	// 400 = 회원정보 불러오기
	// 500 = 입장
	// 501 = 유저 목록 송수신
	// 501 = 퇴장
	// 600 = 채팅 송신
	// 601 = 채팅 수신
	// 700 = 방 만들기
	// 701 = 방 입장하기
	// 702 = 방 나가기
	// 705 = 방 인원 송수신
	// 800 = 퀴즈 시작
	// 801 = 첫 퀴즈 송수신
	// 802 = 퀴즈 답 입력
	// 803 = 다음 퀴즈 송수신
	
	public String[] getRoomUsers() {
		return roomUsers;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public void setRoomUsers(String[] roomUsers) {
		this.roomUsers = roomUsers;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}
	public String[] getRooms() {
		return rooms;
	}
	public void setRooms(String[] rooms) {
		this.rooms = rooms;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public Quiz_VO getQuizVO() {
		return quizVO;
	}
	public void setQuizVO(Quiz_VO quizVO) {
		this.quizVO = quizVO;
	}
	public QuizMember_VO getQuizMemVO() {
		return quizMemVO;
	}
	public void setQuizMemVO(QuizMember_VO quizMemVO) {
		this.quizMemVO = quizMemVO;
	}
	public ArrayList<Quiz_VO> getQuizList() {
		return quizList;
	}
	public void setQuizList(ArrayList<Quiz_VO> quizList) {
		this.quizList = quizList;
	}
	public ArrayList<QuizMember_VO> getMemberList() {
		return memberList;
	}
	public void setMemberList(ArrayList<QuizMember_VO> memberList) {
		this.memberList = memberList;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int getQuizNum() {
		return quizNum;
	}
	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}
	public QuizScoreVO getQsvo() {
		return qsvo;
	}
	public void setQsvo(QuizScoreVO qsvo) {
		this.qsvo = qsvo;
	}
	public ArrayList<QuizScoreVO> getQsvoList() {
		return qsvoList;
	}
	public void setQsvoList(ArrayList<QuizScoreVO> qsvoList) {
		this.qsvoList = qsvoList;
	}
	public boolean isUserChange() {
		return userChange;
	}
	public void setUserChange(boolean userChange) {
		this.userChange = userChange;
	}
}
