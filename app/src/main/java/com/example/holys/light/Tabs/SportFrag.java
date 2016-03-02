package com.example.holys.light.Tabs;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.holys.light.DATABASE_DIR.NewsContract;
import com.example.holys.light.FetchData.FetchSports;
import com.example.holys.light.NEWS_DIR.EndlessRecyclerViewScrollListener;
import com.example.holys.light.NEWS_DIR.MyListCursorAdapter;
import com.example.holys.light.R;
import com.example.holys.light.TodayNewsFragment;


public class SportFrag extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyListCursorAdapter mAdapter;
    public RecyclerView recyclerView;
    FetchSports mAsyncTodo;
    public static int pageSize = 1;
    public boolean _isLoading = false;
    LinearLayoutManager mLinear;
    //public ProgressBar progressBar;
    public Button mButton;
    public ContentResolver mResolver;
    public SwipeRefreshLayout swipeRefreshLayout;
    public boolean CLEAR ;
    public boolean _shdClear = true;


    public SportFrag () {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SportFrag newInstance (String param1, String param2) {
        SportFrag fragment = new SportFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_refreshing)
        {
            Toast.makeText(getContext(), "Swipe to refresh", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mResolver = getActivity().getContentResolver();
       // setHasOptionsMenu(true);
    }
    private void RefreshState()
    {
        if(TodayNewsFragment.m != null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView)inflater.inflate(R.layout.progress_ring, null);
            Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.roate_refresh);
            rotate.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotate);
            TodayNewsFragment.m.setActionView(iv);
        }
    }
    public void resetUpdating()
    {
        if(TodayNewsFragment.m != null)
        {
            // Remove the animation.
            if(TodayNewsFragment.m.getActionView() != null)
            {
                TodayNewsFragment.m.getActionView().clearAnimation();
                TodayNewsFragment.m.setActionView(null);
            }

        }
    }
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_menu, menu);
        TodayNewsFragment.refreshMenu = menu;
        TodayNewsFragment.m =TodayNewsFragment. refreshMenu.findItem(R.id.action_refreshing);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(true);
        mLinear  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinear);
        mButton = (Button)rootView.findViewById(R.id.loadButton);
        pageSize = 1;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CLEAR = false;
                mButton.setVisibility(View.GONE);
                pageSize++;
                CallFetchData();
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinear) {
            @Override
            public void onLoadMore (int page, int totalItemsCount) {
                if (mButton.getVisibility() == View.GONE &&
                        !_isLoading &&
                        mAdapter.getItemCount() <= 25) {
                    mButton.setVisibility(View.VISIBLE);
                }

            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.guardian_Color, R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                if (_shdClear)
                {
                    pageSize = 1;
                    CLEAR = true;
                    _shdClear = false;
                    CallFetchData();
                }
                else
                {
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        });
        mAdapter = new MyListCursorAdapter(this.getContext(), null);
        recyclerView.setAdapter(mAdapter);
        //progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(2, null, this);
    }

    public void CallFetchData()
    {
        if (CheckConnectionState() )
        {
            mAsyncTodo = new FetchSports();
            if(!_isLoading)
            {
                RefreshState();
                mAsyncTodo.execute(SportFrag.this);
                _isLoading = true;
            }
        }
        else
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_LONG).show();
    }


    public void WaitSomeTime()
    {
        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                //Log.v("TIMER", "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                _shdClear = true;
            }
        }.start();
    }

    private  boolean CheckConnectionState()
    {
        ConnectivityManager manager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info .isConnected())
        {
            return true;
        }
        else
            return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        _isLoading = true;
        return new CursorLoader(getContext(), NewsContract.CONTENT_URI_SPORT, null, null, null, null );
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {
        _isLoading = false;
        if(data.getCount() == 0)
        {
            CallFetchData();
        }
        else
        {
            mButton.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            resetUpdating();
            WaitSomeTime();
        }

        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset (Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
