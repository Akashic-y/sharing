package com.yn.util;

import com.yn.common.util.BeanToMapUtil;
import com.yn.entity.Tag;
import org.junit.Test;

import java.util.Map;

public class UtilTest {
    @Test
    public void BeanMap(){
        Tag tag = new Tag();
        tag.setAvatar("www");
        tag.setTagname("twww");
        tag.setCount(123);
        Map<String, Object> map = BeanToMapUtil.beanToMapObject(tag);
        Tag mapToBean = BeanToMapUtil.mapToBean(map, Tag.class);
        System.out.println(mapToBean);
        System.out.println(map);
    }
}
