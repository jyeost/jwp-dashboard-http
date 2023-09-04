package org.apache.front;

import nextstep.jwp.controller.Controller;
import nextstep.jwp.controller.HelloWorldController;
import nextstep.jwp.controller.LoginController;
import nextstep.jwp.controller.RegisterController;
import org.apache.coyote.request.Request;
import org.apache.coyote.response.PathResponse;
import org.apache.coyote.response.Response;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicController implements FrontController {

    private static final Map<String, Controller> urlMapper = new ConcurrentHashMap<>();


    static {
        urlMapper.put("/", new HelloWorldController());
        urlMapper.put("/login", new LoginController());
        urlMapper.put("/register", new RegisterController());
    }

    @Override
    public Response process(Request request) {
        if (!urlMapper.containsKey(request.getPath())) {
            return new PathResponse(request.getPath(), HttpURLConnection.HTTP_OK, "OK");
        }
        Controller controller = urlMapper.get(request.getPath());
        return controller.handle(request);
    }
}