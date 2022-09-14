package com.serhat.notesapp.ui.view.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serhat.notesapp.R;
import com.serhat.notesapp.databinding.FragmentAddNoteBinding;
import com.serhat.notesapp.ui.viewmodel.AddNoteViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNoteFragment extends Fragment {
    private FragmentAddNoteBinding binding;
    private AddNoteViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false);
        binding.setAddNoteFragment(this);

        viewModel.onSuccessObserverAddNote.observe(getViewLifecycleOwner(), value -> navToMain());
        viewModel.toastObserverAddNote.observe(getViewLifecycleOwner(), value -> Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show());

        return binding.getRoot();
    }

    public void navToMain() {
        requireActivity().onBackPressed();
    }

    public void addNote(String note_title, String note_content) {
        int[] colors = requireContext().getResources().getIntArray(R.array.colors);
        viewModel.addNote(note_title, note_content, colors);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        super.onCreate(savedInstanceState);
    }
}