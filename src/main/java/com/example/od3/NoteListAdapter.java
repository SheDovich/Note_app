package com.example.od3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteListAdapter extends ArrayAdapter<Note> {

    public NoteListAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(note.getNoteText());

        convertView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        return convertView;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
}

