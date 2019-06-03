package com.rilintech.fragment_301_huxike_android.tool;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SdcardTools {
	private Context mContext;
	private String sdCardRoot;
	private String path="/mnt/sdcard/307Image/";
	public SdcardTools(Context context){
		this.mContext=context;
	}

	/**判断sd卡是否存在*/
	public boolean getSdcardState(){	
		String state= Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	/**在SD卡上创建文件夹目录*/
	public File createDirOnSDCard(String dir){
		
		File dirFile = new File("/mnt/sdcard/"+dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}	
		return dirFile;

	}

	/** 
	 * 在SD卡上创建文件 
	 */  
	public File createFileOnSDCard(String fileName, String dir) throws IOException {
		
		File file=new File(path+fileName);
		file.createNewFile();  
		return file;  
	}  
	/** 
	 * 判断SD卡上文件夹是否存在 
	 */  
	public boolean isDirFileExist(String dirs){
		File dir = new File("/mnt/sdcard/"+ dirs);
		return dir.exists();  
	}

	/** 
	 * 判断SD卡上文件是否存在 
	 */  
	public boolean isFileExist(String fileName, String path){
		File file = new File(path+ fileName);
		return file.exists();  
	}  

	/**
	 * 写入数据到SD卡中 
	 */  
	public File writeData2SDCard(String path, String fileName, InputStream data){
		File file = null;
		OutputStream output = null;
		try {  
			createDirOnSDCard(path);  //创建目录  
			file = createFileOnSDCard(fileName, path);  //创建文件  
			output = new FileOutputStream(file);
			byte buffer[] = new byte[2*1024];          //每次写2K数据  
			int temp;  
			while((temp = data.read(buffer)) != -1 ){  
				output.write(buffer,0,temp);  
			}  
			output.flush();  

		} catch (Exception e) {
			e.printStackTrace();  
		}  
		finally{  
			try {  
				output.close();    //关闭数据流操作  
			} catch (Exception e2) {
				e2.printStackTrace();  
			}  
		}  

		return file;  
	}  

}
