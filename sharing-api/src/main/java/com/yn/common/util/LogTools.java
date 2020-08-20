package com.yn.common.util;

public class LogTools {
    /**
     * 获得文件名和行号。
     *
     * @param className 类名
     * @return 文件名和行号
     */
    public static String getFileNameAndLineNumber(String className) {
        String fname = null;
        int lineNumber = 0;
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        int ix = 0;
        while (ix < stack.length) {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();
            if (cname.equals(className)) {
                break;
            }
            ix++;
        }

        while (ix < stack.length) {
            StackTraceElement frame = stack[ix];
            String cname = frame.getClassName();
            if (!cname.equals(className)) {
                fname = frame.getFileName();
                lineNumber = frame.getLineNumber();
                break;
            }
            ix++;
        }

        if (null == fname) {
            return "Unknown] [";
        }

        if (lineNumber > 0) {
            return fname + ":" + lineNumber + "] [";
        } else {
            return fname + "] [";
        }
    }
}
