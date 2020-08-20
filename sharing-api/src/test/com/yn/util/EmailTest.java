package com.yn.util;

import com.yn.SharingApiApplicationTests;
import com.yn.common.util.EmailUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailTest extends SharingApiApplicationTests {

    @Autowired
    EmailUtil emailUtil;

    @Test
    public void test() {
        String[] to = {"1678549524@qq.com"};
        String subject = "邮件主题";
        String text = "邮件内容";
        emailUtil.sendHtmlMail(to, subject, text);
    }
}
