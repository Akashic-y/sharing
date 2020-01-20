package com.yn.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
  * @ClassName: CFile 
  * @Description: 功能描述:本类主要用于文件的相关操作
  * @author YN
  * @date 2019年8月27日 下午5:51:38
 */
public class FileUtils {
	public static final byte DIRCREATE_ALL = 0x03;// 创建所有上级目录

	// 设置dircreatetype
	public static final byte DIRCREATE_NORMAL = 0x01;// 创建目录
	public static final byte DIRCREATE_PARENT = 0x02;// 父目录不存在创建父目录
	public static final byte DIRDEL_ALLCHILD = 0x03;// 删除所有子目录
	public static final byte DIRDEL_CHILD = 0x02;// 删除当前子目录
	// 设置dircreatetype
	public static final byte DIRDEL_NORMAL = 0x01;// 删除目录
	// 设置文件大小
	public static final long FILE_G = 10494541824L;// G,1024*1024*1024
	public static final long FILE_K = 1024;// K

	public static final long FILE_M = 1048576;// M,1024*1024
	public static final byte FILESIZE_B = 0x04;// B
	// 设置文件大小
	public static final byte FILESIZE_G = 0x01;// G
	public static final byte FILESIZE_K = 0x03;// K
	public static final byte FILESIZE_M = 0x02;// M

	// 创建目录
	public static boolean dircreate(String as_dirname, byte ab_dircreatetype) {
		try {
			File file = new File(as_dirname);
			if (file.exists())
				return true;
			switch (ab_dircreatetype) {
			case DIRCREATE_NORMAL:
				if (file.isDirectory()) {
					return file.mkdir();
				}
				break;
			case DIRCREATE_PARENT:
				File parent = file.getParentFile();
				if (!parent.exists()) {
					if (!parent.mkdir())
						return false;
				}
				return file.mkdir();
			case DIRCREATE_ALL:
				return file.mkdirs();
			}
		} catch (Exception ex) {
        }
		return false;
	}

	// 删除指定目录下指定类型文件
	public static boolean dirdelete(String as_dirname, byte ab_dirdeltype) {
		try {
			File file = new File(as_dirname);
			if (!file.exists())
				return false;
			boolean lb_result = true;
			File[] child = null;
			switch (ab_dirdeltype) {
			case DIRDEL_NORMAL:
				if (file.isDirectory()) {
					return file.delete();
				}
				break;
			case DIRDEL_CHILD:
				child = file.listFiles();
				for (File value : child) {
					if (!value.delete())
						lb_result = false;
				}
				if (lb_result) {
					return file.delete();
				}
				break;
			case DIRDEL_ALLCHILD:
				child = file.listFiles();
				for (File value : child) {
					if (value.isDirectory()) {
						if (!dirdelete(value.getPath(), ab_dirdeltype))
							lb_result = false;
					} else {
						if (!value.delete())
							lb_result = false;
					}
				}
				if (lb_result) {
					return file.delete();
				}
				break;
			}
		} catch (Exception ex) {
			
		}
		return false;

	}

	/** 文件拷贝（单个） */
	public static boolean filecopy(String from, String to) {
		try {
			// String ls_parent;
			// System.out.println(CDate.getcurrentdate()+":to:"+to+":is ok");
			// ls_parent=getparent(to);
			if (to.contains("/") || to.contains("\\")) {
				to = to.replaceAll("\\\\", "/");
				String toPath = to.substring(0, to.lastIndexOf("/")); // 提取文件路径
				// System.out.println(CDate.getcurrentdate()+":toPath:"+toPath+":is
				// ok");
				File f = new File(toPath); // 建立文件目录路
				if (!f.exists())
					f.mkdirs();
			}
			// System.out.println(CDate.getcurrentdate()+":ls_parent:"+ls_parent+":is
			// ok");

			// dircreate(ls_parent, DIRCREATE_ALL);
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(from));
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(to));
			int c;
			while ((c = bin.read()) != -1) // 复制
			bout.write(c);
			bin.close();
			bout.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 创建新文件
	public static boolean filecreate(String as_filename) {
		try {
			File file = new File(as_filename);
			if (!file.exists()) {
				return file.createNewFile();
			}
		} catch (Exception ex) {
        }
		return false;
	}

