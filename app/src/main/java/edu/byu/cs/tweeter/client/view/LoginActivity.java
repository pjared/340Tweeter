/*
package edu.byu.cs.tweeter.client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;

*/
/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 *//*

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, LoginTask.Observer {

    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;
    private Toast loginInToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInToast = Toast.makeText(LoginActivity.this, "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                LoginRequest loginRequest = new LoginRequest("dummyUserName", "dummyPassword");
                LoginTask loginTask = new LoginTask(presenter, LoginActivity.this);
                loginTask.execute(loginRequest);
            }
        });
    }

    */
/**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     *//*

    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    */
/**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     *//*

    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(this, "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    */
/**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     *//*

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);

        if(exception instanceof TweeterRemoteException) {
            TweeterRemoteException remoteException = (TweeterRemoteException) exception;
            Log.e(LOG_TAG, "Remote Exception Type: " + remoteException.getRemoteExceptionType());

            Log.e(LOG_TAG, "Remote Stack Trace:");
            if(remoteException.getRemoteStackTrace() != null) {
                for(String stackTraceLine : remoteException.getRemoteStackTrace()) {
                    Log.e(LOG_TAG, "\t\t" + stackTraceLine);
                }
            }
        }

        Toast.makeText(this, "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
*/

package edu.byu.cs.tweeter.client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.InputStream;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 */
public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, LoginTask.Observer
        ,RegisterTask.Observer {
    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;
    private Toast loginInToast;
    private EditText editTextPassword;
    private EditText editTextUserName;
    private EditText editTextFirstName;
    private EditText editTextLastName;

    public static final int PICK_IMAGE = 1;
    Uri imageUri;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkRegisterValues();
            checkLoginValues();
        }
    };

    private void checkRegisterValues(){
        Button b = (Button) view.findViewById(R.id.registerButton);

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String userNameText = editTextUserName.getText().toString();
        String passWordText = editTextPassword.getText().toString();
        //String profilePic = emailEditText.getText().toString();
        //|| imageUri == null
        if(userNameText.equals("") || passWordText.equals("") ||
                firstName.equals("") || lastName.equals("")) {
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    private void checkLoginValues(){
        Button b = (Button) view.findViewById(R.id.loginButton);

        String userNameText = editTextUserName.getText().toString();
        String passWordText = editTextPassword.getText().toString();

        if(userNameText.equals("") || passWordText.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //view = inflater.inflate(R.layout.fragment_login, container, false);
        view = findViewById(android.R.id.content).getRootView();

        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextUserName = view.findViewById(R.id.editTextUserName);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        editTextFirstName.addTextChangedListener(mTextWatcher);
        editTextLastName.addTextChangedListener(mTextWatcher);
        editTextUserName.addTextChangedListener(mTextWatcher);
        editTextPassword.addTextChangedListener(mTextWatcher);

        presenter = new LoginPresenter(this);

        checkRegisterValues();
        checkLoginValues();

        Button imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void pickImage() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        //open a camera activity
        //open a file select dialog

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {

                loginInToast = Toast.makeText(LoginActivity.this, "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                LoginRequest loginRequest = new LoginRequest(editTextUserName.getText().toString()
                        ,editTextPassword.getText().toString());
                LoginTask loginTask = new LoginTask(presenter, LoginActivity.this);
                loginTask.execute(loginRequest);
            }
        });
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                loginInToast = Toast.makeText(LoginActivity.this, "Registering...", Toast.LENGTH_LONG);
                loginInToast.show();
                String imgString = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                //TODO: Find out what i'm supposed to do with user image
                RegisterRequest registerRequest = new RegisterRequest(editTextFirstName.getText().toString(),
                        editTextLastName.getText().toString(), editTextUserName.getText().toString()
                        ,editTextPassword.getText().toString(), imgString);//imageUri
                RegisterTask regTask = new RegisterTask(presenter, LoginActivity.this);

                //we probably have to convert register into a login?
                regTask.execute(registerRequest);
            }
        });
    }

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, registerResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, registerResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(this, "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        Toast.makeText(this, "Failed to Register. " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            Toast.makeText(this, "You chose a profile picture", Toast.LENGTH_LONG).show();
            //imageView.setImageURI(imageUri);
        }
    }
}

