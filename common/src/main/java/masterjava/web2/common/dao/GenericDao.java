package masterjava.web2.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Grigory Kislin
 */
public interface GenericDao<T, PK extends Serializable> {

    T create();

    T get(PK id);

    T load(PK id);

    List<T> loadAll();

    <M> M narrow(T entity, Class<M> targetClass);

    void refresh(T entity);

    PK save(T entity);

    void update(T entity);

    void saveOrUpdate(T entity);

    void saveOrUpdateAll(Collection<T> entities);

    void delete(T entity);

    void delete(PK id);

    void deleteAll(Collection<T> entities);

    void deleteAll();

    List<T> findByNamedQuery(final String queryName);

    List<T> findByNamedQuery(final String queryName, Object value);

    List<T> findByNamedQuery(final String queryName, final Object[] values);

    List<T> findByNamedQueryAndNamedParam(String queryName, String paramName, Object value);

    List<T> findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);
}
