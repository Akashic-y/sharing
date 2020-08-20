package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "me_tag")
public class Tag extends BaseEntity {

    /**
     * 图片路径
     */
    private String avatar;

    private String tagname;

    private Integer count;
}