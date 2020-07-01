package com.yn.util;


import com.yn.common.util.QrCodeUtils;
import java.io.*;

public class QrCodeTest {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("d:\\code.png");
        FileOutputStream fw = new FileOutputStream(file);
        QrCodeUtils.writeToStream("张逗比", null, fw,null, null);
    }
}
