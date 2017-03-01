package com.mockuai.usercenter.core.filter;

import java.lang.reflect.Field;

import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.filter.conf.FilterDescBean;




public class FilterFactory {

	public static Filter createFilter(FilterDescBean bean) throws UserException{
		//BizFilter filter = null;
		Object obj = null; 
		try {
			Class<?> clazz = Class.forName(bean.getImplClass());
			//filter = (BizFilter)clazz.newInstance();
			obj = clazz.newInstance();
			for (String name : bean.getAttributeNames()) {
				Field fd = null;
				try{
					fd = clazz.getField(name);			
				}catch(Exception e){
					fd = clazz.getDeclaredField(name);
				}
				if (fd != null) {
					if (!fd.isAccessible()) fd.setAccessible(true); 
					setField(obj, fd, bean.getAttribute(name));
				}
			}
		} catch (Exception e) {
			throw new UserException(e);
		}
		return (Filter)obj;
	}
	
	
	private static void setField(Object instance, Field fd, String value) throws UserException {
		try {
			if (fd.getType().getCanonicalName().equals("int")) {
				fd.setInt(instance, Integer.parseInt(value));
			} else if (fd.getType().getCanonicalName().equals("long")) {
				fd.setLong(instance, Long.parseLong(value)); 
			} else if (fd.getType().getCanonicalName().equals("char")) {
				fd.setChar(instance, value.charAt(0));
			} else if (fd.getType().getCanonicalName().equals("byte")) {
				fd.setByte(instance,Byte.parseByte(value));
			} else if (fd.getType().getCanonicalName().equals("java.lang.String")) {
				fd.set(instance,value);
			} else if (fd.getType().getCanonicalName().equals("boolean")) {
				fd.setBoolean(instance,Boolean.parseBoolean(value));
			} else {
				throw new IllegalArgumentException("Unsupported configurable field type");
			}
		} catch (Exception e) {
			throw new UserException(e);
		}
	}
}
