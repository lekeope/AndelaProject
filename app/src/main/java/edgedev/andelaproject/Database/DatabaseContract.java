package edgedev.andelaproject.Database;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */

class DatabaseContract {
    static final String DB_NAME = "profile_database.db";

     abstract class PostTable{
         static final String TABLE_NAME = "github_profile_java_dev_lagos";

        static final String GITHUB_PROFILE_ID = "ID";
        static final String GITHUB_USERNAME ="USERNAME";
        static final String GITHUB_PROFILE_URL ="PROFILEURL";
        static final String GITHUB_IMAGE_URL ="IMAGEURL";
        static final String GITHUB_PROFILE_SCORE ="SCORE";
    }
}
