package com.jandar.frame.base.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

import com.jandar.frame.support.CustomExample;
import com.jandar.frame.support.PaginationSupport;
import com.jandar.frame.support.PropertyFilter;

public interface IBaseService<T> extends Serializable {

	public void merge(T entity);
	public void saveOrUpdate(T entity);

	public void saveOrUpdateAll(Collection<T> entities);
	
	public void saveOrUpdateOther(T entity);

	public void delete(T entity);

	public void deleteAll(Collection<T> entities);

	public void delete(Long id);

	public void deleteAll(List<Long> idToDelete);
	
    public List<T> findAll();
    
    public List<T> findByKeyValuePair(Object... keyValuePair);
 
	public void flush();

	public void clear();
 	
	public T findById(Serializable id);

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize);
	
	public PaginationSupport<T> findPageByExampleFilter(
			final CustomExample<T> example, final List<PropertyFilter> filters, final int startIndex,
			final int pageSize);
	
	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize,final Projection p);
	
	public PaginationSupport<T> findPageByList(
			final List<T> list,final int totalCount, final int startIndex,
			final int pageSize);

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final Order[] orders,
			final int startIndex, final int pageSize);
	
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int startIndex,
			final int pageSize);
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final Order[] orders,
			final int startIndex, final int pageSize);
	
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria,
			final int max);
	
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria);
	
	public List<T> findAllByFilter(List<PropertyFilter> filters);

	public int countByExample(final CustomExample<T> example);
	
	public List<T> findAllValid();
	
	/**
	 * @$comment 无效状态，假删除
	 * @param idToDelete
	 */
	public void invalid(T entity);
	/**
	 * @$comment 无效状态，假删除
	 * @param idToDelete
	 */
	public void invalidAll(List<Long> idToDelete);
	
	public Session getsession();
}
