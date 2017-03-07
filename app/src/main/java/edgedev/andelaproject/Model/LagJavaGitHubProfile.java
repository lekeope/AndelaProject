package edgedev.andelaproject.Model;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

public class LagJavaGitHubProfile {
    private int profile_id;
    private String github_username;
    private String github_profile_url;
    private String image_url;
    private Double score;


    public LagJavaGitHubProfile(int profile_id, String github_username, String github_profile_url, String image_url, Double score ) {

        this.image_url = image_url;
        this.github_username = github_username;
        this.github_profile_url = github_profile_url;
        this.profile_id = profile_id;
        this.score = score;

    }

    public String getImage_url() {
        return image_url;
    }

    public String getGithub_username() {
        return github_username;
    }

    public String getGithub_profile_url() {
        return github_profile_url;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public String getScore() {
        return ""+round(score);
    }

    private double round(double value){
        long factor = (long) Math.pow(10,3);
        value = value * factor;
        long tmp = Math.round(value);

        return (double) tmp/factor;
    }
}
