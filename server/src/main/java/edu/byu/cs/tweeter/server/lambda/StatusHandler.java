package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.server.service.StatusServiceImpl;

public class StatusHandler implements RequestHandler<StatusRequest, StatusResponse> {
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        StatusServiceImpl statusService = new StatusServiceImpl();
        return statusService.postStatus(request);
    }
}
