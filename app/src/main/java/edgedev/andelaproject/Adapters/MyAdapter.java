package edgedev.andelaproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import edgedev.andelaproject.Activites_and_Fragments.ViewPagerActivity;
import edgedev.andelaproject.Connection.ConnectionManager;
import edgedev.andelaproject.Model.LagJavaGitHubProfile;
import edgedev.andelaproject.R;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context context;
    private List<LagJavaGitHubProfile> profiles = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LagJavaGitHubProfile profile = profiles.get(position);
        holder.bindProfiles(profile);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public void addPosts(ArrayList<LagJavaGitHubProfile> profiles) {
        for (LagJavaGitHubProfile devProfile : profiles) {
            this.profiles.add(devProfile);
            notifyItemInserted(profiles.size() - 1);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private NetworkImageView dev_image;
        private TextView dev_username;
        private LagJavaGitHubProfile profile_viewholder;

        public MyViewHolder(View itemView) {
            super(itemView);
            dev_image = (NetworkImageView) itemView.findViewById(R.id.gitHubProfileImage);
            dev_username = (TextView) itemView.findViewById(R.id.usernameTextView);
            itemView.setOnClickListener(this);
        }

        public void bindProfiles(LagJavaGitHubProfile profile) {
            profile_viewholder = profile;
            dev_username.setText(profile.getGithub_username());
            dev_image.setImageUrl(profile.getImage_url(), ConnectionManager.getImageLoader(context));
        }

        @Override
        public void onClick(View v) {
            Intent intent = ViewPagerActivity.newIntent(context, profile_viewholder.getGithub_username());
            context.startActivity(intent);
        }
    }

}