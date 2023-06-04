package com.example.od3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NoteDetailFragment extends Fragment {

    private EditText editText;
    private Note note;
    private NoteDatabaseHelper databaseHelper;

    public NoteDetailFragment() {
    }

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("note", note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new NoteDatabaseHelper(requireContext());
        if (getArguments() != null) {
            note = getArguments().getParcelable("note");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);

        editText = view.findViewById(R.id.editText);

        if (note != null) {
            editText.setText(note.getNoteText());
        }

        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveNote());

        Button deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> deleteNote());

        Button shareButton = view.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v -> shareNote());

        return view;
    }

    private void shareNote() {
        String noteText = editText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, noteText);
        startActivity(Intent.createChooser(intent, "Share Note"));
    }
    private void saveNote() {
        String updatedNoteText = editText.getText().toString();
        if (note != null) {
            note.setNoteText(updatedNoteText);
            databaseHelper.update(note.getId(), updatedNoteText);
        } else {
            long id = databaseHelper.insert(updatedNoteText);
            note = new Note(id, updatedNoteText);
        }
        requireActivity().getSupportFragmentManager().popBackStack();
        Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show();

    }

    private void deleteNote() {
        if (note != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        databaseHelper.delete(note.getId());
                        requireActivity().getSupportFragmentManager().popBackStack();
                        Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}

