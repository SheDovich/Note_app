package com.example.od3;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private long id;
    private String noteText;

    public Note() {
    }

    public Note(long id, String noteText) {
        this.id = id;
        this.noteText = noteText;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        noteText = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(noteText);
    }
}

