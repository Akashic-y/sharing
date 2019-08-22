package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Tag extends BaseEntity{

	private static final long serialVersionUID = -8703756043484996111L;

	private Integer id;
	
	private String avatar;

	private String tagname;

}