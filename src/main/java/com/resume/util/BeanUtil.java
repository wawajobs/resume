package com.resume.util;

import java.util.ArrayList;
import java.util.List;

public class BeanUtil {
	
	/**
	 * 按照目标类名创建对象，并拷贝原对象属性
	 * 如果原对象为空，则将目标对象也置为空
	 * @param source 原对象
	 * @param targetClass 目标类名
	 */
	public static <T1 ,T2> T2 createCopy(T1 source,  Class<T2> targetClass){
		
		if(source==null){
			return null;
		}else{
			T2 target;
			try {
				target = targetClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
			CustomBeanUtils.copyVpsProperties(source, target);
			return target;
		}
	}
	
	/**
	 * 按照目标类名创建List，并拷贝原List中对象属性
	 * 如果原List为空，则返回为空
	 * @param sourceList 原对象List
	 * @param targetClass 目标类名
	 */
	public static <T1 ,T2> List<T2> createCopyList(List<T1> sourceList, Class<T2> targetClass){
		if(sourceList==null){
			return null;
		}else if(sourceList.size()==0){
			return new ArrayList<T2>();
		}else{
			List<T2> targetList=new ArrayList<T2>();
			for(Object source:sourceList){
				T2 target = (T2)createCopy(source,targetClass);
				targetList.add(target);
			}
			return targetList;
		}
	}
	
	public static <T1 ,T2> T2 createCopy(T1 source,  Class<T2> targetClass,String... ignorProperties){
		
		if(source==null){
			return null;
		}else{
			T2 target;
			try {
				target = targetClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
			CustomBeanUtils.copyVpsProperties(source, target,ignorProperties);
			return target;
		}
		
	}
}
