package quiz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Photo_IO {
	
	public int fileSize(String s){
		File file = new File(s);
		int size = (int)file.length();
		return size;
	}
	
	public void photoSave(String path, byte[] b){
		BufferedOutputStream bos=null;
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(path);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
			bos.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				bos.close();
				fos.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public byte[] photoUpload(String path, int size){
		FileInputStream fis=null;
		BufferedInputStream bis=null;
		byte[] b = new byte[size];
		try {
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			bis.read(b);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				bis.close();
				fis.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return b;
	}

}
