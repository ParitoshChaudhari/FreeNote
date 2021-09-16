package com.paritoshchaudhari.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paritoshchaudhari.notes.Activity.InsertNotesActivity;
import com.paritoshchaudhari.notes.Adaptor.NotesAdaptor;
import com.paritoshchaudhari.notes.Model.Notes;
import com.paritoshchaudhari.notes.ViewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotesViewModel notesViewModel;
    FloatingActionButton newNotesBtn;
    RecyclerView notesRecycler;
    NotesAdaptor adaptor;
    TextView noFilter,highToLow,lowToHigh;

    List<Notes> filteredNotesAllList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecycler = findViewById(R.id.notesRecycler);
        noFilter = findViewById(R.id.noFilter);
        highToLow = findViewById(R.id.highToLow);
        lowToHigh = findViewById(R.id.lowToHigh);

        noFilter.setBackgroundResource(R.drawable.filter_selected_shape);

        noFilter.setOnClickListener(v -> {
            loadData(0);
            highToLow.setBackgroundResource(R.drawable.filter_unselected_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_unselected_shape);
            noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
        });

        lowToHigh.setOnClickListener(v -> {
            loadData(1);
            highToLow.setBackgroundResource(R.drawable.filter_unselected_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_selected_shape);
            noFilter.setBackgroundResource(R.drawable.filter_unselected_shape);
        });

        highToLow.setOnClickListener(v -> {
            loadData(2);
            highToLow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowToHigh.setBackgroundResource(R.drawable.filter_unselected_shape);
            noFilter.setBackgroundResource(R.drawable.filter_unselected_shape);
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdaptor(notes);
            }
        });

//        notesViewModel.getAllNotes.observe(this,notes -> {
//
//            notesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
//            adaptor = new NotesAdaptor(MainActivity.this,notes);
//            notesRecycler.setAdapter(adaptor);
//
//        });
    }

    private void loadData(int i) {
        if(i==0){
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdaptor(notes);
                    filteredNotesAllList = notes;
                }

            });
        }
        else if(i==1){
            notesViewModel.highToLow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdaptor(notes);
                    filteredNotesAllList = notes;
                }
            });
        }
        else if(i==2){
            notesViewModel.lowToHigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdaptor(notes);
                    filteredNotesAllList = notes;
                }
            });
        }
    }

    public void setAdaptor(List<Notes> notes ){
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            adaptor = new NotesAdaptor(MainActivity.this,notes);
            notesRecycler.setAdapter(adaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes,menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes Here...");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {
        ArrayList<Notes> FilteredNames = new ArrayList<>();


        for(Notes notes:this.filteredNotesAllList){
            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)){
                FilteredNames.add(notes);
            }
        }
        this.adaptor.searchNotes(FilteredNames);
    }
}