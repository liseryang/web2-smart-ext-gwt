package masterjava.web2.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
/**
 * Client side interface for the greeting service.
 */
@RemoteServiceRelativePath("rpc/greetingService")
public interface GreetingService extends RemoteService {

	String greet(String name);
}
