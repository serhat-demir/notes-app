package com.serhat.notesapp.data.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.room.NoteDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteRepository {
    private NoteDao noteDao;
    private MutableLiveData<List<Note>> notes;
    private MutableLiveData<Note> note;

    private MutableLiveData<String> toastObserverNotes, toastObserverAddNote, toastObserverNoteDetails, toastObserverEditNote;
    private MutableLiveData<Boolean> onSuccessObserverAddNote, onSuccessObserverDeleteNote, onSuccessObserverEditNote;

    public NoteRepository(NoteDao noteDao) {
        this.noteDao = noteDao;
        notes = new MutableLiveData<>();
        note = new MutableLiveData<>();
    }

    public MutableLiveData<List<Note>> getNotes() {
        return notes;
    }

    public MutableLiveData<Note> getNote() {
        return note;
    }

    public MutableLiveData<String> getToastObserverNotes() {
        toastObserverNotes = new MutableLiveData<>();
        return toastObserverNotes;
    }

    public MutableLiveData<String> getToastObserverAddNote() {
        toastObserverAddNote = new MutableLiveData<>();
        return toastObserverAddNote;
    }

    public MutableLiveData<Boolean> getOnSuccessObserverAddNote() {
        onSuccessObserverAddNote = new MutableLiveData<>();
        return onSuccessObserverAddNote;
    }

    public MutableLiveData<String> getToastObserverNoteDetails() {
        toastObserverNoteDetails = new MutableLiveData<>();
        return toastObserverNoteDetails;
    }

    public MutableLiveData<Boolean> getOnSuccessObserverDeleteNote() {
        onSuccessObserverDeleteNote = new MutableLiveData<>();
        return onSuccessObserverDeleteNote;
    }

    public MutableLiveData<String> getToastObserverEditNote() {
        toastObserverEditNote = new MutableLiveData<>();
        return toastObserverEditNote;
    }

    public MutableLiveData<Boolean> getOnSuccessObserverEditNote() {
        onSuccessObserverEditNote = new MutableLiveData<>();
        return onSuccessObserverEditNote;
    }

    public void loadNotes() {
        noteDao.getNotes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onSuccess(List<Note> newNotes) {
                        notes.setValue(newNotes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastObserverNotes.setValue("Notlar yüklenemedi.");
                    }
                });
    }

    public void getNoteDetails(int note_id) {
        noteDao.getNoteDetails(note_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Note>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onSuccess(Note newNote) {
                        note.setValue(newNote);
                    }

                    @Override
                    public void onError(Throwable e) { }
                });
    }

    public void addNote(String note_title, String note_content, int[] colors) {
        if (note_title.isEmpty() || note_content.isEmpty()) {
            toastObserverAddNote.setValue("Not başlığı ve içeriği boş olamaz.");
        } else {
            int randomIndex = new Random().nextInt(colors.length);
            int randomColor = colors[randomIndex];

            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MMM d, yyyy", new Locale("tr"));
            Note note = new Note(0, note_title, note_content, df.format(new Date()), randomColor);
            noteDao.addNote(note).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onComplete() {
                            onSuccessObserverAddNote.setValue(true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            toastObserverAddNote.setValue("Not kaydedilemedi.");
                        }
                    });
        }
    }

    public void editNote(int note_id, String note_title, String note_content, String created_at, int color) {
        if (note_title.isEmpty() || note_content.isEmpty()) {
            toastObserverEditNote.setValue("Not başlığı ve içeriği boş olamaz.");
        } else {
            Note note = new Note(note_id, note_title, note_content, created_at, color);
            noteDao.editNote(note).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onComplete() {
                            onSuccessObserverEditNote.setValue(true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            toastObserverEditNote.setValue("Not düzenlenemedi.");
                        }
                    });
        }
    }

    public void deleteNote(int note_id) {
        Note note = new Note(note_id, "", "", "", 0);
        noteDao.deleteNote(note).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onComplete() {
                        onSuccessObserverDeleteNote.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        toastObserverNoteDetails.setValue("Not silinemedi.");
                    }
                });
    }
}
