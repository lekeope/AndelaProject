package edgedev.andelaproject.Activites_and_Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

    private RecyclerView mRecyclerView;
    private ArrayList<LagJavaGitHubProfile> profiles = new ArrayList<>();
//    public static final String API_URL = "https://api.github.com/search/users?q=java+location:lagos";
    public static final String API_URL = " https://api.github.com/search/users?q=location:lagos+language:java";
    private CoordinatorLayout mCoordinatorLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage(getString(R.string.loading));
//        progressDialog.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(myAdapter);
        ArrayList<LagJavaGitHubProfile> retrieved = new ArrayList<>();


//        if (DatabaseAccessObject.getsInstance().totalNumberOFrows(this)<1){
//            boolean successful = new DownloadAndParseData(this).download_users(API_URL);
//            if (successful) {
//                new DatabaseAccessObject().storePosts(this, profiles);
//                retrieved = DatabaseAccessObject.getsInstance().getEntriesFromDB(this);
//            } else {
//                snackBarMethod();
//            }
//        } else {
//            retrieved = DatabaseAccessObject.getsInstance().getEntriesFromDB(this);
//            new DownloadAndParseData(this).download_users(API_URL);
//        }

        LagJavaGitHubProfile lagJavaGitHubProfile;

        for (int i=0 ; i<20; i++){
            lagJavaGitHubProfile = new LagJavaGitHubProfile(i,"username"+i, "http:///www.url.com"+i,"imgur.com/abcdefgh", 2.3455);
            profiles.add(lagJavaGitHubProfile);
        }

        new DatabaseAccessObject().storePosts(this, profiles);
        retrieved = DatabaseAccessObject.getsInstance().getEntriesFromDB(this);
        myAdapter.addPosts(retrieved);
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
            case R.id.retry_download_post:
                new DownloadAndParseData(this).download_users(API_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void snackBarMethod() {
//
//        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Error Occured While Fetching Data", Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        progressDialog.show();
//                        boolean success = new DownloadAndParseData(MainActivity.this).download_users(API_URL);
//                        if (!success){
//                            snackBarMethod();
//                        }
//                        progressDialog.dismiss();
//                    }
//                });
////        snackbar.setActionTextColor(Color.BLUE);
////        View sbView = snackbar.getView();
////        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
////        textView.setTextColor(Color.YELLOW);
//        snackbar.show();
//
//    }
}
