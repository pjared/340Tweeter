package edu.byu.cs.tweeter.client.view.main.story;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryFragment extends Fragment implements StoryPresenter.View {

    private static final String LOG_TAG = "FeedFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private StoryPresenter presenter;

    private StoryRecyclerViewAdapter feedRecyclerViewAdapter;

    public static StoryFragment newInstance(User user, AuthToken authToken) {
        StoryFragment fragment = new StoryFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new StoryPresenter(this);

        RecyclerView StoryRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        StoryRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        StoryRecyclerView.setAdapter(feedRecyclerViewAdapter);

        StoryRecyclerView.addOnScrollListener(new StoryRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class StoryHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView userMessage;

        StoryHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);
            userMessage = itemView.findViewById(R.id.postMessage);
        }

        void bindUser(Status status) {
            User poster = status.getPoster();
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(poster.getImageBytes()));
            userAlias.setText(poster.getAlias());
            userName.setText(poster.getName());
            userMessage.setText(status.getMessage());
        }
    }

    private class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryHolder> implements GetStoryTask.Observer {

        private final List<Status> story = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Status lastStory;

        private boolean hasMorePages;
        private boolean isLoading = false;

        StoryRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Status> newFeed) {
            int startInsertPosition = story.size();
            story.addAll(newFeed);
            this.notifyItemRangeInserted(startInsertPosition, newFeed.size());
        }


        void addItem(Status user) {
            story.add(user);
            this.notifyItemInserted(story.size() - 1);
        }


        void removeItem(Status user) {
            int position = story.indexOf(user);
            story.remove(position);
            this.notifyItemRemoved(position);
        }


        @NonNull
        @Override
        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.post_row, parent, false);
            }

            return new StoryHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull StoryHolder storyHolder, int position) {
            if(!isLoading) {
                storyHolder.bindUser(story.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return story.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == story.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetStoryTask getStoryTask = new GetStoryTask(presenter, this);
            StoryRequest request = new StoryRequest(PAGE_SIZE, lastStory);
            getStoryTask.execute(request);
        }

        @Override
        public void storyRetrieved(StoryResponse storyResponse) {
            List<Status> story = storyResponse.getStory();

            lastStory = (story.size() > 0) ? story.get(story.size() -1) : null;
            hasMorePages = storyResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(story);
        }

        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        private void addLoadingFooter() {
            addItem(new Status(user, "Hi"));
        }

        private void removeLoadingFooter() {
            removeItem(story.get(story.size() - 1));
        }
    }

    private class StoryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        StoryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
