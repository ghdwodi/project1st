package quiz;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class QuizPlayer extends Thread {
	Socket socket;
	Quiz_server quizServer;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Protocol pro1, pro2;
	
	// 퀴즈VO
	Quiz_VO qvo;
	ArrayList<Quiz_VO> qvoList;
	Quiz_DAO qd;
	Quiz_factory qf;
	
	// 회원VO
	QuizMember_VO qmvo;
	ArrayList<QuizMember_VO> qmvoList;
	ArrayList<String> id_list;
	QuizMember_DAO qmd;
	
	// 체크
	int idCheck=0;
	int joinRes=0;
	
	// 파일 입출력
	Photo_IO pio;
	Text_IO tio;
	
	// 방
	QuizRoom quizRoom;
	
	// 기타
	Waitroom_methods wm;
	String nickname;
	
	public String getNickname() {
		return nickname;
	}

	public QuizPlayer(Quiz_server quiz_server) {
		try {
			quizServer = quiz_server;
			quizRoom = new QuizRoom();
			wm = new Waitroom_methods(quizServer);
			socket = quizServer.s;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			qmvo = new QuizMember_VO();
			qvo = new Quiz_VO();
			qvoList = new ArrayList<>();
			qmd = new QuizMember_DAO();
			pio = new Photo_IO(); 
			tio = new Text_IO();
			qd = new Quiz_DAO();
		} catch (Exception e) {
		}
	}
	
	@Override
	public void run() {
		try {
			while (true){
				pro1 = (Protocol) ois.readObject();
				pro2 = new Protocol();
				System.out.println(pro1.getCmd());
				System.out.println(quizServer.p_list);
				switch (pro1.getCmd()) {
					// ID 중복체크
					case 101:
						String idChk = pro1.getMsg();
						id_list = qmd.selectID();
//						System.out.println(id_list);
						if (id_list.contains(idChk)){
							idCheck = 1;
						} else {
							idCheck = 0;
						}
						pro2.setCmd(idCheck);
						oos.writeObject(pro2);
						oos.flush();
						break;
	
					// 회원가입
					case 100:
						qmvo = pro1.getQuizMemVO();
						
						String newId = qmvo.getId().trim();
						String newPw = qmvo.getPw().trim();
						String newName = qmvo.getName().trim();
						String newNick = qmvo.getNickname().trim();
						String newIntro = qmvo.getIntroduction().trim();
						String newPhoto = qmvo.getPhoto().trim();
						byte[] photo = qmvo.getPhotoByte();
						
						joinRes = qmd.join(newId, newPw, newName, newNick, newIntro, newPhoto);
						
						File file = new File("c:/util/quizmember/"+newId);
						file.mkdirs();
						String introPath = "c:/util/quizmember/"+newId+"/"+newId+"_자기소개.txt";
						String introContent = qmvo.getIntroduction().replace("\n", "\r\n");
						tio.textsave(introPath, introContent);
						String photoPath = "c:/util/quizmember/"+newId+"/"+newId+"_"+newPhoto;
						pio.photoSave(photoPath, photo);
						
						pro2.setCmd(joinRes);
						oos.writeObject(pro2);
						oos.flush();
						break;

					// 로그인
					case 200:
						qmvo = pro1.getQuizMemVO();
						String id = qmvo.getId();
						String pw = qmvo.getPw();
//						System.out.println(id+pw);
						int loginIdx = qmd.login(id, pw);
//						System.out.println(loginIdx);
						if (loginIdx!=0){
							QuizMember_VO qmvo2 = qmd.info(loginIdx);
							String photoPath2 = "c:/util/quizmember/"+id+"/"+id+"_"+qmvo2.getPhoto().trim();
							int size2 = pio.fileSize(photoPath2);
							byte[] photo2 = pio.photoUpload(photoPath2, size2);
							qmvo2.setPhotoByte(photo2);
							pro2.setQuizMemVO(qmvo2);
						}
						pro2.setCmd(loginIdx);
						if (loginCheck(loginIdx)){
							pro2.setCmd(-1);
						}
						oos.writeObject(pro2);
						oos.flush();
						break;
						
					// 퀴즈 추가
					case 300:
						qvo = pro1.getQuizVO();
						String quiz = qvo.getQuiz();
						String item1 = qvo.getQuiz_item1();
						String item2 = qvo.getQuiz_item2();
						String item3 = qvo.getQuiz_item3();
						String item4 = qvo.getQuiz_item4();
						int quizAns = qvo.getQuiz_answer();
						int result = qd.addQuiz(quiz, item1, item2, item3, item4, quizAns);
						
						pro2.setCmd(result);
						oos.writeObject(pro2);
						oos.flush();
						break;
						
					// 모든 회원정보 조회
					case 400:
						qmvoList = qmd.selectAll();
						System.out.println(qmvoList.size());
						pro2.setMemberList(qmvoList);
						oos.writeObject(pro2);
						oos.flush();
						break;
						
					// 대기실 입장
					case 500:
						qmvo = pro1.getQuizMemVO();
						nickname = qmvo.getNickname();
						quizServer.p_list.add(this);
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						break;
								
					// 대기실 퇴장
					case 502:
						quizServer.p_list.remove(this);
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						break;

					// 채팅
					case 600:
						String msg = pro1.getMsg();
						String style = pro1.getStyle();
						qmvo = pro1.getQuizMemVO();
						pro2.setCmd(601);
						pro2.setMsg(msg);
						pro2.setStyle(style);
						pro2.setQuizMemVO(qmvo);
						wm.sendMsg(pro2);
						break;
						
					// 귓속말
					case 602:
						String shortmsg = pro1.getMsg();
						int usernum = pro1.getIndex();
						qmvo = pro1.getQuizMemVO();
						pro2.setCmd(601);
						pro2.setMsg("(귓속말)"+shortmsg);
						pro2.setStyle("BLACK");
						pro2.setQuizMemVO(qmvo);
						wm.sendShortMsg(usernum, pro2);
						break;
						
					// 빙 생성
					case 700:
						System.out.println(pro1.getMsg());
						String roomName = pro1.getMsg();
						qmvo = pro1.getQuizMemVO();
						quizRoom.joinRoom(this);
						quizRoom.setRoomName(roomName);
						quizRoom.setQuizPlaying(false);
//						System.out.println(quizRoom.getRoomMember());
						quizServer.r_list.add(quizRoom);
						quizServer.p_list.remove(this);
//						System.out.println(quizServer.p_list.size());
//						System.out.println(quizRoom.qp_list.size());
						
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						
						pro2.setCmd(705);
						pro2.setRoomUsers(quizRoom.getRoomUsers());
						pro2.setIndex(quizRoom.qp_list.size());
						pro2.setQuizMemVO(qmvo);
						quizRoom.sendMsg(pro2);
						break;
					
					// 방 입장
					case 701:
						int index = pro1.getIndex();
						quizRoom = quizServer.r_list.get(index);
						quizRoom.joinRoom(this);
						quizServer.p_list.remove(this);
						
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						
						pro2.setCmd(705);
						pro2.setRoomUsers(quizRoom.getRoomUsers());
						pro2.setIndex(quizRoom.qp_list.size());
						pro2.setQuizMemVO(qmvo);
						quizRoom.sendMsg(pro2);
						break;
						
					// 방 퇴장
					case 702:
						quizServer.p_list.add(this);
						quizRoom.exitRoom(this);
						if(quizRoom.getRoomMember()==0){
							quizServer.r_list.remove(quizRoom);
						}
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						
						pro2.setCmd(705);
						pro2.setRoomUsers(quizRoom.getRoomUsers());
						pro2.setIndex(quizRoom.qp_list.size());
						pro2.setQuizMemVO(qmvo);
						quizRoom.sendMsg(pro2);
						break;
						
					// 퀴즈 시작
					case 800:
						int quizNum = pro1.getIndex();
						qf = new Quiz_factory(quizNum);
						quizRoom.setQuizPlaying(true);
						quizServer.qsvoList = new ArrayList<>();
						String[] users = quizRoom.getRoomUsers();
//						for (String string : users) {
//							System.out.println(string);
//						}
						pro2.setCmd(801);
						pro2.setQuizMemVO(pro1.getQuizMemVO());
						pro2.setQuizList(qf.getQuizList());
						pro2.setRoomUsers(users);
						pro2.setScore(0);
						pro2.setIndex(0);
						pro2.setStyle(pro1.getStyle());
						pro2.setMsg(quizRoom.getRoomName());
						pro2.setCorrect(-1);
						quizRoom.sendMsg(pro2);
						
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						break;
						
					// 퀴즈 진행
					case 802:
						pro2.setCmd(803);
						pro2.setUserChange(false);
						pro2.setQuizNum(pro1.getQuizNum());
						pro2.setScore(pro1.getScore());
						pro2.setIndex(pro1.getIndex());
						pro2.setCorrect(pro1.getCorrect());
						pro2.setQuizMemVO(pro1.getQuizMemVO());
						pro2.setMsg(pro1.getQuizMemVO().getNickname());
						quizRoom.sendMsg(pro2);
						break;
					
					// 유저 바꿔서 퀴즈 진행
					case 803:
						quizServer.qsvoList.add(pro1.getQsvo());
//						System.out.println("점수목록 크기 : "+quizServer.qsvoList.size());
//						System.out.println("점수 : "+pro1.getQsvo().getScore());
						pro2.setCmd(803);
						pro2.setCorrect(pro1.getCorrect());
						pro2.setUserChange(true);
						pro2.setQuizNum(pro1.getQuizNum());
						pro2.setScore(pro1.getScore());
						pro2.setIndex(pro1.getIndex());
						pro2.setQuizMemVO(pro1.getQuizMemVO());
						pro2.setMsg(quizRoom.qp_list.get(pro1.getIndex()).getNickname());
						quizRoom.sendMsg(pro2);
						break;
						
					// 퀴즈 종료
					case 804:
						quizServer.qsvoList.add(pro1.getQsvo());
						quizRoom.setQuizPlaying(false);
						pro2.setCorrect(pro1.getCorrect());
						System.out.println("점수목록 크기 : "+quizServer.qsvoList.size());
						pro2.setCmd(805);
						pro2.setQsvoList(quizServer.qsvoList);
						quizRoom.sendMsg(pro2);
						System.out.println("점수목록 크기 : "+quizServer.qsvoList.size());
						pro2.setCmd(501);
						pro2.setUsers(wm.getUsers());
						pro2.setRooms(wm.getRooms());
						wm.sendMsg(pro2);
						break;
					}
				}
		} catch (Exception e) {
			System.out.println("2:"+e);
		}
	}
	// 현재 접속중인 사람 idx 조회
	public boolean loginCheck(int idx){
		boolean res = false;
		for (QuizPlayer k : quizServer.p_list) {
			if (k.qmvo.getIdx()==idx) {
				res = true;
			}
		}
		for (QuizPlayer k : quizRoom.qp_list) {
			if (k.qmvo.getIdx()==idx) {
				res = true;
			}
		}
		return res;
	}
}