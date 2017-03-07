package edgedev.andelaproject.Database;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

public class DatabaseContract {
    public static final String DB_NAME = "profile_database.db";

    public abstract class PostTable{
        public static final String TABLE_NAME = "git_hub_profile_of_java_developer_in_lagos";

        public static final String GITHUB_PROFILE_ID = "profile_id";
        public static final String GITHUB_USERNAME ="username";
        public static final String GITHUB_PROFILE_URL ="profile";
        public static final String GITHUB_IMAGE_URL ="image";
        public static final String GITHUB_PROFILE_SCORE ="score";

    }
}
