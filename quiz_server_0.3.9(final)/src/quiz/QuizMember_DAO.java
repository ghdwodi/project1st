package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//Quiz Member Data Access Object
public class QuizMember_DAO {
	Connection conn = null;
	PreparedStatement ptmt = null;
	ResultSet resSet = null;
	int result;
	QuizMember_VO qmvo;
	ArrayList<QuizMember_VO> qmvoList;
	ArrayList<String> idList;
	int idx;
	
	public QuizMember_DAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "1111";
			
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
		}
	}
	
	// 전체 회원 조회
	public ArrayList<QuizMember_VO> selectAll(){
		qmvoList = new ArrayList<>();
		try {
			String sql = "select * from quizmember";
			ptmt = conn.prepareStatement(sql);
			resSet = ptmt.executeQuery();
			
			while (resSet.next()){
				qmvo = new QuizMember_VO();
				qmvo.setIdx(resSet.getInt(1));
				qmvo.setId(resSet.getString(2));
				qmvo.setPw(resSet.getString(3));
				qmvo.setName(resSet.getString(4));
				qmvo.setNickname(resSet.getString(5));
				qmvo.setIntroduction(resSet.getString(6));
				qmvo.setPhoto(resSet.getString(7));
				qmvo.setRegdate(resSet.getString(8));
				qmvoList.add(qmvo);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return qmvoList;
	}
	
	// 로그인
	public int login(String id, String pw){
		try {
			String sql = "select idx from quizmember where id=? and password=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, id);
			ptmt.setString(2, pw);
			resSet = ptmt.executeQuery();
			
			if (resSet.next()){
				idx = resSet.getInt("idx");
			} else {
				idx = 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return idx;
	}
	
	// ID조회
	
	public ArrayList<String> selectID(){
		idList = new ArrayList<>();
		try {
			String sql = "select id from quizmember";
			ptmt = conn.prepareStatement(sql);
			resSet = ptmt.executeQuery();
			
			while(resSet.next()){
				idList.add(resSet.getString("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return idList;
	}
	
	// 회원정보 조회
	public QuizMember_VO info(int idx){
		qmvo = new QuizMember_VO();
		try {
			String sql = "select * from quizmember where idx=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, idx);
			resSet = ptmt.executeQuery();
			
			while (resSet.next()){
				qmvo = new QuizMember_VO();
				qmvo.setIdx(resSet.getInt(1));
				qmvo.setId(resSet.getString(2));
				qmvo.setPw(resSet.getString(3));
				qmvo.setName(resSet.getString(4));
				qmvo.setNickname(resSet.getString(5));
				qmvo.setIntroduction(resSet.getString(6));
				qmvo.setPhoto(resSet.getString(7));
				qmvo.setRegdate(resSet.getString(8));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return qmvo;
	}
	
	// 회원가입
	public int join(String id, String pw, String name, String nick, String intro, String photo){
		try {
			String sql = "insert into quizmember values(quizmember_seq.nextval,?,?,?,?,?,?,sysdate)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, id);
			ptmt.setString(2, pw);
			ptmt.setString(3, name);
			ptmt.setString(4, nick);
			ptmt.setString(5, intro);
			ptmt.setString(6, photo);
			result = ptmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	// 
	
	// 연결 끊기
	public void closeConnect(){
		try {
			resSet.close();
			ptmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
