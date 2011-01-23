package masterjava.common.test;

import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import masterjava.common.util.IOUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * User: GKislin
 * Date: 24.12.2010
 */
public class DaoHsqlDbInFileTest extends DaoTest {

    private static Properties properties;

    @BeforeClass
    public static void beforeClass() {
        File db;
        try {
            properties = IOUtil.getResourceAsProperties("jdbc.properties");
            Class.forName(properties.getProperty("jdbc.driverClassName"));

            String database = properties.getProperty("jdbc.database");
            db = new File(database);
            Server.main(new String[]{
                    "-database.0", db.getAbsolutePath(),
                    "-dbname.0", db.getName(),
                    "-no_system_exit", "true"
            });
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("HSQLDB start failed", e);
        }
    }

    @AfterClass
    public static void afterClass() {
        executeStatement("SHUTDOWN;");
    }

    private static void executeStatement(String statement) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"),
                    properties.getProperty("jdbc.password"));

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(statement);
            stmt.close();
        } catch (Exception e) {
            throw new IllegalStateException("HSQLDB execute '" + statement + "' problem", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.print("Cannot close database connection " + e.getMessage());
                }
            }
        }
    }
}
