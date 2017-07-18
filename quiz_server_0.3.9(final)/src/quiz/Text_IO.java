package quiz;

import java.io.FileReader;
import java.io.FileWriter;

public class Text_IO {
	
	// 텍스트 저장
	public void textsave(String path, String jta){
		FileWriter fw = null;
		try {
			fw = new FileWriter(path);
			fw.write(jta);
			fw.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}
	
	
	// 불러오기
	public String load(String path){
		FileReader fr = null;
		String res = "";
		int k;
		try {
			fr = new FileReader(path);
			while ((k=fr.read()) != -1){
				res += (char)k;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return res;
	}
	

}
