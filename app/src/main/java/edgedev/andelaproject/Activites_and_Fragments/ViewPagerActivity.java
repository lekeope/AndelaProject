package edgedev.andelaproject.Activites_and_Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import edgedev.andelaproject.Database.DatabaseAccessObject;
import edgedev.andelaproject.Model.LagJavaGitHubProfile;
import edgedev.andelaproject.R;

public class ViewPagerActivity extends AppCompatActivity {

    private static final String USERNAME_KEY = "username_key";
    private final String BUNDLE_TOOLBAR_SUBTITLE_KEY = "toolbar_subtitle";
    private FloatingActionButton share;
    private ViewPager mViewPager;
    private List<LagJavaGitHubProfile> lag_java_dev_profiles;
    private String username, profileUrl;
    private Toolbar toolbar;

    public static Intent newIntent(Context context, String github_username) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        intent.putExtra(USERNAME_KEY, github_username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        toolbar = (Toolbar) findViewById(R.id.toolbar_ViewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null){
            toolbar.setSubtitle(""+savedInstanceState.getString(BUNDLE_TOOLBAR_SUBTITLE_KEY));
        }

        String username_from_intent = getIntent().getStringExtra(USERNAME_KEY);

        share = (FloatingActionButton) findViewById(R.id.fab);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myText = "Check out this Awesome Developer"
                        +"\n @"+ username
                        + "\n"+ profileUrl;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "GitHub Profile");
                intent.putExtra(Intent.EXTRA_TEXT, myText);
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });


        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        lag_java_dev_profiles = DatabaseAccessObject.getsInstance().getEntriesFromDB(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                LagJavaGitHubProfile profile = lag_java_dev_profiles.get(position);
                int indexOfCurrentViewPager = mViewPager.getCurrentItem();
                username = lag_java_dev_profiles.get(indexOfCurrentViewPager).getGithub_username();
                profileUrl = lag_java_dev_profiles.get(indexOfCurrentViewPager).getGithub_profile_url();
                toolbar.setSubtitle(username);
                return SingleProfileFragment.newInstance(profile.getGithub_username());
            }

            @Override
            public int getCount() {
                return lag_java_dev_profiles.size();
            }
        });


        for (int i = 0; i < lag_java_dev_profiles.size(); i++) {
            if (lag_java_dev_profiles.get(i).getGithub_username().equals(username_from_intent)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_TOOLBAR_SUBTITLE_KEY, toolbar.getSubtitle().toString());
    }
}