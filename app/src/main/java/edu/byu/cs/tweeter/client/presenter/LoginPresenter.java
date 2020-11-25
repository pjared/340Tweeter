package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.LoginServiceInterfaceProxy;
import edu.byu.cs.tweeter.client.model.service.RegisterServiceInterfaceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowServiceInterface;
import edu.byu.cs.tweeter.model.service.RegisterServiceInterface;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.LoginServiceInterface;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to edu.byu.cs.shared.edu.byu.cs.tweeter.client.model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LoginPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param loginRequest the request.
     */
    public LoginResponse login(LoginRequest loginRequest) throws IOException, TweeterRemoteException {
        LoginServiceInterface loginServiceInterface = getLoginService();
        return loginServiceInterface.login(loginRequest);
    }

    public RegisterResponse register(RegisterRequest registerRegister) throws IOException, TweeterRemoteException {
        RegisterServiceInterface registerService = getRegisterService();
        return registerService.register(registerRegister);
    }

    LoginServiceInterface getLoginService() {
        return new LoginServiceInterfaceProxy();
    }


    RegisterServiceInterface getRegisterService(){ return new RegisterServiceInterfaceProxy();}
}
