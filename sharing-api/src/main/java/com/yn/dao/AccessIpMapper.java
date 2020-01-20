package com.yn.dao;

import com.yn.entity.AccessIp;
import tk.mybatis.mapper.common.Mapper;

public interface AccessIpMapper extends Mapper<AccessIp> {
	public int existIp(String ip);

	void updateByIp(String nip);
}