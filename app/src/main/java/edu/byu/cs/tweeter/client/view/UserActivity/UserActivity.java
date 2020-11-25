package edu.byu.cs.tweeter.client.view.UserActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.UserPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.IsFollowingTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.MainUserTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.UnFollowTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.UserTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class UserActivity extends AppCompatActivity implements UserPresenter.View, UnFollowTask.Observer,
                                                                    FollowTask.Observer, IsFollowingTask.Observer, UserTask.Observer {

    private static final String LOG_TAG = "UserActivity";

    public static final String BASE_USER_KEY = "BaseUser";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private boolean isFollowing = false;
    User curViewUser = null;
    Button followButton;
    UserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserPresenter(UserActivity.this);
        setContentView(R.layout.activity_user);

        curViewUser = (User) getIntent().getSerializableExtra(BASE_USER_KEY);
        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        updateUser();
        setFollowingButton(curViewUser, user);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        setUpButton(user);

        UserSectionsPagerAdapter sectionsPagerAdapter = new UserSectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
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

        TextView followeeCount = findViewById(R.id.followeeCount);

        followeeCount.setText(getString(R.string.followeeCount, user.getFolloweeCount()));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, user.getFollowCount()));

        followButton = findViewById(R.id.followButton);
        followButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO:Do follow logic here
                if(isFollowing) {
                    Toast.makeText(UserActivity.this, "Unfollowing...", Toast.LENGTH_SHORT).show();
                    FollowRequest request = new FollowRequest(curViewUser, user);
                    UnFollowTask unFollowTask = new UnFollowTask(presenter, UserActivity.this);
                    unFollowTask.execute(request);
                    isFollowing = false;
                } else {
                    Toast.makeText(UserActivity.this, "Following...", Toast.LENGTH_SHORT).show();
                    FollowRequest request = new FollowRequest(curViewUser, user);
                    FollowTask followTask = new FollowTask(presenter, UserActivity.this);
                    followTask.execute(request);
                    isFollowing = true;
                }
                setFollowingButton(curViewUser, user);
            }
        });
    }

    public void setUpButton(User user) {
        FollowRequest request = new FollowRequest(curViewUser, user);
        IsFollowingTask followTask = new IsFollowingTask(presenter, UserActivity.this);
        followTask.execute(request);
        if (followButton != findViewById(R.id.followButton)) {
            followButton = findViewById(R.id.followButton);
        }
        if(isFollowing) {
            followButton.setText("UNFOLLOW");
        } else {
            followButton.setText("FOLLOW");
        }
    }

    public void setFollowingButton(User followee, User following) {
        FollowRequest request = new FollowRequest(followee, following);
        IsFollowingTask followTask = new IsFollowingTask(presenter, UserActivity.this);
        followTask.execute(request);
        if (followButton != findViewById(R.id.followButton)) {
            followButton = findViewById(R.id.followButton);
        }
        if(isFollowing) {
            followButton.setText("UNFOLLOW");
        } else {
            followButton.setText("FOLLOW");
        }

    }

    public void updateUser() {
        User getUser = new User(curViewUser.getFirstName(), curViewUser.getLastName(), curViewUser.getAlias());
        UserRequest userRequest = new UserRequest(getUser);
        UserTask userTask = new UserTask(presenter, UserActivity.this);
        userTask.execute(userRequest);
    }


    @Override
    public void unfollowSuccessful(FollowResponse feedResponse) {
        Toast.makeText(this, "Unfollowed user", Toast.LENGTH_LONG).show();
    }

    @Override
    public void unfollowUnSuccessful(FollowResponse feedResponse) {
        Toast.makeText(this, "Was not able to unfollow user", Toast.LENGTH_LONG).show();
    }

    @Override
    public void followSuccessful(FollowResponse feedResponse) {
        Toast.makeText(this, "Followed User", Toast.LENGTH_LONG).show();
    }

    @Override
    public void followUnSuccessful(FollowResponse feedResponse) {
        Toast.makeText(this, "Was not able to follow user", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetFollowingSuccess(FollowResponse response) {
        isFollowing = response.isFollowing();
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

        Toast.makeText(this, "Failed action: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}