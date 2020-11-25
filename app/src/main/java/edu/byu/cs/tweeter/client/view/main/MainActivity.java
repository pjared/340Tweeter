package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.view.LoginActivity;
import edu.byu.cs.tweeter.client.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.StatusTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.MainUserTask;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements MainPresenter.View, StatusTask.Observer,
                                                                LogoutTask.Observer, MainUserTask.Observer{

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String LOG_TAG = "MainActivity";
    User user = null;
    AuthToken authToken = null;
    MainPresenter mainPresenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);

        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        updateUser();

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        //How do we get a user's followers?
        TextView followeeCount = findViewById(R.id.followeeCount);

        followeeCount.setText(getString(R.string.followeeCount, user.getFolloweeCount()));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, user.getFollowCount()));

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.status_popup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                ImageButton closeStatus = popupView.findViewById(R.id.exitStatus);
                closeStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });


                Button postStatus = popupView.findViewById(R.id.statusPostButton);
                postStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Don't enable this button until there is text in the status
                        //setStatus(editTextStatus)
                        StatusRequest statRequest = new StatusRequest(user, "");
                        StatusTask statusTask = new StatusTask(mainPresenter, MainActivity.this);
                        statusTask.execute(statRequest);

                        Toast.makeText(MainActivity.this, "Posting...", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    public void updateUser() {
        User getUser = new User(user.getFirstName(), user.getLastName(), user.getAlias());
        UserRequest userRequest = new UserRequest(getUser);
        MainUserTask userTask = new MainUserTask(mainPresenter, MainActivity.this);
        userTask.execute(userRequest);
        //send just the user
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() ==  R.id.logoutMenu) {
            LogoutRequest logoutRequest = new LogoutRequest(user,authToken);
            LogoutTask logoutTask = new LogoutTask(mainPresenter, MainActivity.this);
            logoutTask.execute(logoutRequest);
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void logoutSuccessful(LogoutResponse loginResponse) {
        Toast.makeText(this, "Logged Out",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse loginResponse) {
        Toast.makeText(this, "Unable to log out",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void statusSuccessful(StatusResponse loginResponse) {
        Toast.makeText(MainActivity.this, "Posted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void statusUnsuccessful(StatusResponse loginResponse) {
        Toast.makeText(MainActivity.this, "Unable to post", Toast.LENGTH_SHORT).show();
    }

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

//Where do I store the users number of followers and followees?