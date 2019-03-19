package com.financyear.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.GenericDao;

@Transactional(propagation = Propagation.MANDATORY)
public class GenericDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDao<T, ID> {
	protected final Class<T> entityType;
	
	protected GenericDaoImpl(Class<T> entityType) {
		this.entityType = entityType;
	}
	@Autowired
	protected void bindSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	public T save(T entity) {
		getHibernateTemplate().save(entity);
		return entity;
	}
	
	public T update(T entity){
		getHibernateTemplate().update(entity);
		return entity;
	}
	public T saveOrUpdate(T entity){
		getHibernateTemplate().merge(entity);
		return entity;
	}
	public T delete(T entity){
		getHibernateTemplate().delete(entity);
		return entity;
	}
	public List<T> findByCriteria(){
		
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(entityType);
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
	public List<?> findHqlListByParams(final String query, final Map<String, Object> params) {
		
		List<?> entities = null;
		
		entities = (List<?>) getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(query);
				if(params!=null){
					for (Map.Entry<String, Object> entry : params.entrySet()) {
						queryObject.setParameter(entry.getKey(), entry.getValue());
					}
				}
				return queryObject.list();
			}
			
		});
				
		
		return entities;
	}
	 

	 
}
