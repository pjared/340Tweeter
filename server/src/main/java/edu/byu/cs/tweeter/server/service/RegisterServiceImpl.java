package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.RegisterServiceInterface;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceImpl implements RegisterServiceInterface {

    @Override
    public RegisterResponse register(RegisterRequest register) {
        User user = new User(register.getFirstName(), register.getLastName(),
                register.getUsername(), register.getImage());
        return new RegisterResponse(user, new AuthToken());
    }

    //FeedDAO getFeedDAO() {return new FeedDAO();}
}
