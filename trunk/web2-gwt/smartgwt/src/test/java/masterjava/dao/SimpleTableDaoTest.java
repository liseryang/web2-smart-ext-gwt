package masterjava.dao;

import masterjava.common.dao.GenericDao;
import masterjava.common.test.DaoHsqlDbInFileTest;
import org.junit.Test;
import masterjava.model.SimpleTableEntity;

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
