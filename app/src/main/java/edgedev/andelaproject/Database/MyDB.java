package edgedev.andelaproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by OPEYEMI OLORUNLEKE on 3/5/2017.
 */
public class MyDB extends SQLiteOpenHelper {

    /*
    * DATABASE VERSION
    */
    private static final int DATABASE_VERSION = 1;

    /*
    * TABLE STRINGS
    */
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA = ", ";

    /*
    * SQLite Create Table Sentence
    */
    private static final String CREATE_PROFILE_TABLE =
            "CREATE TABLE "
                    + DatabaseContract.PostTable.TABLE_NAME
                    + " ("
                    + DatabaseContract.PostTable.GITHUB_PROFILE_ID + INTEGER_TYPE + COMMA
                    + DatabaseContract.PostTable.GITHUB_USERNAME + TEXT_TYPE + COMMA
                    + DatabaseContract.PostTable.GITHUB_PROFILE_URL + TEXT_TYPE + COMMA
                    + DatabaseContract.PostTable.GITHUB_IMAGE_URL + TEXT_TYPE
                    + DatabaseContract.PostTable.GITHUB_PROFILE_SCORE + TEXT_TYPE
                    + " )";


    public MyDB(Context context) {
        super(context, DatabaseContract.DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

