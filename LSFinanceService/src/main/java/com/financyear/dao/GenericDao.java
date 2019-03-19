package com.financyear.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, ID extends Serializable>{

	 public T save(T entity);
	 public T update(T entity);
	 public T saveOrUpdate(T entity);
	 public T delete(T entity);
	 public List<T> findByCriteria();
	 public List<?> findHqlListByParams(String query, Map<String, Object> params);
}
