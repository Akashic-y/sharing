package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "access_ip")
public class AccessIp extends BaseEntity {

    //ip地址
    private String ip;

    //第一次访问时间
    private Date createDate;

    //更新时间
    private Date updateDate;

    //访问次数
    private int times;

    //请求的URL地址
    private String requestUrl;

    //来访者的IP地址
    private String remoteAddr;

    //访问资源
    private String requestUri;

    private String remoteHost;

    private int remotePort;

    //浏览器类型
    private String agent;

    //通过ip获取的地址
    private String address;

    //定位
    private String position;

    private String sessionId;
}
