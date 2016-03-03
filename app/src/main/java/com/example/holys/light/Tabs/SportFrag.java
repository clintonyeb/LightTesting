package com.example.holys.light.Tabs;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Toast;

import com.example.holys.light.DATABASE_DIR.NewsContract;
import com.example.holys.light.FetchData.FetchSports;


public class SportFrag extends TabsSuperClass
        implements LoaderManager.LoaderCallbacks<Cursor>{

    FetchSports mAsyncTodo;

    public SportFrag () {}


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
            layout.setVisibility(View.VISIBLE);
            CallFetchData();
        }
        else
        {
            layout.setVisibility(View.GONE);
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
