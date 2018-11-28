package edgedev.andelaproject.Activites_and_Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

    public static final String API_URL = " https://api.github.com/search/users?q=location:lagos+language:java";
    private static final String the_nextpageURL = "https://api.github.com/search/users?q=location:lagos+language:java&page=";
    private CoordinatorLayout mCoordinatorLayout;
    private ArrayList<LagJavaGitHubProfile> retrieved;
    private MyAdapter myAdapter;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle("Java Developers - Lagos");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(myAdapter);
        retrieved = new ArrayList<>();

        if (DatabaseAccessObject.getsInstance().totalNumberOFrows(this) == 0) {
            //boolean success = new DownloadAndParseData(this, myAdapter).download_profiles(API_URL);
            new DownloadAndParseData(this, myAdapter).allJavaDevProfiles(the_nextpageURL);
            //theMethod(success);
        } else {
            retrieved = DatabaseAccessObject.getsInstance().getEntriesFromDB(this);
            addPostsToAdapter(retrieved);
            //new DownloadAndParseData(this, myAdapter).download_profiles(API_URL);
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
//              boolean success = new DownloadAndParseData(this,myAdapter).download_profiles(API_URL);
                new DownloadAndParseData(this, myAdapter).allJavaDevProfiles(the_nextpageURL);
//                theMethod(success);
//                if (success) {
//                    theMethod(success);
//                } else {
//                    snackbarMethod();
//                    snackbar.show();
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void snackbarMethod() {
//        snackbar = Snackbar.make(mCoordinatorLayout, "Error Fetching Data", Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        boolean success = new DownloadAndParseData(MainActivity.this, myAdapter).download_profiles(API_URL);
//                        if (success) {
//                            theMethod(success);
//                        } else {
//                            snackbarMethod();
//                            snackbar.show();
//                        }
//                    }
//                });
//        snackbar.setActionTextColor(Color.WHITE);
//        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.YELLOW);
//
//    }

    private void addPostsToAdapter(ArrayList<LagJavaGitHubProfile> retrieved) {
        myAdapter.addProfiles(retrieved);
    }
}

