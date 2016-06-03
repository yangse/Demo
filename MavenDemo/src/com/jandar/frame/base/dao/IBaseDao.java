package com.jandar.frame.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.type.NullableType;

import com.jandar.frame.support.CustomExample;
import com.jandar.frame.support.PaginationSupport;
import com.jandar.frame.support.PropertyFilter;

public interface IBaseDao<T> extends Serializable {

	public T findById(Serializable id);

	public void saveOrUpdate(T entity);
	
	public void saveOrUpdateOther(T entity);

	public void saveOrUpdateAll(Collection<T> entities);

	public void save(T entity);

	public void delete(T entity);

	public void deleteAll(Collection<T> entities);

	public void delete(Long id);

	public void deleteAll(List<Long> idToDelete);

	public void clear();

	public void flush();

	public void merge(T entity);

	public void persist(T entity);

	public void refresh(T entity);

	public void replicate(T entity, ReplicationMode replicationMode);

	public void evict(T entity);

	public void update(T entity);
	
	public List<Object[]> listQyjcMain(final String sql);
	
	public List<Object[]> listGrjcMain(final String sql);

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize);
	
	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize,final Projection p);
	
	public PaginationSupport<T> findPageByExampleFilter(
			final CustomExample<T> example, final List<PropertyFilter> filters, final int startIndex,
			final int pageSize);
	
	public PaginationSupport<T> findPageByList(
			final List<T> list,final int totalCount, final int startIndex,
			final int pageSize);

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final Order[] orders,
			final int startIndex, final int pageSize);

	public PaginationSupport<Object[]> findPageBySQL(final String sql,
			final String[] scalar, final int startIndex, final int pageSize);

	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int startIndex,
			final int pageSize);

	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final Order[] orders,
			final int startIndex, final int pageSize);

	public PaginationSupport<T> findAll(final int startIndex, final int pageSize);

	public int countByCriteria(final DetachedCriteria detachedCriteria);

	public int countByExample(final CustomExample<T> example);
 
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria);

	public List<T> findAllByCriteria(Criterion... criterion);

	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria,
			final int max);

	public List<T> findAllBySQL(final String hql, final int startIndex,
			final int numPerPage);

	public Class<T> getEntityClass();
	
	public Object uniqueResult(final DetachedCriteria detachedCriteria);
	
	public List<Object[]> findAllObjBySQL(final String sql);
	
	public List<Object[]> chartFindBySQL(final String sql);
	
	public List<Object[]> chartFind(final String sql);
	
	public List<Object[]> FindBySQL(final String sql);
	
	public List<Object[]> FindAllBySQL(final String sql);
	
	public List<Object[]> chartFindBySQL(final String sql, final NullableType xNullableType,final NullableType yNullableType);
	
	public List<Object[]> listFindBySQL(final String sql);
	
	public List<Object[]> queryFindBySQL(final String sql);
	
	public void excuteBySql(final String sql);
	
	public int findValue(final String sql);
	
	public int getValueX(final String sql);
	
	public String getValue(final String sql);
	
	public String getSJQXSQL(final String table,final String column,final String method,
			final String keyword);
	
	public void doSJQX(String str_sql);
	
	public Session getsession();

	public List<T> findAllByFilter(List<PropertyFilter> filters);
}
