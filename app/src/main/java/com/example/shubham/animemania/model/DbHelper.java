package com.example.shubham.animemania.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shubham.animemania.utility.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * DbHelper Class for Getting data from DB ,storing results of quiz in Db
 * <p/>
 * Created by shubham on 3/1/17.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    private static SQLiteDatabase sMyDatabase;
    private static DbHelper instance;

    public DbHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.VERSION);
    }

    /**
     * Method For getting Path of Database
     *
     * @param aContext     :AppContext
     * @param databaseName :Name Of Db
     * @return String Which is name of Path
     */
    private static String getDatabasePath(Context aContext, String databaseName) {
        return "/data/data/" + aContext.getPackageName() + "/databases/"
                + databaseName;
    }

    /**
     * Method for Copying Db in Application
     *
     * @param aContext     :App Context
     * @param databaseName : name of Db
     * @throws IOException
     */
    private static void copyDb(Context aContext, String databaseName) throws IOException {
        Logger.debug(TAG, "Db Copying Started");
        InputStream myInput = aContext.getAssets().open(databaseName);
        String outFileName = getDatabasePath(aContext, databaseName);
        Logger.debug("File Path", outFileName);

        File file = new File("/data/data/" + aContext.getPackageName()
                + "/databases/");

        if (!file.exists())
            file.mkdir();


        OutputStream myOutput = new FileOutputStream(outFileName);
        //copying Db
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
        Logger.debug(TAG, "Db Copied");
    }

    /**
     * Method for Checking The DB is present or not
     *
     * @param aContext     :App Context
     * @param databaseName :Name of Db
     * @return : Is present or not
     */
    public static boolean checkDatabase(Context aContext, String databaseName) {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

            checkDB.close();
        } catch (SQLiteException e) {

            Logger.error(TAG, databaseName + " does not exists", e);
        }

        return checkDB != null;
    }

    /**
     * Method for initialising Database
     *
     * @param context      :Context of App
     * @param databaseName :Name Of Db
     */
    private static void initialize(Context context, String databaseName) {
        if (instance == null) {

            if (!checkDatabase(context, databaseName)) {

                try {
                    copyDb(context, databaseName);
                } catch (IOException e) {

                    Logger.debug(TAG, databaseName + " does not exists ");
                }
            }

            instance = new DbHelper(context);
            sMyDatabase = instance.getWritableDatabase();

            Logger.debug(TAG, "instance of  " + databaseName + " created ");
        }
    }

    /**
     * Method for Starting the Copy of Db in App
     *
     * @param context       :App Context
     * @param databaseName: Name of DB
     * @return :Instance of DbHelper Class
     */
    public static DbHelper getInstance(Context context, String databaseName) {
        initialize(context, databaseName);
        return instance;
    }

    /**
     * Method For getting Question
     *
     * @param iLevel    : Level of question for that category
     * @param iCategory : Category of Questions
     * @return : List of Questions
     */
    public static ArrayList<QuestionModel> getQuestionData(int iLevel, String iCategory) {
        Cursor cursor = null;
        ArrayList<QuestionModel> oQuestionList = new ArrayList<>();
        String[] projection = {
                Contract.TRIVIA_ROW_ID, Contract.TRIVIA_ROW_QUESTION, Contract.TRIVIA_ROW_OPTION_A,
                Contract.TRIVIA_ROW_OPTION_B, Contract.TRIVIA_ROW_OPTION_C, Contract.TRIVIA_ROW_OPTION_D,
                Contract.TRIVIA_ROW_ANSWER, Contract.TRIVIA_ROW_LEVEL, Contract.TRIVIA_ROW_CATEGORY};

        String selection = Contract.TRIVIA_ROW_LEVEL + " = ? AND " + Contract.TRIVIA_ROW_CATEGORY + " = ?";
        String[] selectionArgs = {String.valueOf(iLevel), iCategory};

        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();

            cursor = sMyDatabase.query(Contract.TRIVIA_TABLE_NAME, projection,
                    selection, selectionArgs, null, null, null);
            Logger.debug(TAG + "DB operation", "implementing getting result from database");

            if (cursor.moveToFirst()) {
                do {
                    int rowId = cursor.getInt(0);
                    String question = cursor.getString(1);
                    String optA = cursor.getString(2);
                    String optB = cursor.getString(3);
                    String optC = cursor.getString(4);
                    String optD = cursor.getString(5);
                    String answer = cursor.getString(6);
                    int level = cursor.getInt(7);
                    String category = cursor.getString(8);
                    //setting data in model
                    QuestionModel questionModel = new QuestionModel();
                    questionModel.setRowId(rowId);
                    questionModel.setQuestion(question);
                    questionModel.setOptionA(optA);
                    questionModel.setOptionB(optB);
                    questionModel.setOptionC(optC);
                    questionModel.setOptionD(optD);
                    questionModel.setAnswer(answer);
                    questionModel.setLevel(level);
                    questionModel.setCategory(category);
                    //adding question model in ArrayList
                    oQuestionList.add(questionModel);
                } while (cursor.moveToNext());
            }
            Logger.debug(TAG, "" + oQuestionList.size());
        } catch (SQLiteException | NullPointerException e) {
            e.printStackTrace();
            Logger.error(TAG, "Error in Getting Data", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return oQuestionList;
    }

    /**
     * Method For Checking The User Exist or Not
     *
     * @param userName :UserName of Player
     * @param password :Password of User
     * @return :Boolean Value for Checking
     */
    public static boolean isExist(String userName, String password) {
        Cursor cursor = null;
        String[] projection = {Contract.LOGIN_USER_NAME, Contract.LOGIN_USER_PASSWORD};
        String selection = Contract.LOGIN_USER_NAME + " = ? AND " + Contract.LOGIN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {userName, password};

        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();

            cursor = sMyDatabase.query(Contract.LOGIN_TABLE_NAME, projection,
                    selection, selectionArgs, null, null, null);
            Logger.debug(TAG, " " + cursor.getCount());
            if (cursor.getCount() > 0) {
                int length = cursor.getCount();
                return length == 1;
            }

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();

            }
        }
        return false;
    }

    /**
     * Method for adding new user
     *
     * @param userName :username of Player
     * @param name     :Name of Player
     * @param password : Password of player
     * @param avatarId :ResourceId of Drawable
     * @return : return rowId of table where new new user is added
     */
    public static boolean addRegister(String userName, String name, String password, long avatarId) {
        long outCheckingLeaderBoard = -1;
        long outCheckingLogin = -1;
        boolean outChecking = false;
        ContentValues iValues = new ContentValues();
        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();

            iValues.put(Contract.LOGIN_USER_NAME, userName);
            iValues.put(Contract.LOGIN_NAME, name);
            iValues.put(Contract.LOGIN_USER_PASSWORD, password);
            iValues.put(Contract.LOGIN_AVATAR_ID, avatarId);

            Logger.debug("Values  " + Contract.LOGIN_TABLE_NAME, iValues.getAsString(Contract.LOGIN_USER_NAME));
            Logger.debug("Values  " + Contract.LOGIN_TABLE_NAME, iValues.getAsString(Contract.LOGIN_NAME));
            Logger.debug("Values  " + Contract.LOGIN_TABLE_NAME, iValues.getAsString(Contract.LOGIN_USER_PASSWORD));
            Logger.debug("Values  " + Contract.LOGIN_TABLE_NAME, iValues.getAsString(Contract.LOGIN_AVATAR_ID));

            //inserting in table
            outCheckingLogin = sMyDatabase.insertOrThrow(Contract.LOGIN_TABLE_NAME, null, iValues);
            Logger.debug("OutCheckingLogin", " " + outCheckingLogin);
            iValues.clear();

            //inserting in LeaderBoard Table
            iValues.put(Contract.LEADERBOARD_USER_NAME, userName);
            iValues.put(Contract.LEADERBOARD_TOTAL_SCORE, 0);
            outCheckingLeaderBoard = sMyDatabase.insertOrThrow(Contract.LEADERBOARD_TABLE_NAME,
                    null, iValues);
            Logger.debug("OutCheckingLeaderboard", " " + outCheckingLeaderBoard);
            iValues.clear();
        } catch (CursorIndexOutOfBoundsException | SQLiteException | NullPointerException e) {
            e.printStackTrace();
            Logger.error(TAG + "DB operation :AddRegister :", " insertion unsuccessful due to"
                    + e.getMessage(), e);
        }

        if (outCheckingLeaderBoard != -1 && outCheckingLogin != -1)
            outChecking = true;
        Logger.debug(TAG, " " + outChecking + " " + " " + outCheckingLeaderBoard + " " + outCheckingLogin);
        return outChecking;
    }

    /**
     * Method For Getting LeaderBoardList for Setting LeaderBoard Activity
     *
     * @return :List which contains Total Score of Players and their UserName
     */
    public static List<LeaderBoardModel> getLeaderBoardList() {
        //Get LeaderBoard List
        List<LeaderBoardModel> arrayList = new ArrayList<>();
        String orderBy = Contract.LEADERBOARD_TOTAL_SCORE + " DESC";
        Cursor cursor = null;
        String[] projection = {Contract.LEADERBOARD_USER_NAME, Contract.LEADERBOARD_TOTAL_SCORE};
        try {

            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();
            cursor = sMyDatabase.query(Contract.LEADERBOARD_TABLE_NAME, projection
                    , null, null, null, null, orderBy);
            if (cursor.moveToFirst()) {
                do {
                    LeaderBoardModel list = new LeaderBoardModel();
                    list.setUserName(cursor.getString(0));
                    list.setTotalScore(cursor.getInt(1));
                    arrayList.add(list);
                } while (cursor.moveToNext());
            }

        } catch (CursorIndexOutOfBoundsException | NullPointerException | SQLiteException e) {
            Logger.error(TAG, "Error in Getting List ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return arrayList;
    }

    /**
     * Method for getting Profile of Player
     *
     * @param userName :userName of Player
     * @return : Object of ProfileDetails
     */
    public static ProfileDetails getProfileData(String userName) {
        ProfileDetails details = new ProfileDetails();
        Cursor cursorLogin = null;
        Cursor cursorLeaderBoard = null;
        String[] projectionLogin = {Contract.LOGIN_USER_NAME, Contract.LOGIN_NAME,
                Contract.LOGIN_AVATAR_ID};
        String selection = Contract.LOGIN_USER_NAME + " = ? ";
        String[] projectionLeaderBoard = {Contract.LEADERBOARD_TOTAL_SCORE};
        String[] selectionArgs = {userName};
        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();
            cursorLogin = sMyDatabase.query(Contract.LOGIN_TABLE_NAME, projectionLogin,
                    selection, selectionArgs, null, null, null);

            cursorLeaderBoard = sMyDatabase.query(Contract.LEADERBOARD_TABLE_NAME, projectionLeaderBoard,
                    selection, selectionArgs, null, null, null);
            cursorLogin.moveToFirst();
            cursorLeaderBoard.moveToFirst();
            Logger.debug(TAG, " " + cursorLogin.getCount() + " " + cursorLeaderBoard.getCount());
            if (cursorLogin.getCount() == 1 && cursorLeaderBoard.getCount() == 1) {
                details.setUserName(cursorLogin.getString(0));
                details.setName(cursorLogin.getString(1));
                details.setAvatarId(cursorLogin.getInt(2));
                details.setUserTotalScore(cursorLeaderBoard.getInt(0));
            }

        } catch (CursorIndexOutOfBoundsException | SQLiteException | NullPointerException e) {
            Logger.error(TAG, "Error in Getting Data from Login and" +
                    "leaderboard", e);
        } finally {
            if (cursorLogin != null && cursorLeaderBoard != null) {
                cursorLogin.close();
                cursorLeaderBoard.close();
            }

        }
        return details;
    }

    /**
     * Method for Getting Status of Player for That Category
     *
     * @param userName :userName of Player
     * @param category :Category which user has selected
     * @return : status of that category
     */
    public static int getStatus(String userName, String category) {
        int status = 0;
        Cursor cursor = null;
        int listSize = 3;

        String[] projection = {Contract.SCORE_LEVEL_1, Contract.SCORE_LEVEL_2};
        String selection = Contract.SCORE_USER_NAME + " = ? AND " + Contract.SCORE_CATEGORY + " = ?";
        String[] selectionArgs = {userName, category};
        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();
            cursor = sMyDatabase.query(Contract.SCORE_TABLE_NAME, projection, selection,
                    selectionArgs, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                int scoreLevel1 = cursor.getInt(0);
                int scoreLevel2 = cursor.getInt(1);
                Logger.debug("Score level1", " " + scoreLevel1 + " Score Level 2 " + scoreLevel2);
                if (scoreLevel1 == listSize) {
                    if (scoreLevel2 == listSize)
                        status = 2;
                    else
                        status = 1;
                } else
                    status = 0;

            }
        } catch (NullPointerException | SQLiteException e) {
            Logger.error(TAG, "Error in getting Status", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return status;
    }

    /**
     * Method for Getting the oldScore of Player
     *
     * @param userName    :userName
     * @param category    :Category
     * @param level:Level
     * @return :oldScore
     */
    public static int getOldScore(String userName, String category, int level) {
        int oldScore = -1;
        Cursor cursor = null;

        String[] projection = new String[1];
        String selection = Contract.SCORE_USER_NAME + " = ? AND " + Contract.SCORE_CATEGORY + " = ?";
        String[] selectionArgs = {userName, category};
        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();
            if (level == 1) {
                projection[0] = Contract.SCORE_LEVEL_1;
            }
            if (level == 2) {
                projection[0] = Contract.SCORE_LEVEL_2;
            }

            cursor = sMyDatabase.query(Contract.SCORE_TABLE_NAME, projection, selection,
                    selectionArgs, null, null, null);
            cursor.moveToFirst();
            Logger.debug(TAG + " oldScore", " " + cursor.getCount());
            if (cursor.getCount() != 0) {
                oldScore = cursor.getInt(0);
            }

        } catch (NullPointerException | SQLiteException e) {
            Logger.error(TAG, "Error in Getting OldScore ", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return oldScore;
    }

    /**
     * Method For getting Old Total Score
     *
     * @param userName:USer NAme
     * @return :oldScore
     */
    public static int getOldTotalScore(String userName) {
        int oldTotalScore = 0;
        Cursor cursor = null;
        Logger.debug(TAG + " UserName Passed", userName);
        String[] projection = {Contract.LEADERBOARD_TOTAL_SCORE};
        String selection = Contract.LEADERBOARD_USER_NAME + " = ? ";
        String[] selectionArgs = {userName};
        try {
            if (sMyDatabase.isOpen()) {
                sMyDatabase.close();
            }
            sMyDatabase = instance.getWritableDatabase();
            cursor = sMyDatabase.query(Contract.LEADERBOARD_TABLE_NAME, projection, selection,
                    selectionArgs, null, null, null);
            cursor.moveToFirst();
            Logger.debug(TAG + " in oldTotalScore", " " + cursor.getCount());
            if (cursor.getCount() != 0) {
                oldTotalScore = cursor.getInt(0);
            }

        } catch (NullPointerException | CursorIndexOutOfBoundsException | SQLiteException e) {
            Logger.error(TAG, "Error in Getting OldTotalScore ", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return oldTotalScore;
    }

    /**
     * Method For updating total score
     *
     * @param userName  :userName
     * @param userScore :total score
     * @return : no of updated rows
     */
    public static int updateTotalScore(String userName, int userScore) {
        ContentValues iValues;

        String[] selectionArgs = {userName};
        String selection = Contract.LOGIN_USER_NAME + " = ? ";
        int outUpdateRow = 0;
        iValues = new ContentValues();
        iValues.put(Contract.LEADERBOARD_TOTAL_SCORE, userScore);
        try {
            outUpdateRow = sMyDatabase.update(Contract.LEADERBOARD_TABLE_NAME,
                    iValues, selection, selectionArgs);

        } catch (NullPointerException | SQLiteException e) {
            Logger.error(TAG, "Error in Updating The TotalScore", e);
        }
        return outUpdateRow;
    }

    /**
     * Method For updating the Score of User For a particular category
     *
     * @param userName :userName of Player
     * @param category :category which user played
     * @param score    :user's Score
     * @param level    :level of category
     * @return : no of row updated
     */
    public static int updateScore(String userName, String category, int score, int level) {
        ContentValues iValues;
        String[] selectionArgs = {userName, category};
        String selection = Contract.SCORE_USER_NAME + " = ? AND " + Contract.SCORE_CATEGORY + " = ?";
        int outUpdateRow = 0;
        iValues = new ContentValues();
        try {
            if (level == 1) {
                iValues.put(Contract.SCORE_LEVEL_1, score);
            }
            if (level == 2) {
                iValues.put(Contract.SCORE_LEVEL_2, score);
            }
            outUpdateRow = sMyDatabase.update(Contract.SCORE_TABLE_NAME, iValues, selection, selectionArgs);

        } catch (NullPointerException | SQLiteException e) {
            Logger.error(TAG, "Error in updating score", e);
        } finally {
            iValues.clear();
        }
        if (outUpdateRow == 1)
            return outUpdateRow;
        else
            return 0;
    }

    /**
     * Method For AddNew Row in Score Table which has primary Key as UserName + Category
     *
     * @param userName :userName of Player
     * @param category :Category
     * @param score    :score of player
     * @return : row id of new inserted row
     */
    public static long addScore(String userName, String category, int score) {
        long outCheck = -1;
        ContentValues iValues = new ContentValues();

        iValues.put(Contract.SCORE_USER_NAME, userName);
        iValues.put(Contract.SCORE_CATEGORY, category);
        iValues.put(Contract.SCORE_LEVEL_1, score);
        iValues.put(Contract.SCORE_LEVEL_2, 0);
        try {
            outCheck = sMyDatabase.insertOrThrow(Contract.SCORE_TABLE_NAME, null, iValues);

        } catch (NullPointerException | SQLiteException e) {
            Logger.error(TAG, "Error in Inserting New row in Score table", e);
        }
        return outCheck;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}