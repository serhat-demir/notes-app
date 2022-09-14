package com.serhat.notesapp.ui.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serhat.notesapp.R;
import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.databinding.FragmentEditNoteBinding;
import com.serhat.notesapp.ui.viewmodel.EditNoteViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditNoteFragment extends Fragment {
    private FragmentEditNoteBinding binding;
    private EditNoteViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_note, container, false);
        binding.setEditNoteFragment(this);

        viewModel.onSuccessObserverEditNote.observe(getViewLifecycleOwner(), value -> navToNoteDetails());
        viewModel.toastObserverEditNote.observe(getViewLifecycleOwner(), value -> Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show());

        EditNoteFragmentArgs bundle = EditNoteFragmentArgs.fromBundle(getArguments());
        Note note = bundle.getNote();
        binding.setNote(note);

        return binding.getRoot();
    }

    public void navToNoteDetails() {
        requireActivity().onBackPressed();
    }

    public void editNote(int note_id, String note_title, String note_content, String created_at, int color) {
        viewModel.editNote(note_id, note_title, note_content, created_at, color);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditNoteViewModel.class);
    }
}