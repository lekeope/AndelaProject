package edgedev.andelaproject.Activites_and_Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import edgedev.andelaproject.Adapters.MyAdapter;
import edgedev.andelaproject.Database.DatabaseAccessObject;
import edgedev.andelaproject.Model.DownloadAndParseData;
import edgedev.andelaproject.Model.LagJavaGitHubProfile;
import edgedev.andelaproject.R;

public class MainActivity extends AppCompatActivity {

    private static final String the_nextpageURL = "https://api.github.com/search/users?q=location:lagos+language:java&page=";
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle(R.string.toolbar_subtitle);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(myAdapter);
        ArrayList<LagJavaGitHubProfile>  retrieved;

        if (DatabaseAccessObject.getsInstance().totalNumberOFrows(this) == 0) {
            new DownloadAndParseData(this, myAdapter).allJavaDevProfiles(the_nextpageURL);
        } else {
            retrieved = DatabaseAccessObject.getsInstance().getEntriesFromDB(this);
            addPostsToAdapter(retrieved);
            new DownloadAndParseData(this, myAdapter).allJavaDevProfiles(the_nextpageURL);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sync_profiles:
                new DownloadAndParseData(this, myAdapter).allJavaDevProfiles(the_nextpageURL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addPostsToAdapter(ArrayList<LagJavaGitHubProfile> retrieved) {
        myAdapter.addProfiles(retrieved);
    }
}
