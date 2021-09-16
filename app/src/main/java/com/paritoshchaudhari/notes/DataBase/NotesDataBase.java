package com.paritoshchaudhari.notes.DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.paritoshchaudhari.notes.Dao.NotesDao;
import com.paritoshchaudhari.notes.Model.Notes;

@Database(entities ={Notes.class}, version = 1)
public abstract class NotesDataBase extends RoomDatabase {

    public abstract NotesDao notesDao();
    public static NotesDataBase INSTANCE;

    public static NotesDataBase getDataBaseInstance(Context context){

        if(INSTANCE==null){

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDataBase.class,
                    "Notes_DataBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
