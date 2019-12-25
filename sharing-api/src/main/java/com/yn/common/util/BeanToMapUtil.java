package com.yn.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO class purpose description
 * 
 * @author zhangweidong
 * @version 3.0 Revise History:
 * 
 */
public class BeanToMapUtil {

    private static Log log = LogFactory.getLog(BeanToMapUtil.class);

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     * 
     * @param bean
     *            要转化的JavaBean对象
     * @return returnMap 转化出来的 Map对象
     */
    public static <T> Map<String, String> beanToMap(T bean) {

        Class<? extends Object> type = bean.getClass();
        Map<String, String> returnMap = new HashMap<String, String>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    returnMap.put(propertyName, result == null ? null : result.toString());
                }
            }
        } catch (IntrospectionException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        } catch (InvocationTargetException e) {
            log.error(e);
        }
        return returnMap;
    }

    public static <T> Map<String, Object> beanToMapObject(T bean) {

        Class<? extends Object> type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class") && !propertyName.endsWith("Str")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    returnMap.put(propertyName, result);
                }
            }
        } catch (IntrospectionException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        } catch (InvocationTargetException e) {
            log.error(e);
        }
        return returnMap;
    }

    
    /**
     * 将一个Map对象转化为一个JavaBean
     * 
     * @param map
     *            包含属性值的map
     * @param bean
     *            要转化的类型
     * @return beanObj 转化出来的JavaBean对象
     */
    public static <T> T mapToBean(Map<String, Object> paramMap, Class<T> clazz) {
        T beanObj = null;
        try {
            beanObj = clazz.newInstance();
            String propertyName = null;
            Object propertyValue = null;
            for (Map.Entry<String, Object> entity : paramMap.entrySet()) {
                propertyName = entity.getKey();
                propertyValue = entity.getValue();
                setProperties(beanObj, propertyName, propertyValue);
            }
        } catch (IllegalArgumentException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return beanObj;
    }

    public static <T> void setProperties(T entity, String propertyName, Object value) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, entity.getClass());
        Method methodSet = pd.getWriteMethod();
        methodSet.invoke(entity, value);
    }

}
