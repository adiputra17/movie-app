package com.example.adiputra.movie_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.adiputra.movie_app.Activity.DetailActivity;

/**
 * Created by adiputra on 5/22/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ TableData.TableInfo.TABLE_NAME+"("+
            TableData.TableInfo.MOVIE_ID+" TEXT,"+
            TableData.TableInfo.POSTER_PATH+" TEXT)";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database Operations","Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations","Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void create(DatabaseOperations dop, String movie_id, String poster_path){
        SQLiteDatabase db = dop.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TableData.TableInfo.TABLE_NAME+" " +
                "WHERE "+TableData.TableInfo.MOVIE_ID+"='"+movie_id+"'", null);
        if(c.moveToFirst()) {
            delete(movie_id);
        }
        else {
            SQLiteDatabase SQ = dop.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableData.TableInfo.MOVIE_ID, movie_id);
            cv.put(TableData.TableInfo.POSTER_PATH, poster_path);
            long k = SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);
            Log.d("Database Operations","1 row inserted - \n"+cv+"-"+k);
        }
    }

    public Cursor read(DatabaseOperations dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] coloums = {
            TableData.TableInfo.MOVIE_ID,
            TableData.TableInfo.POSTER_PATH,
        };
        Cursor CR = SQ.query(
                TableData.TableInfo.TABLE_NAME,
                coloums,
                null,
                null,
                null,
                null,
                null);
        return CR;
    }

    public void delete(String movie_id){
        this.getWritableDatabase().delete(TableData.TableInfo.TABLE_NAME,""+TableData.TableInfo.MOVIE_ID+"='"+movie_id+"'",null);
    }
}
