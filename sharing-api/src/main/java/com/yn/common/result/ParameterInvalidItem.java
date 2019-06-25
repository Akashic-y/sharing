package com.yn.common.result;

/**
 * Controller参数校验 错误返回封装
 *
 * @author yn
 * <p>
 * 2018年1月23日
 */
public class ParameterInvalidItem {

    private String fieldName;

    private String message;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
