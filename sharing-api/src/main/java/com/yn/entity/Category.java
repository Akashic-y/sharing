package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Category extends BaseEntity{

	private String avatar;

	private String categoryname;

	private String description;

}