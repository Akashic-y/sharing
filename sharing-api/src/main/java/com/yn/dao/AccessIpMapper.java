package com.yn.dao;

import com.yn.entity.AccessIp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AccessIpMapper extends Mapper<AccessIp> {
	public int existIp(String ip);

	void updateByIp(@Param("ip") String ip,@Param("sessionId") String sessionId);

	void updatePosition(AccessIp accessIp);
}