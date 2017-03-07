package edgedev.andelaproject.Activites_and_Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import edgedev.andelaproject.Connection.ConnectionManager;
import edgedev.andelaproject.Database.DatabaseAccessObject;
import edgedev.andelaproject.Model.LagJavaGitHubProfile;
import edgedev.andelaproject.R;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

public class SingleProfileFragment extends Fragment {

    public static final String DEV_USERNAME = "username";
    public String username;
    private LagJavaGitHubProfile mLagJavaGitHubProfile;
    private String profileUrl_String;
    private String profileUsername_String;


    public static Fragment newInstance(String github_username) {
        Bundle args = new Bundle();
        args.putString(DEV_USERNAME, github_username);
        SingleProfileFragment readNewsFragment = new SingleProfileFragment();
        readNewsFragment.setArguments(args);
        return readNewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = (String) getArguments().get(DEV_USERNAME);
        mLagJavaGitHubProfile = DatabaseAccessObject.getsInstance().getEntryByUsername(getActivity(), username).get(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        TextView username = (TextView) view.findViewById(R.id.dev_githubUsername_Fragment);
        TextView profileURL = (TextView) view.findViewById(R.id.dev_githubProfileUrl_Fragment);
        NetworkImageView profileImage = (NetworkImageView) view.findViewById(R.id.profileImage_Fragment);

        profileImage.setImageUrl(mLagJavaGitHubProfile.getImage_url(), ConnectionManager.getImageLoader(getContext()));


        profileUrl_String = mLagJavaGitHubProfile.getGithub_profile_url();
        profileUsername_String = mLagJavaGitHubProfile.getGithub_username();

        profileURL.setText(profileUrl_String);
        username.setText(profileUsername_String);

        profileURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(profileUrl_String));
                startActivity(i);
            }
        });

        return view;
    }
}