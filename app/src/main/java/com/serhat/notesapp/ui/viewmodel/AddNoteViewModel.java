package com.serhat.notesapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serhat.notesapp.data.repository.NoteRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNoteViewModel extends ViewModel {
    private NoteRepository nRepo;

    public MutableLiveData<String> toastObserverAddNote;
    public MutableLiveData<Boolean> onSuccessObserverAddNote;

    @Inject
    public AddNoteViewModel(NoteRepository nRepo) {
        this.nRepo = nRepo;

        toastObserverAddNote = nRepo.getToastObserverAddNote();
        onSuccessObserverAddNote = nRepo.getOnSuccessObserverAddNote();
    }

    public void addNote(String note_title, String note_content, int[] colors) {
        nRepo.addNote(note_title, note_content, colors);
    }
}
