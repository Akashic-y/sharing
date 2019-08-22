package com.yn.common.entity;

import java.io.Serializable;

/**
 * 基础Entity
 *
 * @author yn
 * 2018年1月23日
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer id;

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity that = (BaseEntity) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
