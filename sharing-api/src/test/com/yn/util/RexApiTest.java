package com.yn.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

public class RexApiTest {
    public static void main(String[] args) {
        startMeetingSchedule();
    }

    private static void createMeeting() {
        RequestMsgXML msgXML = new RequestMsgXML("10008",
                "<Meeting>" +
                        "<MeetingTopic></MeetingTopic>" +
                        "<MeetingAuthor></MeetingAuthor>" +
                        "<RecordAlias>1441</RecordAlias>" +
                        "<IsDirect>1</IsDirect>" +
                        "<BeginTime>2020-08-18 11:12:00</BeginTime>" +
                        "<EndTime>2020-08-18 12:51:00</EndTime>" +
                        "<IsUnitCast>1</IsUnitCast>" +
                        "<IsRecord>0</IsRecord>" +
                        "<CameraList>" +
                        "<Encoder>" +
                        "<Ip>192.168.3.151</Ip>" +
                        "<Type>0</Type>" +
                        "<Username></Username>" +
                        "<UserPwd></UserPwd>" +
                        "<Port></Port>" +
                        "</Encoder>" +
                        "</CameraList>" +
                        "<IsPublic>1</IsPublic>" +
                        "<MeetingLevel>1</MeetingLevel>" +
                        "<MeetingType>1</MeetingType>" +
                        "<CallNum>172.16.30.122</CallNum>" +
                        "<CallBitrate>3</CallBitrate>" +
                        "<Remark></Remark>" +
                        "</Meeting>");
        post(msgXML.getXML());
    }

    private static void stopMeeting() {
        RequestMsgXML msgXML = new RequestMsgXML("10009",
                "<RoomIds>0,1</RoomIds>");
        post(msgXML.getXML());
    }

    private static void meetingScheduleList() {
        RequestMsgXML msgXML = new RequestMsgXML("10010",
                "<GetScheduleListReq><CurrPage>3</CurrPage><PageCount>1</PageCount></GetScheduleListReq>");
        post(msgXML.getXML());
    }
    private static void startMeetingSchedule() {
        RequestMsgXML msgXML = new RequestMsgXML("10011",
                "<StartScheduleReq><ScheduleId>21725350e4cd47418db14515cf461c1c</ScheduleId></StartScheduleReq>");
        post(msgXML.getXML());
    }

    private static int post(String xml) {
        PostMethod post = new PostMethod("http://192.168.3.119/HttpService.action");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        try {
            RequestEntity entity = new StringRequestEntity(xml, "text/xml", "utf-8");
            post.setRequestEntity(entity);
            HttpClient client = new HttpClient();
            int status = client.executeMethod(post); // 执行，模拟POST方法提交到服务器
            String returnData = post.getResponseBodyAsString();
            System.out.println("finish push return code:" + status);
            System.out.println("finish push return data:" + returnData);
            return status;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return 200;
    }
}

class RequestMsgXML {

    private String msgHead;
    private String msgBody;
    private String msgCode;
    private String passKey = "MediaCenter";

    public String getMsgHead() {
        return msgHead;
    }

    public void setMsgHead(String msgHead) {
        this.msgHead = msgHead;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public RequestMsgXML(String msgCode, String msgBody) {
        super();
        // this.msgHead = msgHead;
        this.msgBody = msgBody;
        this.msgCode = msgCode;
        // this.passKey = passKey;
    }

    public RequestMsgXML(String msgCode, String msgBody, String passKey) {
        super();
        // this.msgHead = msgHead;
        this.msgBody = msgBody;
        this.msgCode = msgCode;
        this.passKey = passKey;
    }

    public String getXML() {
        StringBuffer headxml = new StringBuffer();
        // headxml.append("<MsgHead>");
        headxml.append("<MsgCode>").append(this.msgCode).append("</MsgCode>")
                .append("<PassKey>" + this.passKey + "</PassKey>");
        // headxml.append("</MsgHead>");

        StringBuffer buff = new StringBuffer();
        buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<RequestMsg>").append("<MsgHead>")
                .append(headxml.toString())
                .append("</MsgHead>")
                .append("<MsgBody>")
                .append(this.msgBody)
                .append("</MsgBody>").append("</RequestMsg>");

        return buff.toString();
    }

}