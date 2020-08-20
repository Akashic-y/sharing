package com.yn.redis;

import com.yn.common.util.RedisUtil;
import com.yn.entity.ArticleBody;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yn.SharingApiApplicationTests;
import com.yn.common.cache.RedisManager;

import java.util.ArrayList;
import java.util.List;

public class RedisManagerTest extends SharingApiApplicationTests {

    @Autowired
    private RedisManager redisManager;

    @Test
    public void setTest() {
        String k = "zzz";
        String v = "123789";
        redisManager.set(k, v);
    }

    @Test
    public void getTest() {
        String k = "zzz";
        String v = redisManager.get(k, String.class);
        System.out.println(v);
    }

    @Test
    public void deleteTest() {
        String k = "zzz";
        redisManager.delete(k);
        String v = redisManager.get(k, String.class);
        System.out.println(v);
    }

    @Test
    public void test() {
        RedisUtil.set("www", "hello redis");
        String www = (String) RedisUtil.get("www");
        System.out.println(www);
//        boolean r = RedisUtil.exists("www");
//        System.out.println(r);
//        RedisUtil.del("www");
//        r = RedisUtil.exists("www");
//        System.out.println(r);
        //List test
        List<String> list = new ArrayList<>();
        list.add("asd");
        list.add("bbb");
        list.add("ccc");
        RedisUtil.setList("list", list);
        List<String> rs = RedisUtil.getListString("list");
        System.out.println(rs);
        List<ArticleBody> ls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArticleBody a = new ArticleBody();
            a.setContent(i + "-------Content");
            a.setContentHtml(i + "----html");
            ls.add(a);
        }
        RedisUtil.setList("list2", ls);
        List<ArticleBody> rs2 = RedisUtil.getListEntity("list2", ArticleBody.class);
        System.out.println(rs2);
    }
}
