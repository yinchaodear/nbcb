package com.yuqiaotech.zsnews.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.yuqiaotech.sysadmin.model.SysConfig;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class AttachmentService{




	@Value("${attachmentRoot}")
	public String attachmentRoot;

	public static boolean downloadPicture(String urlx,String pathString) {
		 boolean flag = false ;
	     URL url = null;
	     int imageNumber = 0;

	     DataInputStream dataInputStream =null;
	     FileOutputStream fileOutputStream =null;
	     try {
	         url = new URL(urlx);
	         dataInputStream = new DataInputStream(url.openStream());

	        String path=(new File(pathString).getAbsolutePath()).replace(new File(pathString).getName(), "");
	  		File dirPath = new File(path);
		    if (!dirPath.exists()) {
		        dirPath.mkdirs();
		    }
	         fileOutputStream = new FileOutputStream(new File(pathString));
	         ByteArrayOutputStream output = new ByteArrayOutputStream();

	         byte[] buffer = new byte[1024];
	         int length;

	         while ((length = dataInputStream.read(buffer)) > 0) {
	             output.write(buffer, 0, length);
	         }
	         byte[] context=output.toByteArray();
	         fileOutputStream.write(output.toByteArray());
	         justImage(new File(pathString));
	         flag=true;
	     } catch (MalformedURLException e) {
	         e.printStackTrace();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }finally{
				try {
					if(dataInputStream!=null)
						dataInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(fileOutputStream!=null)
					fileOutputStream.close();
				} catch (IOException e) {
						e.printStackTrace();
				}
	     }
	     return flag;
	 }
	 

	/**1.调整图片大小
	 * 缩小图片，选择高宽里最大的，然后和相应的最大值进行比较，获取缩放比例，然后收缩图片。
	 * */
	private final static String[] picTypeStrings = { "bmp", "gif", "jpeg","jpg", "png" };
	private final static int maxWidth = 400;
	private final static int maxHeight = 400;
	private final static int maxWidthMiddle = 800;
	private final static int maxHeightMiddle = 800;

//	public int[] justImage(File file) throws IOException{
//		return justImage( file, null, null);
//	}
	public static int[] justImage(File file) throws IOException{
    	String fileName = file.getName();
    	String postFix = fileName.substring(fileName.lastIndexOf(".")+1);
    	postFix = postFix.toLowerCase();
    	if(Arrays.binarySearch(picTypeStrings,postFix)<0)return null;
		// 获取图片的高宽
    	Image imgdist = null;
    	File outFile = null;
    	try{
    		imgdist = ImageIO.read(file);
    		int width = imgdist.getWidth(null);
    		int height =imgdist.getHeight(null) ;
    		//小图尺寸
			float scaleWidth = ((float )maxWidth / width);
			float scaleHeight = ((float)maxHeight / height);
			float scale = Math.min(scaleHeight, scaleWidth);
			if (scale > 1)scale = 1;
			int newWidth = (int) (width * scale);
			int newHeight = (int) (height * scale);

			outFile = new File(file.getAbsolutePath()+".small");//以.small文件存放在path下
			//压缩
			Thumbnails.of(file).size(newWidth, newHeight).outputQuality(0.5).outputFormat("png")
					.toFile(outFile);

                //中图尺寸
                float scaleWidthMiddle = ((float )maxWidthMiddle / width);
                float scaleHeightMiddle = ((float)maxHeightMiddle / height);
                float scaleMiddle = Math.min(scaleHeightMiddle, scaleWidthMiddle);
                if (scaleMiddle > 1)scaleMiddle = 1;
                int newWidthMiddle = (int) (width * scaleMiddle);
                int newHeightMiddle = (int) (height * scaleMiddle);

                File middleOutFile = new File(file.getAbsolutePath() + ".middle");//以.middle文件存放在path下
                //压缩
                Thumbnails.of(file).size(newWidthMiddle, newHeightMiddle).outputQuality(0.5).outputFormat("png")
                        .toFile(middleOutFile);
  			return new int[]{width,height};
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return null;
    }
	 
}
