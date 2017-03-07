package edgedev.andelaproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edgedev.andelaproject.Model.LagJavaGitHubProfile;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */
public class DatabaseAccessObject {
    private static DatabaseAccessObject sInstance = null;

    public static DatabaseAccessObject getsInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseAccessObject();
        }
        return sInstance;
    }

    public boolean storePosts(Context context, ArrayList<LagJavaGitHubProfile> profiles) {
        ArrayList<LagJavaGitHubProfile> storedPosts = DatabaseAccessObject.getsInstance().getEntriesFromDB(context);
        SQLiteDatabase db = new MyDB(context).getWritableDatabase();

//        if (storedPosts == null){
//            return false;
//        } else {
        db.beginTransaction();

        for (LagJavaGitHubProfile freshProfile : profiles) {
            boolean inDB = false;
            for (LagJavaGitHubProfile storedProfiles : storedPosts) {
                if (freshProfile.getGithub_username().equals(storedProfiles.getGithub_username())) {
                    inDB = true;
                    //TODO Checks if The Profile is already in the Database, if not it, saves it.
                }
            }
            if (!inDB) {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.PostTable.GITHUB_PROFILE_ID, freshProfile.getProfile_id());
                cv.put(DatabaseContract.PostTable.GITHUB_USERNAME, freshProfile.getGithub_username());
                cv.put(DatabaseContract.PostTable.GITHUB_PROFILE_URL, freshProfile.getGithub_profile_url());
                cv.put(DatabaseContract.PostTable.GITHUB_IMAGE_URL, freshProfile.getImage_url());
                cv.put(DatabaseContract.PostTable.GITHUB_PROFILE_SCORE, freshProfile.getScore());

                long result = db.insert(DatabaseContract.PostTable.TABLE_NAME, null, cv);

                if (result < 0) {
                    return false;
                }
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return true;
//        }
    }

    public ArrayList<LagJavaGitHubProfile> getEntriesFromDB(Context context) {

        SQLiteDatabase db = new MyDB(context).getWritableDatabase();

        Cursor cursor = db.query(DatabaseContract.PostTable.TABLE_NAME, null, null, null, null, null, null);
        LagJavaGitHubProfile mLagJavaGitHubProfile;
        ArrayList<LagJavaGitHubProfile> profiles = new ArrayList<>();


        if (cursor != null) {


            while (cursor.moveToNext()) {

                    int a = cursor.getInt(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_PROFILE_ID));
                    String b = cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_USERNAME));
                    String c = cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_PROFILE_URL));
                    String d = cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_IMAGE_URL));
                    Double e = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_PROFILE_SCORE)));


            mLagJavaGitHubProfile = new LagJavaGitHubProfile(a,b,c,d,e);


                profiles.add(mLagJavaGitHubProfile);
            }
        }
        cursor.close();
        db.close();
        return profiles;
    }

    public ArrayList<LagJavaGitHubProfile> getEntryByUsername(Context context, String username) {

        SQLiteDatabase db = new MyDB(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                        + DatabaseContract.PostTable.TABLE_NAME
                        + " WHERE "
                        + DatabaseContract.PostTable.GITHUB_USERNAME
                        + " = ? ORDER BY " + DatabaseContract.PostTable.GITHUB_PROFILE_ID + " ASC"
                , new String[]{username});

        LagJavaGitHubProfile lagJavaGitHubProfile;
        ArrayList<LagJavaGitHubProfile> profiles = new ArrayList<>();

        while (cursor.moveToNext()) {
            lagJavaGitHubProfile = new LagJavaGitHubProfile(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_PROFILE_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_PROFILE_URL)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_IMAGE_URL)),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseContract.PostTable.GITHUB_PROFILE_SCORE)))
            );
            profiles.add(lagJavaGitHubProfile);
        }

        cursor.close();
        db.close();
        return profiles;
    }

    public int totalNumberOFrows(Context context) {
        SQLiteDatabase db = new MyDB(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.PostTable.TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
