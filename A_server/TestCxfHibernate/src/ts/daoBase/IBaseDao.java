package ts.daoBase;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

/**
 * 提供Hibernate Dao的所有操作 实现类由Spring注入HibernateEntityDao和HibernateGenericDao来实现
 * 
 * @author acdart
 * @date 2018年5月8日
 * @param <T>
 * @param <PK>
 */
public interface IBaseDao<T, PK extends Serializable> {
	/**
	 * 根据ID获取对象 实际是调用Hibernate的session.load()方法返回实体或其proxy对象，如果对象不存在，则抛出异常
	 * 
	 * @param id
	 * @return T ID对应的对象
	 */
	public T get(PK id);

	/**
	 * 获取全部对象
	 * 
	 * @see HibernateGenericDao#getAll(Class)
	 * @return List<T> 全部对象列表
	 */
	public List<T> getAll();

	/**
	 * 获取全部对象，并根据参数进行排序
	 * 
	 * @param orderBy
	 * @param isAsc
	 * @return List<T> 按指定参数排序后的全部对象
	 */
	public List<T> getAll(String orderBy, boolean isAsc);

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param orderBy
	 * @param isAsc
	 * @param criterions
	 * @return List<T> 符合条件的对象列表
	 */
	public List<T> findBy(String orderBy, boolean isAsc, Criterion... criterions);

	/**
	 * 根据属性名和属性值查询对象，并按指定参数进行排序
	 * 
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc);

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<T> findLike(String propertyName, Object value, String orderBy, boolean isAsc);

	/**
	 * 保存对象
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 在不同的session中关联修改过的托管对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void remove(T entity);

	/**
	 * 根据ID移除对象
	 * 
	 * @param id
	 */
	public void removeById(PK id);

	/**
	 * 消除与 Hibernate Session 的关联
	 * 
	 * @param entity
	 */
	public void evit(T entity);

	/**
	 * 
	 */
	public void flush();

	/**
	 * 
	 */
	public void clear();
}
