package com.example.project1group8.ui.US1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1group8.R;
import com.example.project1group8.databinding.FragmentSlideshowBinding;

public class USOneFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private EditText courseNumberInput;
    private EditText timeInput;
    private EditText courseNameInput;
    private Button submitButton;

        // ... (other fields)

        private Button addButton;
        private Button editButton;
        private Button deleteButton;

        // ... (onCreateView method)

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // ... (existing code)

            // Initialize buttons
            addButton = binding.addButton; // Make sure you have this in your layout
            editButton = binding.editButton; // Make sure you have this in your layout
            deleteButton = binding.deleteButton; // Make sure you have this in your layout

            // Set onClickListener for Add button
            addButton.setOnClickListener(v -> {
                addCourse();
            });

            // Set onClickListener for Edit button
            editButton.setOnClickListener(v -> {
                editCourse();
            });

            // Set onClickListener for Delete button
            deleteButton.setOnClickListener(v -> {
                deleteCourse();
            });

            return root;
        }

        private void addCourse() {
            // Code to add a new course to the database
            // You might want to collect data from the EditText fields
        }

        private void editCourse() {
            // Code to edit an existing course in the database
            // You will need to have a way to select which course to edit
        }

        private void deleteCourse() {
            // Code to delete a course from the database
            // Similarly, you'll need to know which course to delete
        }

        // ... (other overridden methods)

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }

}
