package masterjava.web2.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * User: GKislin
 * Date: 10.12.2010
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/bean/applicationContext.xml"})
// @ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
// @TransactionConfiguration
// @Transactional
public class DaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    protected DataSource dataSource;

}
