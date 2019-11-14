package ts.daoBase;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author acdart
 * @date 2018年6月4日
 * @param <T>
 * @param <PK>
 * 提供Hibernate dao的所有操作 类中所有方法都进行事务处理
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW) // 不管是否存在事务，都创建一个新的事务，可读写
// HibernateDaoSupport提供getHibernateTemplate()方法，返回HibernateTemplate的实例
public class BaseDao<T, PK extends Serializable> extends HibernateDaoSupport implements IBaseDao<T, PK> {
	protected Class<T> entityClass; // DAO所管理的Entity类型

	public BaseDao() {}

	/**
	 * 让Spring提供构造函数注入
	 * 
	 * @param type
	 */
	public BaseDao(Class<T> type) {
		this.entityClass = type;
	}

	/**
	 * 清空Hibernate缓存
	 */
	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 从缓存中删除指定对象
	 */
	@Override
	public void evit(T entity) {
		getHibernateTemplate().evict(entity);
	}

	/**
     * 按照指定条件查找，并返回排序后的结果
     * @param orderBy 排序的属性
     * @param isAsc true代表升序 false代表降序
     * @param criterions 限制条件
     * @return 满足条件的全部的对象
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findBy(String orderBy, boolean isAsc, Criterion... criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		if (isAsc) criteria.addOrder(Order.asc(orderBy));
		else criteria.addOrder(Order.desc(orderBy));
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}

	/**
     * 指定属性值相等的对象
     * @param propertyName 属性名
     * @param value 属性值
     * @param orderBy 排序的属性
     * @param isAsc true代表升序 false代表降序
     * @return 满足条件的全部对象
     */
	@Override
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return findBy(orderBy, isAsc, Restrictions.eq(propertyName, value));
	}

	/**
     * 模糊查找
     * @param propertyName 属性名
     * @param value  属性值
     * @param orderBy 排序的属性名
     * @param isAsc true代表升序 false代表降序
     * @return 满足条件的全部对象
     */
	@Override
	public List<T> findLike(String propertyName, Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return findBy(orderBy, isAsc, Restrictions.like(propertyName, value));
	}

	/**
	 * 刷新缓存,执行所有待执行的数据库操作
	 */
	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 根据PK获取指定对象
	 */
	@Override
	public T get(PK id) { // load结果集为空时抛异常，这里进行捕获并仅返回null处理
		try {
			T tmp =  getHibernateTemplate().get(getEntityClass(), id);
		//	System.out.println(tmp);
			return tmp;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取数据库中全部对象
	 */
	@Override
	public List<T> getAll() {
		try {
			return (List<T>) (getHibernateTemplate().loadAll(getEntityClass()));
		} catch (Exception e) {
			return null;
		}
	}

	/**
     * 获取安装某一属性排序后的全部对象
     * @param orderBy 属性名
     * @param isAsc true代表升序 false代表降序
     * @return 排序后的对象
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc)
			return (List<T>) getHibernateTemplate()
					.findByCriteria(DetachedCriteria.forClass(getEntityClass()).addOrder(Order.asc(orderBy)));
		else
			return (List<T>) getHibernateTemplate()
					.findByCriteria(DetachedCriteria.forClass(getEntityClass()).addOrder(Order.desc(orderBy)));
	}

	/**
	 * 获取Entity
	 * 
	 * @return
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 删除对象
	 */
	@Override
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 按主键删除
	 */
	@Override
	public void removeById(PK id) {
		remove(get(id));
	}

	/**
	 * 保存持久化对象
	 */
	@Override
	public void save(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	public void save2(T entity) {
		getHibernateTemplate().save(entity);
	}
	/**
	 * 更新
	 */
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}
}