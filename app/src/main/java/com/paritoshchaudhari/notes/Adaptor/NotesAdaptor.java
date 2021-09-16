package com.paritoshchaudhari.notes.Adaptor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paritoshchaudhari.notes.Activity.UpdateNoteActivity;
import com.paritoshchaudhari.notes.MainActivity;
import com.paritoshchaudhari.notes.Model.Notes;
import com.paritoshchaudhari.notes.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdaptor extends RecyclerView.Adapter<NotesAdaptor.notesViewHolder> {


    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;


    public NotesAdaptor(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity=mainActivity;
        this.notes=notes;
        allNotesItem= new ArrayList<>(notes);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchNotes(List<Notes> filteredNotes){
        this.notes = filteredNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {

        Notes note = notes.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }

        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);

            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subTitle",note.notesSubtitle);
            intent.putExtra("Priority",note.notesPriority);
            intent.putExtra("note",note.notes);

            mainActivity.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    protected static class notesViewHolder extends RecyclerView.ViewHolder{

        TextView title,subtitle,notesDate;
        View notesPriority;
        public notesViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.notesTitle);
            subtitle = itemView.findViewById(R.id.notesSubtitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