	// 创建新文件
	public static boolean filecreate(String as_filename, boolean ab_createdir) {
		try {
			if (ab_createdir) {
				File file = new File(as_filename);
				if (dircreate(file.getParent(), DIRCREATE_ALL)) {
					if (!file.exists()) {
						return file.createNewFile();
					}
				}
			} else {
				return filecreate(as_filename);
			}
		} catch (Exception ex) {
        }
		return false;
	}

	// 删除文件
	public static boolean filedelete(String as_filename) {
		try {
			File file = new File(as_filename);
			if (!file.exists())
				return false;
			return file.delete();
		} catch (Exception ex) {
        }
		return false;

	}

	// 判断文件是否存在
	public static boolean fileexist(String as_fullfilename) {
		try {
			File file = new File(as_fullfilename);
			if (file.exists())
				return true;
		} catch (Exception ex) {
		}
		return false;
	}

	/* 文件移动 */
	public static boolean filemove(String as_filename, String as_dir) {
		try {
			File file = new File(as_filename);
			String ls_filename = file.getName();
			filedelete(as_dir + ls_filename);
			return file.renameTo(new File(as_dir + ls_filename));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 逐行文件读取 */
	public static String[] fileread(LineNumberReader linenumberread, int ai_row1, int ai_row2) {
		try {
			String strLine;
			int li_row = 0;
			if (ai_row1 < 1)
				ai_row1 = 1;
			if (ai_row1 > ai_row2 && ai_row2 > 0)
				return new String[0];
			String[] ls_return = null;
			StringBuffer ls_return2 = new StringBuffer(100);
			li_row = linenumberread.getLineNumber() + 1;
			// linenumberread.setLineNumber(ai_row1);
			// li_row = ai_row1;
			while ((strLine = linenumberread.readLine()) != null) {
				if (li_row >= ai_row1) {
					ls_return2.append(strLine).append("/r/n");
					if (li_row == ai_row2 && ai_row2 > 0) {
						ls_return = ls_return2.toString().split("/r/n");
						return ls_return;
					}
				}
				li_row++;
			}
			if (ls_return2.toString().trim().equals("")) {
				ls_return = new String[0];
			} else {
				ls_return = ls_return2.toString().split("/r/n");
			}
			return ls_return;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String[0];

	}

	/** 逐行文件读取 */
	public static String[] fileread(String as_file, int ai_row1, int ai_row2) {
		/*
		 * 读取文件 as_file:文件 ai_row1：开始行 ai_row2:中止行
		 */
		LineNumberReader linenumberread = null;
		try {
			linenumberread = new LineNumberReader(new FileReader(as_file));
			String[] ls_return = fileread(linenumberread, ai_row1, ai_row2);
			return ls_return;
		} catch (Exception ex) {
		} finally {
			try {
				if (linenumberread != null) {
					linenumberread.close();
				}
			} catch (Exception ex) {
			}
		}
		return new String[0];
	}

	// 删除文件
	public static boolean fileRename(String as_filename, String as_filename2) {
		try {
			File file = new File(as_filename);
			if (!file.exists())
				return false;
			return file.renameTo(new File(as_filename2));
		} catch (Exception ex) {
        }
		return false;

	}

	// 文件大小转换
	public static double filesize(double ad_size, byte lb_filesize1, byte lb_filesize2) {
		switch (lb_filesize1) {
		case FILESIZE_G:
			ad_size = ad_size * 1024 * 1024 * 1024;
			break;
		case FILESIZE_M:
			ad_size = ad_size * 1024 * 1024;
			break;
		case FILESIZE_K:
			ad_size = ad_size * 1024;
			break;
		case FILESIZE_B:
			break;
		}
		switch (lb_filesize2) {
		case FILESIZE_G:
			ad_size = ad_size / 1024 / 1024 / 1024;
			break;
		case FILESIZE_M:
			ad_size = ad_size / 1024 / 1024;
			break;
		case FILESIZE_K:
			ad_size = ad_size / 1024;
			break;
		case FILESIZE_B:
			break;
		}
		return ad_size;
	}

	// 获得文件大小
	public static long filesize(String as_filename) {
		try {
			File file = new File(as_filename);
			if (file.exists()) {
				return file.length();
			}
		} catch (Exception ex) {
        }
		return -1;
	}

	// 获得文件大小
	public static double filesize(String as_filename, byte lb_filesize) {
		double ld_filesize = 0;
		ld_filesize = filesize(as_filename);
		return filesize(ld_filesize, FILESIZE_B, lb_filesize);
	}

	/** 文件写入 */
	public static boolean filewrite(String as_filename, String as_nr) {
		try {
			// BufferedOutputStream bout = new BufferedOutputStream(new
			// FileOutputStream(as_filename));
			// bout.write(as_nr.getBytes());
			// bout.close();
			PrintWriter write = new PrintWriter(as_filename, "GBK");
			write.write(as_nr);
			write.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 获得指定目录下所有文件
	public static String[] getChilds(String dir) throws IOException {
		String[] larray_rtn = null;
		File[] fileList;
		File file = new File(dir);
		fileList = file.listFiles();
		if (fileList != null) {
			larray_rtn = new String[fileList.length];
			for (int i = 0; i < fileList.length; i++) {
				larray_rtn[i] = fileList[i].getPath();
			}
		}
		return larray_rtn;
	}

	// 当前系统的所有盘符
	public static String[] getdrive() {
		File[] roots = File.listRoots();
		String[] ls_return = new String[roots.length];
		for (int i = 0; i < roots.length; i++) {
			ls_return[i] = roots[i].getPath();
			// if(ls_return[i].endsWith("\\")){
			// ls_return[i]=ls_return[i].substring(0,ls_return[i].length() - 1);
			// }
			// System.out.print(ls_return[i]);
		}
		return ls_return;
	}

	// 获得文件
	public static File getFile(String filePath) {
		File file = new File(filePath);
		return file;
	}

	// 获得文件名
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	// 获得文件流
	public static PrintWriter getFilewrite(String as_filename, PrintWriter filewriter) {
		try {
			File l_file = new File(as_filename);
			if (!l_file.exists()) {
				l_file.createNewFile();
				l_file = new File(as_filename);
				filewriter = new PrintWriter(new BufferedWriter(new FileWriter(l_file, true)));
			} else {
				if (filewriter == null)
					filewriter = new PrintWriter(new BufferedWriter(new FileWriter(l_file, true)));
			}
		} catch (Exception ex) {
			filewriter = null;
		}
		return filewriter;
	}

	// 获得网页中的连接,str-网页内容,cp-正则表达,s-分隔符
	public static String getlink(String str, String cp, String s) {
		if (str == null || str.equals(""))
			return "";
		Pattern p = Pattern.compile(cp, 2); // 参数2表示大小写不区分
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		// 使用循环将句子里所有匹配的内容找出并替换再将内容加到sb里
		while (result) {
			sb.append(m.group());
			sb.append(s);
			// 继续查找下一个匹配对象
			result = m.find();
		}
		str = String.valueOf(sb);
		return str;
	}

	// 获得父目录
	public static String getparent(String as_dirname) {
		try {
			File file = new File(as_dirname);
			if (!file.exists())
				return "";
			String ls_parent = file.getParent();
			if (ls_parent.endsWith(File.separator)) {
				return ls_parent;
			}
			return ls_parent + File.separator;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	// 获得网页的内容
	public static String getwebcontent(String domain) {
		StringBuffer sb = new StringBuffer();
		try {
			java.net.URL url = new java.net.URL(domain);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) { // Report any errors that arise
			sb.append(e.toString());
		}
		return sb.toString();
	}

	// 控制台输入
	public static String inputString() {
		BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
		try {
			s = bufferedreader.readLine();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		return s;
	}

	// 是否为utf-8编码
	public static boolean isutf8(String as_filename) {
		java.io.File f = new java.io.File(as_filename);
		try {
			java.io.InputStream ios = new java.io.FileInputStream(f);
			byte[] b = new byte[3];
			ios.read(b);
			ios.close();
			if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 遍历目录下所有文件
	public static int listDir(String dir, ArrayList<String> listFile) throws IOException {
		int result = 0;
		File file = new File(dir);
		File[] fileList;
		if (!file.isDirectory()) {
			result = -1;
		} else {

			fileList = file.listFiles();
			for (File value : fileList) {
				if (value.isDirectory()) {
					listDir(value.getPath(), listFile);
				} else {
					listFile.add(value.getPath());
				}
			}
		}
		return result;
	}

	/* 判断文件是否被其它程序操作，如无则返回真 */
	public static boolean pdfilecanread(File file) {
		String strTempName = file.getName();
		boolean blFile = false;
		boolean blTempFile = false;
		if (file.isFile()) {
			File fileTemp = new File(strTempName + "_temp");
			blTempFile = file.renameTo(fileTemp);
			// 利用Ｒｅｎａｍｅｔｏ方法判断，如ＲｅｎａｍｅＴｏ成功则文件没被操作
			if (fileTemp.exists() && blTempFile) {
				blFile = true;
				fileTemp.renameTo(file);
			}
		}
		return blFile;
	}

	// ini文件读取
	public static int readinifile(String as_inifile, String as_1, String as_2, StringBuffer as_return) {
		/*
		 * 读取ini文件 as_inifile:ini文件全路径 as_1：主节点 as_2:子节点
		 */
		BufferedReader bufferedReader = null;
		try {
			String strLine, value = "";
			bufferedReader = new BufferedReader(new FileReader(as_inifile));
			boolean isInSection = false;
			while ((strLine = bufferedReader.readLine()) != null) {
				strLine = strLine.trim();
				strLine = strLine.split("[;]")[0];
				Pattern p;
				Matcher m;
				p = Pattern.compile("\\[\\s*.*\\s*\\]");
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile("\\[\\s*" + as_1 + "\\s*\\]");
					m = p.matcher(strLine);
                    isInSection = m.matches();
				}
				if (isInSection) {
					strLine = strLine.trim();
					String[] strArray = strLine.split("=");
					if (strArray.length == 1) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(as_2)) {
							value = "";
							as_return.append(value);
							return 1;
						}
					} else if (strArray.length == 2) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(as_2)) {
							value = strArray[1].trim();
							as_return.append(value);
							return 1;
						}
					} else if (strArray.length > 2) {
						value = strArray[0].trim();
						if (value.equalsIgnoreCase(as_2)) {
							value = strLine.substring(strLine.indexOf("=") + 1).trim();
							as_return.append(value);
							return 1;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.getMessage();
            return -1;
		}finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	// 读取远程文件保存到本地
	public static boolean readhttpfile(String as_httpfile, String as_localfilename) {
		try {
			FileOutputStream fos = new FileOutputStream(as_localfilename);
			URL url = new URL(as_httpfile);
			InputStream is = url.openStream();
			byte[] b = new byte[4096];
			while (true) {
				int i = is.read(b);
				if (i == -1)
					break;
				fos.write(b);
			}
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	// 运行外部命令
	public static Process run(String as_cmd, StringBuffer as_error) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(as_cmd);
		} catch (Exception ex) {
			ex.printStackTrace();
			as_error.append(ex.getMessage());
			return null;
		}
		return p;
	}

	// 设置文件的最后修改日期
	public static boolean setlastmodifydate(File file, Date adt_rq) {
		long L = adt_rq.getTime();
		return file.setLastModified(L);
	}

	/* 写ini文件 */
	public static int writeinifile(String as_inifile, String as_1, String as_2, String as_value) {
		BufferedReader bufferedReader = null;
		try {
			StringBuilder fileContent;
			String allLine;
			String strLine;
			String newLine;
			String remarkStr;
			String getValue;
			bufferedReader = new BufferedReader(new FileReader(as_inifile));
			boolean isInSection = false;
			fileContent = new StringBuilder();

			while ((allLine = bufferedReader.readLine()) != null) {
				allLine = allLine.trim();
				if (allLine.split("[;]").length > 1)
					remarkStr = ";" + allLine.split(";")[1];
				else
					remarkStr = "";
				strLine = allLine.split(";")[0];
				Pattern p;
				Matcher m;
				p = Pattern.compile("\\[\\s*.*\\s*\\]");
				m = p.matcher((strLine));
				if (m.matches()) {
					p = Pattern.compile("\\[\\s*" + as_1 + "\\s*\\]");
					m = p.matcher(strLine);
                    isInSection = m.matches();
				}
				if (isInSection) {
					strLine = strLine.trim();
					String[] strArray = strLine.split("=");
					getValue = strArray[0].trim();
					if (getValue.equalsIgnoreCase(as_2)) {
						newLine = getValue + " = " + as_value + " " + remarkStr;
						fileContent.append(newLine).append("\r\n");
						while ((allLine = bufferedReader.readLine()) != null) {
							fileContent.append(allLine).append("\r\n");
						}
						bufferedReader.close();
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(as_inifile, false));
						bufferedWriter.write(fileContent.toString());
						bufferedWriter.flush();
						bufferedWriter.close();
						return 1;
					}
				}
				fileContent.append(allLine).append("\r\n");
			}
			return -1;
		} catch (Exception ex) {
			ex.getMessage();
			return -1;
		}finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 获得当前类的路径
	public String getPath() {
		return this.getClass().getClassLoader().getResource(".").getPath();
	}

}
