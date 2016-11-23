package wujin.tourism.android.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileHelper {
	private String floderpath = "wujinly";

	public String SDPATH() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	public Boolean issdsure() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	public String imagepath() {
		File sdFile = Environment.getExternalStorageDirectory();
		String filepath = sdFile + "/" + floderpath;
		return filepath;
	}

	public void IsSDExistandcreatedir() {
		boolean isdExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (isdExist) {
			File sdFile = Environment.getExternalStorageDirectory();
			String filepath = sdFile + "/" + floderpath;
			if (!isFileDirExsit(filepath)) {
				CreateFileDir(filepath);
			}
		}
	}

	public void IsfileExistandcreatedir(String path) {
		boolean isdExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (isdExist) {
			if (!isFileDirExsit(path)) {
				CreateFileDir(path);
			}
		}
	}

	public void print(String str, String pathString) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(pathString, true);
			bw = new BufferedWriter(fw);
			bw.write(str);
			bw.newLine();
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				bw.close();
				fw.close();
			} catch (IOException e1) {
			}
		}
	}

	public boolean isFileDirExsit(String file) {
		File fileDir = new File(file);
		return fileDir.exists();
	}

	public boolean CreateFile(String file) {
		boolean Issuccess = false;
		File filename = new File(file);
		try {
			filename.createNewFile();
			Issuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Issuccess;
	}

	public boolean CreateFileDir(String file) {
		boolean issuccess = false;
		try {
			File fileDir = new File(file);
			fileDir.mkdirs();
			issuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issuccess;
	}

	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH() + "/" + floderpath + fileName);
		return file.exists();
	}

	public boolean delSDFile(String fileName) {
		File file = new File(SDPATH() + "/" + floderpath + fileName);
		if (file == null || !file.exists() || file.isDirectory())
			return false;
		file.delete();
		return true;
	}

	public boolean saveMyBitmap(String stringname, Bitmap mBitmap) {
		boolean success = true;
		File sdFile = Environment.getExternalStorageDirectory();
		String filepath = sdFile + "/" + floderpath;
		File f = new File(filepath + "/" + stringname);
		try {
			f.createNewFile();
		} catch (IOException e) {
			success = false;
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			success = false;
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			success = false;
		}
		try {
			fOut.close();
		} catch (IOException e) {
			success = false;
		}
		return success;
	}
}
