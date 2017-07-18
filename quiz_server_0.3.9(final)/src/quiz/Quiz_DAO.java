package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


// Quiz Data Access Object
public class Quiz_DAO {
	Connection conn = null;
	PreparedStatement ptmt = null;
	ResultSet resSet = null;
	int result;
	Quiz_VO vo;
	ArrayList<Quiz_VO> qvoList;
	
	public Quiz_DAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "1111";
			
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 퀴즈 전체 목록
	public ArrayList<Quiz_VO> selectAll(){
		try {
			String sql = "select * from quiz";
			ptmt = conn.prepareStatement(sql);
			resSet = ptmt.executeQuery();
			
			qvoList = new ArrayList<>();
			while (resSet.next()){
				vo = new Quiz_VO();
				vo.setQuiz_number(resSet.getInt(1));
				vo.setQuiz(resSet.getString(2));
				vo.setQuiz_item1(resSet.getString(3));
				vo.setQuiz_item2(resSet.getString(4));
				vo.setQuiz_item3(resSet.getString(5));
				vo.setQuiz_item4(resSet.getString(6));
				vo.setQuiz_answer(resSet.getInt(7));
				qvoList.add(vo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return qvoList;
	}
	
	// 퀴즈 추가
	public int addQuiz(String question, String item1, String item2, String item3, String item4, int answer){
		try {
			String sql = "insert into quiz values(quiz_seq.nextval,?,?,?,?,?,?)";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, question);
			ptmt.setString(2, item1);
			ptmt.setString(3, item2);
			ptmt.setString(4, item3);
			ptmt.setString(5, item4);
			ptmt.setInt(6, answer);
			result = ptmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
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
