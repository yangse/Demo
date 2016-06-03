package com.jandar.frame.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.NullableType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.jandar.frame.support.CustomExample;
import com.jandar.frame.support.PaginationSupport;
import com.jandar.frame.support.PropertyFilter;
import com.jandar.frame.support.PropertyFilter.MatchType;
import com.jandar.frame.utils.ReflectionUtils;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	private static final long serialVersionUID = 8396924531977451965L;
	protected final Log log = LogFactory.getLog(BaseDaoImpl.class);

	private Class<T> entityClass;

	public BaseDaoImpl() {
		try {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception ex) {
			// 忽略
		}
	}

	public BaseDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void deleteAll(Collection<T> entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}

	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<T> entities) {
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void saveOrUpdateOther(T entity) {
		this.getHibernateTemplate().merge(entity);
	}

	public void clear() {
		this.getHibernateTemplate().clear();
	}

	public void flush() {
		this.getHibernateTemplate().flush();
	}

	public T findById(Serializable id) {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize) {
		return findPageByExample(example, null, startIndex, pageSize);
	}
	
	public PaginationSupport<T> findPageByExampleFilter(
			final CustomExample<T> example, final List<PropertyFilter> filters, final int startIndex,
			final int pageSize) {
		return findPageByExampleFilter(example, filters, null, startIndex, pageSize);
	}

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final int startIndex,
			final int pageSize, final Projection p) {
		return findPageByExample(example, null, startIndex, pageSize, p);
	}

	public PaginationSupport<T> findPageByList(final List<T> list,
			final int totalCount, final int startIndex, final int pageSize) {

		PaginationSupport ps = new PaginationSupport(list, totalCount,
				startIndex, pageSize);
		return ps;
	}

	public List<Object[]> listQyjcMain(final String sql) {
		if (sql == null) {
			return null;
		}
		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("qymc", Hibernate.STRING)
								.addScalar("qyzch", Hibernate.STRING)
								.addScalar("zzjgdm", Hibernate.STRING);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> listGrjcMain(final String sql) {
		if (sql == null) {
			return null;
		}
		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("xm", Hibernate.STRING)
								.addScalar("sfzh", Hibernate.STRING);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	@SuppressWarnings("rawtypes")
	public PaginationSupport<Object[]> findPageBySQL(final String sql,
			final String[] scalar, final int startIndex, final int pageSize) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		for (String s : scalar)
			query.addScalar(s);
		List<Object> list = query.list();
		query = this.getSession().createSQLQuery(
				"select count(*) as count "
						+ sql.substring(sql.indexOf("from")));
		query.addScalar("count");
		BigDecimal result = (BigDecimal) query.uniqueResult();
		PaginationSupport resultList = new PaginationSupport(list,
				(result != null) ? result.intValue() : 0, startIndex, pageSize);
		return resultList;
	}

	public void delete(Long id) {
		this.getHibernateTemplate().delete(findById(id));
	}

	public void deleteAll(List<Long> idToDelete) {
		for (Long id : idToDelete) {
			delete(id);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<T> findAllByExample(final CustomExample<T> example,
			final Order[] orders, final int firstResult, final int maxResults) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria executableCriteria = session
								.createCriteria(example.getEntityClass());
						executableCriteria.add(example);
						example.appendCondition(executableCriteria);
						executableCriteria.setProjection(null);
						executableCriteria
								.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
						for (int i = 0; orders != null && i < orders.length; i++) {
							executableCriteria.addOrder(orders[i]);
						}
						List items = executableCriteria
								.setFirstResult(firstResult)
								.setMaxResults(maxResults).list();
						return items;
					}
				});
	}

	@SuppressWarnings("rawtypes")
	public PaginationSupport<T> findPageByExampleFilter(
			final CustomExample<T> example, final List<PropertyFilter> filters, final Order[] orders,
			final int startIndex, final int pageSize) {
		HibernateCallback hcb = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria executableCriteria = session.createCriteria(example.getEntityClass());
				executableCriteria.add(example);
				example.appendCondition(executableCriteria);
				
				Criterion[] criterions = buildPropertyFilterCriterions(filters);
				DetachedCriteria detachedCrit = DetachedCriteria.forClass(example.getEntityClass());
				for (Criterion c : criterions) {
					detachedCrit.add(c);
				}
				example.appendCondition(detachedCrit);
				
				int totalCount = ((Integer) executableCriteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue();
				executableCriteria.setProjection(null);
				executableCriteria
						.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				for (int i = 0; orders != null && i < orders.length; i++) {
					executableCriteria.addOrder(orders[i]);
				}
				List items = executableCriteria.setFirstResult(startIndex)
						.setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items, totalCount,
						startIndex, pageSize);
				return ps;
			}
		};
		HibernateTemplate ht = this.getHibernateTemplate();
		return (PaginationSupport) ht.execute(hcb);
	}
	
	@SuppressWarnings("rawtypes")
	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final Order[] orders,
			final int startIndex, final int pageSize) {
		HibernateCallback hcb = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria executableCriteria = session.createCriteria(example
						.getEntityClass());
				executableCriteria.add(example);
				example.appendCondition(executableCriteria);
				int totalCount = ((Integer) executableCriteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue();
				executableCriteria.setProjection(null);
				executableCriteria
				.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				for (int i = 0; orders != null && i < orders.length; i++) {
					executableCriteria.addOrder(orders[i]);
				}
				List items = executableCriteria.setFirstResult(startIndex)
						.setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items, totalCount,
						startIndex, pageSize);
				return ps;
			}
		};
		HibernateTemplate ht = this.getHibernateTemplate();
		return (PaginationSupport) ht.execute(hcb);
	}

	public PaginationSupport<T> findPageByExample(
			final CustomExample<T> example, final Order[] orders,
			final int startIndex, final int pageSize, final Projection p) {
		HibernateCallback hcb = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria executableCriteria = session.createCriteria(example
						.getEntityClass());
				executableCriteria.add(example);
				example.appendCondition(executableCriteria);
				int totalCount = ((Integer) executableCriteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue();
				executableCriteria.setProjection(p);
				executableCriteria
						.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				for (int i = 0; orders != null && i < orders.length; i++) {
					executableCriteria.addOrder(orders[i]);
				}
				List items = executableCriteria.setFirstResult(startIndex)
						.setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items, totalCount,
						startIndex, pageSize);
				return ps;
			}
		};
		HibernateTemplate ht = this.getHibernateTemplate();
		return (PaginationSupport) ht.execute(hcb);
	}

	@SuppressWarnings("rawtypes")
	public List<T> findAllBySQL(final String hql, final int startIndex,
			final int numPerPage) {
		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setFirstResult(startIndex);
						query.setMaxResults(numPerPage);
						List list = query.list();
						return list;
					}
				});
		return list;
	}

	public int countByCriteria(final DetachedCriteria detachedCriteria) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						int totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult())
								.intValue();
						return totalCount;
					}
				});
	}

	public Object uniqueResult(final DetachedCriteria detachedCriteria) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						return detachedCriteria.getExecutableCriteria(session)
								.uniqueResult();
					}
				});
	}

	public int countByExample(final CustomExample<T> example) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria executableCriteria = session
								.createCriteria(example.getEntityClass());
						executableCriteria.add(example);
						example.appendCondition(executableCriteria);
						int totalCount = ((Integer) executableCriteria
								.setProjection(Projections.rowCount())
								.uniqueResult()).intValue();
						return totalCount;
					}
				});
	}

	public void evict(T entity) {
		this.getHibernateTemplate().evict(entity);
	}

	public PaginationSupport<T> findAll(int startIndex, int pageSize) {
		return findPageByCriteria(DetachedCriteria.forClass(entityClass),
				startIndex, pageSize);
	}

	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria.list();
					}
				});
	}

	public List<T> findAllByCriteria(Criterion... criterion) {
		DetachedCriteria detachedCrit = DetachedCriteria
				.forClass(getEntityClass());
		for (Criterion c : criterion) {
			detachedCrit.add(c);
		}
		return (List<T>) getHibernateTemplate().findByCriteria(detachedCrit);
	}

	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria,
			final int max) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session).setMaxResults(
										max);
						return criteria.list();
					}
				});
	}

	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int startIndex,
			final int pageSize) {
		return findPageByCriteria(detachedCriteria, null, startIndex, pageSize);
	}

	@SuppressWarnings("rawtypes")
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final Order[] orders,
			final int startIndex, final int pageSize) {
		return (PaginationSupport<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						Integer integer = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						int totalCount = 10;
						if (integer != null)
							totalCount = integer.intValue();
						criteria.setProjection(null);
						criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
						for (int i = 0; orders != null && i < orders.length; i++) {
							criteria.addOrder(orders[i]);
						}
						List items = criteria.setFirstResult(startIndex)
								.setMaxResults(pageSize).list();
						PaginationSupport ps = new PaginationSupport(items,
								totalCount, startIndex, pageSize);
						return ps;
					}
				});
	}

	public List<Object[]> findAllObjBySQL(final String sql) {
		if (sql == null) {
			return null;
		}

		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public String getSJQXSQL(final String table, final String column,
			final String method, final String keyword) {
		Object str = this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Connection conn = session.connection();
						CallableStatement cs = conn
								.prepareCall("{?=call GETSJQXSQL(?,?,?,?)}");
						cs.registerOutParameter(1, Types.VARCHAR);
						cs.setString(2, table);
						cs.setString(3, column);
						cs.setString(4, method);
						cs.setString(5, keyword);
						cs.execute();
						return cs.getString(1);
					}
				});
		return str.toString();
	}

	public void doSJQX(final String str_sql) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session
						.createSQLQuery("{call PRO_QX_SJQX(?)}");
				query.setString(0, str_sql);
				query.executeUpdate();
				return null;
			}
		});

	}

	public int findValue(final String sql) {
		if (sql == null) {
			return 0;
		}

		Object obj = this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						return query.uniqueResult();
					}
				});
		// BigInteger result = (BigInteger ) obj;
		int result = 0;
		if (obj != null)
			result = Integer.parseInt(obj.toString());
		return result;

	}

	public int getValueX(final String sql) {
		if (sql == null) {
			return 0;
		}

		Object obj = this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql).addScalar(
								"x", Hibernate.INTEGER);
						return query.uniqueResult();
					}
				});
		int result = 0;
		if (obj != null)
			result = Integer.parseInt(obj.toString());
		return result;

	}

	public String getValue(final String sql) {
		if (sql == null) {
			return null;
		}

		Object obj = this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						return query.uniqueResult();
					}
				});
		if (obj == null)
			return "-";
		else
			return obj.toString();

	}

	public List<Object[]> chartFindBySQL(final String sql) {
		if (sql == null) {
			return null;
		}

		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("x", Hibernate.STRING)
								.addScalar("y", Hibernate.INTEGER);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> chartFind(final String sql) {
		if (sql == null) {
			return null;
		}

		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("x", Hibernate.STRING)
								.addScalar("y", Hibernate.DOUBLE);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> FindBySQL(final String sql) {
		if (sql == null) {
			return null;
		}

		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("x", Hibernate.STRING)
								.addScalar("y", Hibernate.INTEGER)
								.addScalar("success", Hibernate.INTEGER)
								.addScalar("fail", Hibernate.INTEGER);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> FindAllBySQL(final String sql) {
		if (sql == null) {
			return null;
		}

		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> chartFindBySQL(final String sql,
			final NullableType xNullableType, final NullableType yNullableType) {
		if (sql == null) {
			return null;
		}

		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("x", xNullableType)
								.addScalar("y", yNullableType);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> queryFindBySQL(final String sql) {
		if (sql == null) {
			return null;
		}
		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("primeryKey", Hibernate.STRING)
								.addScalar("summy", Hibernate.INTEGER);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public List<Object[]> listFindBySQL(final String sql) {
		if (sql == null) {
			return null;
		}
		List<Object[]> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.addScalar("ssxq", Hibernate.STRING)
								.addScalar("totalcount", Hibernate.INTEGER);
						List<Object[]> list = query.list();
						return list;
					}
				});
		return list;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void merge(T entity) {
		this.getHibernateTemplate().merge(entity);
	}

	public void persist(T entity) {
		this.getHibernateTemplate().persist(entity);
	}

	public void refresh(T entity) {
		this.getHibernateTemplate().refresh(entity);
	}

	public void replicate(T entity, ReplicationMode replicationMode) {
		this.getHibernateTemplate().replicate(entity, replicationMode);
	}

	public void save(T entity) {
		this.getHibernateTemplate().persist(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public void excuteBySql(final String sql) {
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.executeUpdate();
			}

		});
	}

	public Session getsession() {
		return this.getSession();
	}

	@Override
	public List<T> findAllByFilter(List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		DetachedCriteria detachedCrit = DetachedCriteria
				.forClass(getEntityClass());
		for (Criterion c : criterions) {
			detachedCrit.add(c);
		}
		return (List<T>) getHibernateTemplate().findByCriteria(detachedCrit);
	}
	
	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	public Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.isMultiProperty()) { //只有一个属性需要比较的情况.
				Criterion criterion = buildPropertyFilterCriterion(filter.getPropertyName(), filter.getPropertyValue(),
						filter.getMatchType());
				criterionList.add(criterion);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildPropertyFilterCriterion(param, filter.getPropertyValue(), filter
							.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
	
	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildPropertyFilterCriterion(final String propertyName, final Object propertyValue,
			final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		try {

			//根据MatchType构造criterion
			if (MatchType.EQ.equals(matchType)) {
				criterion = Restrictions.eq(propertyName, propertyValue);
			} else if (MatchType.LIKE.equals(matchType)) {
				criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			} else if (MatchType.LE.equals(matchType)) {
				criterion = Restrictions.le(propertyName, propertyValue);
			} else if (MatchType.LT.equals(matchType)) {
				criterion = Restrictions.lt(propertyName, propertyValue);
			} else if (MatchType.GE.equals(matchType)) {
				criterion = Restrictions.ge(propertyName, propertyValue);
			} else if (MatchType.GT.equals(matchType)) {
				criterion = Restrictions.gt(propertyName, propertyValue);
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
		return criterion;
	}

}
