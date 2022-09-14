package com.serhat.notesapp.ui.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.serhat.notesapp.R;
import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.databinding.FragmentNoteDetailsBinding;
import com.serhat.notesapp.ui.viewmodel.NoteDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NoteDetailsFragment extends Fragment {
    private FragmentNoteDetailsBinding binding;
    private NoteDetailsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false);
        binding.setNoteDetailsFragment(this);

        viewModel.onSuccessObserverDeleteNote.observe(getViewLifecycleOwner(), value -> navToMain());
        viewModel.toastObserverNoteDetails.observe(getViewLifecycleOwner(), value -> Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show());

        viewModel.note.observe(getViewLifecycleOwner(), note -> binding.setNote(note));

        return binding.getRoot();
    }

    public void navToMain() {
        requireActivity().onBackPressed();
    }

    public void navToEditNote(View view, Note note) {
        NoteDetailsFragmentDirections.NoteDetailsToEditNote noteDetailsToEditNote = NoteDetailsFragmentDirections.noteDetailsToEditNote(note);
        Navigation.findNavController(view).navigate(noteDetailsToEditNote);
    }

    public void deleteNote(View view, int note_id) {
        Snackbar.make(view, getString(R.string.msg_confirm_delete), Snackbar.LENGTH_LONG).setAction("Evet", v -> viewModel.deleteNote(note_id)).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NoteDetailsViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        NoteDetailsFragmentArgs bundle = NoteDetailsFragmentArgs.fromBundle(getArguments());
        int note_id = bundle.getNoteId();

        viewModel.getNoteDetails(note_id);
    }
}