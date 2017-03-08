package edgedev.andelaproject.Model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

import edgedev.andelaproject.Adapters.MyAdapter;
import edgedev.andelaproject.Connection.ConnectionManager;
import edgedev.andelaproject.Database.DatabaseAccessObject;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

public class DownloadAndParseData {
    private Context context;
    private MyAdapter myAdapter;

    public DownloadAndParseData(Context context, final MyAdapter myAdapter) {
        this.context = context;
        this. myAdapter = myAdapter;
    }

    private void download_profiles(String URL) {
        final ArrayList<LagJavaGitHubProfile> lagosJavaDevelopers = new ArrayList<>();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Model model = new Gson().fromJson(response, Model.class);
                LagJavaGitHubProfile lagosJavaDev;
                Item item;

                for (int i = 0; i < model.getItems().size(); i++) {
                    item = model.getItems().get(i);
                    lagosJavaDev = new LagJavaGitHubProfile(
                            item.get_Id(),
                            item.getLogin(),
                            item.getHtmlUrl(),
                            item.getAvatarUrl(),
                            item.getScore()
                    );
                    lagosJavaDevelopers.add(lagosJavaDev);
                }
                new DatabaseAccessObject().storePosts(context, lagosJavaDevelopers);
                myAdapter.addProfiles(lagosJavaDevelopers);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        ConnectionManager.getInstance(context).add(mStringRequest);
    }

    public void allJavaDevProfiles(String apiURL){
        for (int i=0; i<5; i++){
            download_profiles(apiURL+""+i);
        }
    }
}