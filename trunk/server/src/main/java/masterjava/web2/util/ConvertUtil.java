package masterjava.web2.util;

import net.sf.beanlib.hibernate.HibernateBeanReplicator;
import net.sf.beanlib.hibernate3.Hibernate3BeanReplicator;
import net.sf.beanlib.spi.DetailedPropertyFilter;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import java.lang.reflect.Method;

/**
 * User: GKislin
 * Date: 29.12.10
 */
public class ConvertUtil {
    private static final Logger LOGGER = Logger.getLogger(ConvertUtil.class);

    /**
     * Get copy of unproxy object without lazy loading data
     *
     * @param entity -entity for unproxy
     * @return unporxy object without initialization. Session may be closed.
     */
    public static <T> T getLazyUnproxyCopy(T entity) {

        HibernateBeanReplicator repl = new Hibernate3BeanReplicator().initDetailedPropertyFilter(
                new DetailedPropertyFilter() {

                    public boolean propagate(String propertyName, Object fromBean, Method readerMethod, Object toBean, Method setterMethod) {
                        Object fromValue;
                        try {
                            fromValue = readerMethod.invoke(fromBean);
                        } catch (Exception e) {
                            LOGGER.error("Fail to get " + propertyName + " from " + fromBean.getClass().getSimpleName());
                            return false;
                        }
                        return Hibernate.isInitialized(fromValue);
                    }
                });

        return repl.copy(entity);
    }

    /**
     * Get copy of unproxy object with lazy loading data
     *
     * @param entity -entity for unproxy
     * @return unporxy object with eager initialization, if nesessary. Session must not be closed.
     */
    public static <T> T getUnproxyCopy(T entity) {
        HibernateBeanReplicator repl = new Hibernate3BeanReplicator();
        return repl.copy(entity);
    }

    public static String toJson(Object entity) {
        return JSONObject.fromObject(entity).toString();
    }
}
