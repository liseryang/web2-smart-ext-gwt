package masterjava.common.web2.gwt;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class SpringGwtRemoteServiceServlet extends RemoteServiceServlet {

    private static final Logger LOGGER = Logger.getLogger(SpringGwtRemoteServiceServlet.class);

    @Override
    public String processCall(String payload) throws SerializationException {
        try {
            Object handler = getBean(getThreadLocalRequest());
            RPCRequest rpcRequest = RPC.decodeRequest(payload, handler.getClass(), this);
            onAfterRequestDeserialized(rpcRequest);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoking " + handler.getClass().getName() + "." + rpcRequest.getMethod().getName());
            }
            return RPC.invokeAndEncodeResponse(handler, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest
                    .getSerializationPolicy());
        } catch (IncompatibleRemoteServiceException ex) {
            log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
            return RPC.encodeResponseForFailure(null, ex);
        }
    }

    /**
     * Determine Spring bean to handle request based on request URL, e.g. a
     * request ending in /myService will be handled by bean with name
     * "myService".
     *
     * @param request
     * @return handler bean
     */
    protected Object getBean(HttpServletRequest request) {
        String service = getService(request);
        Object bean = getBean(service);
        if (!(bean instanceof RemoteService)) {
            throw new IllegalArgumentException("Spring bean is not a GWT RemoteService: " + service + " (" + bean + ")");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Bean for service " + service + " is " + bean);
        }
        return bean;
    }

    /**
     * Parse the service name from the request URL.
     *
     * @param request
     * @return bean name
     */
    protected String getService(HttpServletRequest request) {
        String url = request.getRequestURI();
        String service = url.substring(url.lastIndexOf("/") + 1);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Service for URL " + url + " is " + service);
        }
        return service;
    }

    /**
     * Look up a spring bean with the specified name in the current web
     * application context.
     *
     * @param name bean name
     * @return the bean
     */
    protected Object getBean(String name) {
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        if (applicationContext == null) {
            throw new IllegalStateException("No Spring web application context found");
        }
        return applicationContext.getBean(name);
    }
}
