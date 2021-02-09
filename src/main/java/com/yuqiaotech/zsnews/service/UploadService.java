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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yuqiaotech.sysadmin.model.SysConfig;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class UploadService {
	/**
	 * 处理上传的文件
	 *
	 * @param in
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List getBankListByExcel(InputStream in, String fileName) throws Exception {
		List list = new ArrayList<>();
		// 创建Excel工作薄
		Workbook work = this.getWorkbook(in, fileName);
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}

			List headers = new ArrayList<>();
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null) {
					continue;
				}

				// List<Object> li = new ArrayList<>();
				Map single = new HashMap<>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					if (j == 0) {
						headers.add(cell.getStringCellValue());
					} else {
						single.put(headers.get(y),getCellValue(cell));
					}

				}
				list.add(single);
			}
		}
		work.close();
		return list;
	}

	/**
	 * 判断文件格式
	 *
	 * @param inStr
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook workbook = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (".xls".equals(fileType)) {
			workbook = new HSSFWorkbook(inStr);
		} else if (".xlsx".equals(fileType)) {
			workbook = new XSSFWorkbook(inStr);
		} else {
			throw new Exception("请上传excel文件！");
		}
		return workbook;
	}

	public Object getCellValue(Cell cell) {
		Object value = null;
		if (cell != null) {
			int cellType = cell.getCellType();
			CellStyle style = cell.getCellStyle();
			short format = style.getDataFormat();
			//System.err.println(cell.getStringCellValue());
			switch (cellType) {
			case 0:
				double numTxt = cell.getNumericCellValue();
				if (format <= 22 && format >= 14) {
					value = HSSFDateUtil.getJavaDate(numTxt);
				} else if (format <= 47 && format >= 45) {

					value = HSSFDateUtil.getJavaDate(numTxt);
				} else {
					value = numTxt;
				}
				break;
			case 1:
				value =cell.getStringCellValue();
				break;
			case 4:
				boolean booleanTxt = cell.getBooleanCellValue();
				value = booleanTxt;
				break;
			case 3:
				value = null;
				break;

			}

			return value;
		} else {
			return null;
		}
	}

	
	public static  String toStr(Object o){
		if(o instanceof Number)o= ((Number)o).longValue();
		return o == null?null:o.toString().trim();
	}
	
	public static  Float toFloat(Object o){
		if(o == null)return null;
		if(o instanceof Number) return ((Number)o).floatValue();
		Float r = null;
		try{
			r = Float.parseFloat(o.toString());
		}catch(Exception e){}
		return r;
	}
	
	public static  Double toDouble(Object o){
		if(o == null)return null;
		if(o instanceof Number) return ((Number)o).doubleValue();
		Double r = null;
		try{
			r = Double.parseDouble(o.toString());
		}catch(Exception e){}
		return r;
	}
	
	public static Long toLong(Object o){
		if(o == null)return null;
		if(o instanceof Number) return ((Number)o).longValue();
		Long r = null;
		try{
			r = Long.parseLong(o.toString());
		}catch(Exception e){}
		return r;
	}
	
	public static Integer toInteger(Object o){
		if(o == null)return null;
		if(o instanceof Number) return ((Number)o).intValue();
		Integer r = null;
		try{
			r = Integer.parseInt(o.toString());
		}catch(Exception e){}
		return r;
	}
	
	public static  Date toDate(Object o){
		if(o == null)return null;
		Date d = null;
		if(o instanceof Number){//excel中用户的日期是’自定义‘格式的时候 取出来是个数字类型的 所以在这要转换一下
			d =  HSSFDateUtil.getJavaDate((Double)o);//excel自动转类型的方法
		}else if(o instanceof Date){
			d = (Date)o;
		}else{
			String str = o.toString().trim();
			//2010.8.100:00:00:0
			String pattern = "yyyy-MM-dd";
			if((str.indexOf(".")==4||str.indexOf(".")==2||str.indexOf("/")==4||str.indexOf("/")==2)){
				str = str.replace(".", "-").replace("/", "-");
			}
			//有填入 2010.8.100:00:00:0数据的情况 所以作如下处理
			if(str.indexOf(":")!=-1){
				pattern = "yyyy-MM-dd hh:mm:ss";
				str = str.substring(0,str.indexOf(":")-2)+" "+str.substring(str.indexOf(":")-2);
			
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			try {
				d= simpleDateFormat.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}//出现用户自定义格式的情况：obj.getClass().equals(Date.class)为false 
		}
		if(d != null && d.getYear()+1900>3016)return null;
		return d;
	}
	  /**
	   * 过滤空格、换行符，导出csv字段使用
	   * @param obj 获取map字段对象
	   * @return 字段的值
	   */
	  public static  String columnsForBlank(Object obj){
		  String str = "";
		  if(obj!=null){
			  str = obj.toString().trim().replaceAll("\n", "");
		  }
		  return str;
	  }
}
