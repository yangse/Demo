package com.jandar.frame.base.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.jandar.frame.base.action.BaseAction;
import com.jandar.frame.base.dao.BaseDaoImpl;
import com.jandar.frame.base.dao.IBaseDao;
import com.jandar.frame.constant.ValidFlag;
import com.jandar.frame.support.CustomExample;
import com.jandar.frame.support.PaginationSupport;
import com.jandar.frame.support.PropertyFilter;

public class BaseServiceImpl<T> implements IBaseService<T> {

	protected static final long serialVersionUID = 1L;

	protected final Log log = LogFactory.getLog(BaseAction.class);

	@Autowired(required = true)
	protected SessionFactory sessionFactory;
	
	private Class<T> entityClass;

	private IBaseDao<T> baseDao;

	public BaseServiceImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void delete(T entity) {
		getLocalDao().delete(entity);
	}

	public void deleteAll(Collection<T> entities) {
		getLocalDao().deleteAll(entities);
	}

	public void delete(Long id) {
		getLocalDao().delete(id);
	}

	public void deleteAll(List<Long> idToDelete) {
		getLocalDao().deleteAll(idToDelete);
	}

	public void invalid(T entity) {
		try {
			Method method = entity.getClass().getMethod("setValidFlag",
					ValidFlag.class);
			method.invoke(entity, ValidFlag.INVALID);
			this.saveOrUpdate(entity);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
	}

	public void invalidAll(List<Long> idToDelete) {
		for (Long id : idToDelete) {
			T entity = findById(id);
			try {
				Method method = entity.getClass().getMethod("setValidFlag",
						ValidFlag.class);
				method.invoke(entity, ValidFlag.INVALID);
				this.saveOrUpdate(entity);
			} catch (Exception e) {
				log.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	public void saveOrUpdateOther(T entity){
		getLocalDao().saveOrUpdateOther(entity);
	}
	
	public void saveOrUpdate(T entity) {
		getLocalDao().saveOrUpdate(entity);
	}
	
	public void merge(T entity) {
		getLocalDao().merge(entity);
	}

	public void saveOrUpdateAll(Collection<T> entities) {
		getLocalDao().saveOrUpdateAll(entities);
	}

	public T findById(Serializable id) {
		return getLocalDao().findById(id);
	}

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize) {
		return getLocalDao().findPageByExample(example, startIndex, pageSize);
	}
	
	public PaginationSupport<T> findPageByExampleFilter(
			final CustomExample<T> example, final List<PropertyFilter> filters, final int startIndex,
			final int pageSize) {
		return getLocalDao().findPageByExampleFilter(example, filters, startIndex, pageSize);
	}
	
	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize,final Projection p) {
		return getLocalDao().findPageByExample(example, startIndex, pageSize,p);
	}
	
	
	public PaginationSupport<T> findPageByList(List<T> list,int totalCount,int startIndex,
			int pageSize) {
		return getLocalDao().findPageByList(list,totalCount, startIndex, pageSize);
	}

	public void flush() {
		getLocalDao().flush();
	}

	public void clear() {
		getLocalDao().clear();
	}

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final Order[] orders,
			final int startIndex, final int pageSize) {
		return getLocalDao().findPageByExample(example, orders, startIndex,
				pageSize);
	}
	
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int startIndex,
			final int pageSize){
		return getLocalDao().findPageByCriteria(detachedCriteria,startIndex,pageSize);
	}
	
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final Order[] orders,
			final int startIndex, final int pageSize){
		return getLocalDao().findPageByCriteria(detachedCriteria, orders, startIndex, pageSize);
	}
	
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria,
			final int max){
		
		return getLocalDao().findAllByCriteria(detachedCriteria, max);
	}
	
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria){
		
		return getLocalDao().findAllByCriteria(detachedCriteria);
	}
	
	public List<T> findAllByFilter(final List<PropertyFilter> filters){
		
		return getLocalDao().findAllByFilter(filters);
	}

	public int countByExample(final CustomExample<T> example) {
		return getLocalDao().countByExample(example);
	}

	public IBaseDao<T> getLocalDao() {
		if (baseDao == null) {
			BaseDaoImpl<T> temp = new BaseDaoImpl<T>(entityClass);
			temp.setSessionFactory(sessionFactory);
			baseDao = temp;
		}
		return baseDao;
	}

	public List<T> findAll() {
		return getLocalDao().findAllByCriteria();
	}

	public List<T> findAllValid() {
		DetachedCriteria criterion = DetachedCriteria
				.forClass(this.entityClass);
		criterion.add(Restrictions.eq("validFlag", ValidFlag.VALID));
		return this.getLocalDao().findAllByCriteria(criterion);
	}

	public <T2> IBaseDao<T2> getDao(Class<T2> entityClass) {
		BaseDaoImpl<T2> temp = new BaseDaoImpl<T2>(entityClass);
		temp.setSessionFactory(sessionFactory);
		return temp;
	}

	public List<T> findByKeyValuePair(Object... keyValuePair) {
		DetachedCriteria criterion = DetachedCriteria
				.forClass(this.entityClass);
		Integer idx = 1;
		String key = "";
		Object value = null;
		for (Object obj : keyValuePair) {
			if (idx % 2 == 1) {
				key = obj.toString();
				idx++;
				continue;
			}
			if (idx % 2 == 0) {
				value = obj;
				idx++;
				criterion.add(Restrictions.eq(key, value));
				continue;
			}
		}
		return this.getLocalDao().findAllByCriteria(criterion);
	}
	
	public void executeSql(String sql){
		this.getLocalDao().excuteBySql(sql);
	}
	public Session getsession(){
		return this.getLocalDao().getsession();
	}

	

}
