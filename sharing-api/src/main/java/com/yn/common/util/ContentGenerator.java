package com.yn.common.util;

import freemarker.template.*;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * FreeMarker内容生成器
 * 
 */
public class ContentGenerator {
	private String templateFolder;
	private Configuration cfg;
	
	/**
	 * 默认构造方法
	 * 设置默认模板文件夹为 WEB-INF/templates
	 */
	public ContentGenerator() {
		this.templateFolder = "WEB-INF/templates";
		initCfg();
	}

	/**
	 * 生成器构造方法
	 * 
	 * @param templateFolder
	 *            模板存放目录 基于webroot<br />
	 *            <strong>例如</strong>模板目录是webroot/WEB-INF/templates
	 *            则应传入templateFolder的参数为"WEB-INF/templates"
	 */
	public ContentGenerator(String templateFolder) {
		this.templateFolder = templateFolder;
		initCfg();
	}

	private void initCfg() {
		cfg = new Configuration();
		String path = getClass().getClassLoader().getResource("/").getPath();
		String webrootPath = new File(path).getParentFile().getParentFile()
				.getPath();

		File file = new File(webrootPath + File.separator + this.templateFolder);
		try {
			cfg.setDirectoryForTemplateLoading(file);
			cfg.setTemplateUpdateDelay(0);
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
			cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			cfg.setDefaultEncoding("UTF-8");
			cfg.setOutputEncoding("UTF-8");
			cfg.setLocale(Locale.CHINA);
		} catch (IOException e) {
			Log.warnStackTrace(e);
		}
	}

	public ContentGenerator(ServletContext contex, String templateFolder) {
		this.templateFolder = templateFolder;
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(contex, this.templateFolder);
		cfg.setTemplateUpdateDelay(0);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setOutputEncoding("UTF-8");
		cfg.setLocale(Locale.CHINA);
	}

	/**
	 * 根据模板生成内容
	 * 
	 * @param templateName
	 *            模板名称
	 * @param data
	 *            填充模板的数据模型
	 * @return 生成内容
	 * @throws IOException
	 */
	public String generatContent(String templateName, Object data)
			throws IOException {

		String content = null;
		Template template = cfg.getTemplate(templateName);

		Writer out = new StringWriter();

		try {
			template.process(data, out);
			Log.info("generatContent:\n\r" + out.toString());
			content = out.toString();
			out.flush();
			out.close();
		} catch (TemplateException e) {
			Log.warnStackTrace(e);
		}
		return content;
	}

	/**
	 * 根据模板生成文件
	 * 
	 * @param templateName
	 *            模板名称
	 * @param filePath
	 *            文件存放路径
	 * @param fileName
	 *            文件名称
	 * @param data
	 *            填充模板的数据模型
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void generatFile(String templateName, String filePath,
			String fileName, Object data) throws IOException, TemplateException {
		if (StringUtils.isEmpty(templateName)) {
			throw new NullArgumentException("无效模板名称");
		}
		Template template = cfg.getTemplate(templateName);
		File file = new File(filePath + File.separator + fileName);
		OutputStream os = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
		template.process(data, osw);
		osw.flush();
		osw.close();
		Log.info("generat File[" + file.getAbsolutePath() + "] success");
	}
}
