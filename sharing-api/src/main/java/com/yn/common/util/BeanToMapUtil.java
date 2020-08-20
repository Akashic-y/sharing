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
 * @author zhangweidong
 * @version 3.0 Revise History:
 */
public class BeanToMapUtil {

    private static Log log = LogFactory.getLog(BeanToMapUtil.class);

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean 要转化的JavaBean对象
     * @return returnMap 转化出来的 Map对象
     */
    public static <T> Map<String, String> beanToMap(T bean) {

        Class<? extends Object> type = bean.getClass();
        Map<String, String> returnMap = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean);
                    returnMap.put(propertyName, result == null ? null : result.toString());
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            log.error(e);
        }
        return returnMap;
    }

    public static <T> Map<String, Object> beanToMapObject(T bean) {

        Class<?> type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class") && !propertyName.endsWith("Str")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean);
                    returnMap.put(propertyName, result);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            log.error(e);
        }
        return returnMap;
    }


    /**
     * 将一个Map对象转化为一个JavaBean
     *
     * @param paramMap 包含属性值的map
     * @param clazz    要转化的类型
     * @return beanObj 转化出来的JavaBean对象
     */
    public static <T> T mapToBean(Map<String, Object> paramMap, Class<T> clazz) {
        T beanObj = null;
        try {
            beanObj = clazz.newInstance();
            for (Map.Entry<String, Object> entity : paramMap.entrySet()) {
                String propertyName = entity.getKey();
                Object propertyValue = entity.getValue();
                setProperties(beanObj, propertyName, propertyValue);
            }
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
