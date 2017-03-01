package com.mockuai.tradecenter.core.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;


public class DozerBeanService{
	
	private Mapper mapper;
	
	public <T,V> V cover( T t, Class<V> v) {
		return mapper.map(t, v);
	}
	
	
	@SuppressWarnings("unchecked")
	public <T,V> V cover(T t, V v) {
		return (V) mapper.map(t, v.getClass());
	}
	
	
	public <T,V> List<V> coverList(List<T> t, Class<V> v) {
		List<V> list = new ArrayList<V>();
		for (T temp : t) {
			list.add((V) mapper.map(temp, v));
		}
		return list;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	
	
}
