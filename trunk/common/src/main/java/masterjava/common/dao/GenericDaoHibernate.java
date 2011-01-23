package masterjava.common.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Grigory Kislin
 *         Typesafe delegate request to HibernateTemplate
 *         <p/>
 *         Constructor: Entity class
 */
public class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {

    protected Class<T> type;

    public GenericDaoHibernate(Class<T> type) {
        this.type = type;
    }

    // return T or null

    @Override
    public T create() {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public T get(PK id) {
        return (T) getHibernateTemplate().get(type, id);
    }

    // return T or Exception

    @SuppressWarnings("unchecked")
    public T load(PK id) {
        return (T) getHibernateTemplate().load(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(type);
    }

    @SuppressWarnings("unchecked")
    public PK save(T newInstance) {
        return (PK) getHibernateTemplate().save(newInstance);
    }

    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<T> entities) {
        getHibernateTemplate().saveOrUpdateAll(entities);
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public void delete(PK key) {
        getHibernateTemplate().delete(get(key));
    }

    public void deleteAll(Collection<T> entities) {
        getHibernateTemplate().deleteAll(entities);
    }

    public void deleteAll() {
        getHibernateTemplate().deleteAll(loadAll());
    }

    public void refresh(T entity) {
        getHibernateTemplate().refresh(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName) {
        return (List<T>) getHibernateTemplate().findByNamedQuery(queryName);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Object value) {
        return (List<T>) getHibernateTemplate().findByNamedQuery(queryName, value);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Object[] values) {
        return (List<T>) getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value) {
        return (List<T>) getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramName, value);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
        return (List<T>) getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    @SuppressWarnings("unchecked")
    public <M> M narrow(T entity, Class<M> targetClass) {
        getHibernateTemplate().initialize(entity);
        if (!targetClass.isAssignableFrom(entity.getClass())) {
            throw new ClassCastException("Target class " + targetClass.getName() + "is not assignable from " + entity.getClass().getName());
        }
        return (M) entity;
    }
}
