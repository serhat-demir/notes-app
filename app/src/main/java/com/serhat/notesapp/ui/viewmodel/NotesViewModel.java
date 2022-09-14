package com.serhat.notesapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.data.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotesViewModel extends ViewModel {
    private NoteRepository nRepo;
    public MutableLiveData<List<Note>> notes;
    public MutableLiveData<String> toastObserverNotes;

    @Inject
    public NotesViewModel(NoteRepository nRepo) {
        this.nRepo = nRepo;
        notes = nRepo.getNotes();
        toastObserverNotes = nRepo.getToastObserverNotes();
    }

    public void loadNotes() {
        nRepo.loadNotes();
    }
}
