package com.resume.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
  
public class CustomBeanUtils extends org.springframework.beans.BeanUtils {  
  
	public static void copyVpsProperties(Object source, Object target) {
		copyVpsProperties(source, target, null);
	}

	public static void copyVpsProperties(Object source, Object target,String[] ignoreProperties) throws BeansException {
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays
				.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null
					&& (ignoreProperties == null || (!ignoreList
							.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method tarReadMethod = targetPd.getReadMethod();
							Method writeMethod = targetPd.getWriteMethod();
							Class<?> returnType = tarReadMethod.getReturnType();
							if(value instanceof String && !returnType.isInstance(value)){
								//如果数据源是字符串类型，并且target不是字符串
								continue;
							}else if (!isWrapClass(returnType) && !returnType.isInstance(value)) {
								Object newInstance = returnType.newInstance();
								copyVpsProperties(value, newInstance);
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, newInstance);
							}else{
								
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}

						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}  
	

    public static boolean isWrapClass(Class<?> clz) { 
        try { 
           return clz.isPrimitive() || (((Class<?>) clz.getField("TYPE").get(null)).isPrimitive())  ;
        } catch (Exception e) { 
            return false; 
        } 
    } 
}