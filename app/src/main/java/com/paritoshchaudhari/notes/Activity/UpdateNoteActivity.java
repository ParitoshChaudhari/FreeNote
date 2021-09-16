package com.paritoshchaudhari.notes.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.paritoshchaudhari.notes.Model.Notes;
import com.paritoshchaudhari.notes.R;
import com.paritoshchaudhari.notes.ViewModel.NotesViewModel;
import com.paritoshchaudhari.notes.databinding.ActivityUpdateNoteBinding;

import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    String priority = "1";
    NotesViewModel notesViewModel;
    String sTitle,sSubTitle,sNotes,sPriority;
    int iId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iId = getIntent().getIntExtra("id",0);
        sTitle = getIntent().getStringExtra("title");
        sSubTitle = getIntent().getStringExtra("subTitle");
        sNotes = getIntent().getStringExtra("note");
        sPriority = getIntent().getStringExtra("Priority");


        binding.updateNotesTitle.setText(sTitle);
        binding.updateNotesSubtitle.setText(sSubTitle);
        binding.updateNotes.setText(sNotes);

        switch (sPriority) {
            case "1":
                binding.greenPriority.setImageResource(R.drawable.icon_done);
                break;
            case "2":
                binding.yellowPriority.setImageResource(R.drawable.icon_done);
                break;
            case "3":
                binding.redPriority.setImageResource(R.drawable.icon_done);
                break;
        }

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(R.drawable.icon_done);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            priority = "1";
        });


        binding.yellowPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.icon_done);
            binding.redPriority.setImageResource(0);
            priority ="2";
        });


        binding.redPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.icon_done);
            priority="3";
        });

        binding.updateNotesBtn.setOnClickListener(v -> {

            String title = binding.updateNotesTitle.getText().toString();
            String subtitle=binding.updateNotesSubtitle.getText().toString();
            String notes=binding.updateNotes.getText().toString();

            UpdateNotes(title,subtitle,notes);
        });

    }

    private void UpdateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM dd,yyyy",date.getTime());

        Notes updateNotes = new Notes();

        updateNotes.id = iId;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subtitle;
        updateNotes.notes= notes;
        updateNotes.notesPriority=priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);

        /* toast to inform user that note updated successfully */
        Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.ic_delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNoteActivity.this,R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNoteActivity.this).inflate(R.layout.delete_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheet));
            sheetDialog.setContentView(view);
            sheetDialog.show();

            TextView no,yes;

            no = view.findViewById(R.id.delete_no);
            yes = view.findViewById(R.id.delete_yes);

            //yes button on click
            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iId);
                finish();
            });


            //no button on click
            no.setOnClickListener(v -> {
            sheetDialog.dismiss();
            });

        }
        return true;
    }
}