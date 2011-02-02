package masterjava.web2.dao;

import masterjava.common.web2.dao.GenericDao;
import masterjava.common.web2.test.DaoHsqlDbInFileTest;
import masterjava.web2.model.SimpleTableEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

/**
 * User: GKislin
 * Date: 10.12.2010
 */
public class SimpleTableDaoTest extends DaoHsqlDbInFileTest {

    @Resource
    GenericDao<SimpleTableEntity, Long> simpleTableDao;

    @Test
    public void testSave() {
        SimpleTableEntity entity = new SimpleTableEntity("Sample text", 1, new Date(System.currentTimeMillis()));
        long pk = simpleTableDao.save(entity);
        assertEquals(entity, simpleTableDao.load(pk));
    }
}
