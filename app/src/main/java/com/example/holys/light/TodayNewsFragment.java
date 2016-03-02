package com.example.holys.light;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TodayNewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public static Menu refreshMenu;
    public static MenuItem m;

    public TodayNewsFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayNewsFragment newInstance (String param1, String param2) {
        TodayNewsFragment fragment = new TodayNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_today_news, container, false);
        // Inflate the layout for this fragment
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);



        final TabLayout.Tab World = tabLayout.newTab();
        final TabLayout.Tab Science = tabLayout.newTab();
        final TabLayout.Tab Sport = tabLayout.newTab();
        final TabLayout.Tab Culture = tabLayout.newTab();
        final TabLayout.Tab LifeStyle = tabLayout.newTab();

        World.setText("Life and Style");
        Science.setText("Technology");
        Sport.setText("Sports");
        Culture.setText("Culture");
        LifeStyle.setText("World");

        //News1.setIcon(R.drawable.news);
        tabLayout.addTab(World, 0);
        tabLayout.addTab(Science, 1);
        tabLayout.addTab(Sport, 2);
        tabLayout.addTab(Culture, 3);
        tabLayout.addTab(LifeStyle, 4);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.drawable.tab_selected));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.icons));

        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager(), tabLayout.getTabCount());

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected (TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected (TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                resetUpdating();
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:

                        //setting Home as White and rest grey
                        //and like wise for all other positions

                        /*home.setIcon(R.drawable.ic_home_white);
                        inbox.setIcon(R.drawable.ic_inbox_grey);
                        star.setIcon(R.drawable.ic_star_grey);*/
                        break;
                    case 1:
                       /* home.setIcon(R.drawable.ic_home_grey);
                        inbox.setIcon(R.drawable.ic_inbox_white);
                        star.setIcon(R.drawable.ic_star_grey);*/
                        break;
                    case 2:
                        /*home.setIcon(R.drawable.ic_home_grey);
                        inbox.setIcon(R.drawable.ic_inbox_grey);
                        star.setIcon(R.drawable.ic_star_white);*/
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                resetUpdating();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(tabsAdapter);
        setHasOptionsMenu(true);
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_menu, menu);
        refreshMenu = menu;
        m = refreshMenu.findItem(R.id.action_refreshing);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public void resetUpdating()
    {
        if(m != null)
        {
            // Remove the animation.
            if(m.getActionView() != null)
            {
                m.getActionView().clearAnimation();
                m.setActionView(null);
            }

        }
    }

}
