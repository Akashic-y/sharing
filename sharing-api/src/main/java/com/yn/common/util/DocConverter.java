package com.yn.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import lombok.Data;

/**
 * 
 * 文档转换工具类
 * 
 * @author YN
 *
 */
@Data
public class DocConverter {
	private static final Logger logger = LoggerFactory
			.getLogger(DocConverter.class);
	private String fileString;// (只涉及pdf2swf路径问题)
//	private String outputPath = "";// 输入路径 ，如果不设置就输出在默认的位置
	private String fileName;
	private String fileNameEnd;
	private File pdfFile;
	private File swfFile;
	private File docFile;

	public DocConverter(String fileString) {
		ini(fileString);
	}

	/**
	 * 重新设置file
	 * 
	 * @param fileString
	 */
	public void setFile(String fileString) {
		ini(fileString);
	}

	/**
	 * 初始化
	 * 
	 * @param fileString
	 */
	private void ini(String fileString) {
		this.fileString = fileString;
		fileName = fileString.substring(0, fileString.lastIndexOf("."));
		fileNameEnd = fileString.substring(fileString.lastIndexOf(".") + 1,
				fileString.length());
		docFile = new File(fileString);
		if (fileNameEnd.equals("PDF")) {
			pdfFile = new File(fileName + ".PDF");
		} else {
			pdfFile = new File(fileName + ".pdf");
		}
		swfFile = new File(fileName + ".swf");
	}

	/**
	 * 转为PDF
	 * 
	 * @param file
	 */
	private void doc2pdf() throws Exception {
		if (docFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
				try {
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(
							connection);
					converter.convert(docFile, pdfFile);
					// close the connection
					connection.disconnect();
					logger.info("****pdf转换成功，PDF输出：" + pdfFile.getPath()
							+ "****");
				} catch (java.net.ConnectException e) {
					logger.error("****pdf转换器异常，openoffice服务未启动！****");
					throw e;
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					logger.error("****pdf转换器异常，读取转换文件失败****");
					throw e;
				} catch (Exception e) {
					logger.error("****pdf转换器异常，读取转换文件失败****");
					throw e;
				}
			} else {
				logger.info("****已经转换为pdf，不需要再进行转化****");
			}
		} else {
			logger.info("****pdf转换器异常，需要转换的文档不存在，无法转换****");
		}
	}

	static String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();

		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}
		return buffer.toString();
	}

	/**
	 * 转换主方法
	 */
	public Map<String, Object> conver() {
		Map<String, Object> converTip = new HashMap<String, Object>();
		try {
			doc2pdf();
		} catch (Exception e) {
			e.printStackTrace();
			converTip.put("conver", false);
		}
		if (pdfFile.exists()) {
			converTip.put("conver", true);
		} else {
			converTip.put("conver", false);
		}
		return converTip;
	}

	/**
	 * 返回文件路径
	 * 
	 * @param s
	 */
	public String getswfPath() {
		if (swfFile.exists()) {
			String tempString = swfFile.getPath();
			tempString = tempString.replaceAll("\\\\", "/");
			return tempString;
		} else {
			return "";
		}
	}

}
