package com.paritoshchaudhari.notes.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.paritoshchaudhari.notes.Dao.NotesDao;
import com.paritoshchaudhari.notes.DataBase.NotesDataBase;
import com.paritoshchaudhari.notes.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;

    public LiveData<List<Notes>> lowToHigh;
    public LiveData<List<Notes>> highToLow;

    public NotesRepository(Application application){
        NotesDataBase dataBase = NotesDataBase.getDataBaseInstance(application);
        notesDao = dataBase.notesDao();
        getAllNotes = notesDao.getAllNotes();
        lowToHigh = notesDao.lowToHigh();
        highToLow = notesDao.highToLow();
    }

    public void insertNotes(Notes notes){
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes){
        notesDao.updateNotes(notes);
    }

}
