package com.yn.common.util;

public class MavenUtil {

    /**
     * @Desc Maven手动添加jar包
     * @Author yn
     * @Date 11:28 2019/12/27 0027
     */
    public static void addJar(String jarPath, String groupId, String artifactId, String version){
        String cmd = "mvn install:install-file -Dfile=" + jarPath + " -DgroupId=" + groupId +
                " -DartifactId=" + artifactId + " -Dversion=" + version + " -Dpackaging=jar";
        String execCmdResult = CommonTools.getExecCmdResult(cmd);
        System.out.println(execCmdResult);
    }

    public static void main(String[] args) {
        String jarPath = "E:\\20190904\\rcrp-cloud\\WebRoot\\WEB-INF\\lib\\pinyin4j-2.5.0.jar";
        String groupId = "com.github.open-android";
        String artifactId = "pinyin4j";
        String version = "2.5.0";
        addJar(jarPath,groupId,artifactId,version);
    }
}
