package com.example.od3;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public class NoteListFragment extends Fragment implements NoteListAdapter.ItemClickListener {

    private ListView listView;
    private NoteListAdapter adapter;
    private ArrayList<Note> notesList;
    private NoteDatabaseHelper databaseHelper;
    private SearchView searchView;
    private void displayNoteDetailFragment(Note note) {
        NoteDetailFragment fragment = NoteDetailFragment.newInstance(note);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadNotesFromDatabase() {
        Cursor cursor = databaseHelper.getAllNotes();
        if (cursor != null && cursor.moveToFirst()) {
            notesList.clear();
            do {
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String noteText = cursor.getString(cursor.getColumnIndex("note"));
                Note note = new Note(id, noteText);
                notesList.add(note);
            } while (cursor.moveToNext());
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new NoteDatabaseHelper(requireContext());
    }


    @Override
    public void onItemClick(int position) {
        Note selectedNote = notesList.get(position);
        displayNoteDetailFragment(selectedNote);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        listView = view.findViewById(R.id.listView);
        searchView = view.findViewById(R.id.searchView);
        notesList = new ArrayList<>();
        adapter = new NoteListAdapter(requireContext(), notesList);
        listView.setAdapter(adapter);

        adapter.setItemClickListener(this);

        loadNotesFromDatabase();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNotes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchNotes(newText);
                return true;
            }
        });

        return view;
    }

    private void searchNotes(String query) {
        Cursor cursor = databaseHelper.getAllNotes();

        if (cursor != null) {
            cursor.moveToFirst();
            notesList.clear();

            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String noteText = cursor.getString(cursor.getColumnIndex("note"));

                if (noteText.toLowerCase().contains(query.toLowerCase())) {
                    Note note = new Note(id, noteText);
                    notesList.add(note);
                }

                cursor.moveToNext();
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }
}


