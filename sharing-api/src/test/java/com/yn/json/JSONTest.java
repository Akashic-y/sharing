package com.yn.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.session.mgt.SimpleSession;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tk.mybatis.mapper.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JSONTest{
	@Test
	public void setTest() {
		SimpleSession s = new SimpleSession();
		s.setId(123456);
		int features = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.SkipTransientField, false);
		int features2 = SerializerFeature.config(features, SerializerFeature.WriteClassName, true);
		String ss = JSON.toJSONString(s, features2);
		System.out.println(ss);
		SimpleSession s2 = JSON.parseObject(ss,SimpleSession.class);
		System.out.println(s2.getId());
	}

	@Test
	public void testJson(){
		String path = "C:\\Users\\Administrator\\Desktop\\日常文件\\new.txt";
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line;
			StringBuffer sb = new StringBuffer();
			while (StringUtil.isNotEmpty(line = br.readLine())){
				sb.append(line);
			}
			JSONArray jsonArray = JSON.parseArray(sb.toString());
			JSONObject[] js = new JSONObject[jsonArray.size()];
			jsonArray.toArray(js);
			Map<String,Object> smap = new HashMap<>();
			Map<String,Object> emap = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (JSONObject j : js) {
				String authorName = j.getString("author_name");
				String date = j.getString("date");
				Date temp = sdf.parse(date);
				if(smap.containsKey(authorName)){
					String start = smap.get(authorName).toString();
					String end = emap.get(authorName).toString();
					Date s = sdf.parse(start);
					Date e = sdf.parse(end);
					if(s.after(temp)){
						smap.put(authorName,date);
					}
					if(e.before(temp)){
						emap.put(authorName,date);
					}
				}else {
					smap.put(authorName,date);
					emap.put(authorName,date);
				}
			}
			System.out.println(smap);
			System.out.println(emap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
