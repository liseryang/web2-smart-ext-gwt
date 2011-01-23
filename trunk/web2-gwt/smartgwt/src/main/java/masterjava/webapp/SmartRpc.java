package masterjava.webapp;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: GKislin
 * Date: 27.12.10
 */
public class SmartRpc extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SmartRpc.class);

    protected void service(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("BINGO");
    }
}
