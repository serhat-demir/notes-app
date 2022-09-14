package com.serhat.notesapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.data.repository.NoteRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NoteDetailsViewModel extends ViewModel {
    private NoteRepository nRepo;
    public MutableLiveData<Note> note;
    public MutableLiveData<String> toastObserverNoteDetails;
    public MutableLiveData<Boolean> onSuccessObserverDeleteNote;

    @Inject
    public NoteDetailsViewModel(NoteRepository nRepo) {
        this.nRepo = nRepo;
        note = nRepo.getNote();
        toastObserverNoteDetails = nRepo.getToastObserverNoteDetails();
        onSuccessObserverDeleteNote = nRepo.getOnSuccessObserverDeleteNote();
    }

    public void getNoteDetails(int note_id) {
        nRepo.getNoteDetails(note_id);
    }

    public void deleteNote(int note_id) {
        nRepo.deleteNote(note_id);
    }
}
