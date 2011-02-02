package masterjava.web2.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface GreetingServiceAsync {
    void greet(String name, AsyncCallback<String> async);
}
