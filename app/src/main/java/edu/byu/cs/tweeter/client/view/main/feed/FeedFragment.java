package edu.byu.cs.tweeter.client.view.main.feed;

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
import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedFragment extends Fragment implements FeedPresenter.View {

    private static final String LOG_TAG = "FeedFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    public static FeedFragment newInstance(User user, AuthToken authToken) {
        FeedFragment fragment = new FeedFragment();

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

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class FeedHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView userMessage;

        FeedHolder(@NonNull View itemView, int viewType) {
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

    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedHolder> implements GetFeedTask.Observer {

        private final List<Status> feed = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Status lastFeed;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Status> newFeed) {
            int startInsertPosition = feed.size();
            feed.addAll(newFeed);
            this.notifyItemRangeInserted(startInsertPosition, newFeed.size());
        }


        void addItem(Status user) {
            feed.add(user);
            this.notifyItemInserted(feed.size() - 1);
        }


        void removeItem(Status user) {
            int position = feed.indexOf(user);
            feed.remove(position);
            this.notifyItemRemoved(position);
        }


        @NonNull
        @Override
        public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.post_row, parent, false);
            }

            return new FeedHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedHolder feedHolder, int position) {
            if(!isLoading) {
                feedHolder.bindUser(feed.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return feed.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == feed.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getfeedTask = new GetFeedTask(presenter, this);
            FeedRequest request = new FeedRequest(PAGE_SIZE, lastFeed);
            getfeedTask.execute(request);
        }

        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            List<Status> feed = feedResponse.getFeed();

            lastFeed = (feed.size() > 0) ? feed.get(feed.size() -1) : null;
            hasMorePages = feedResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(feed);
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
            removeItem(feed.get(feed.size() - 1));
        }
    }

    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
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
