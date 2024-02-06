package com.example.project1group8.ui.US1;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1group8.R;
import com.example.project1group8.databinding.FragmentGalleryBinding;
import com.example.project1group8.databinding.FragmentSlideshowBinding;
import com.example.project1group8.ui.US2.USTwoViewModel;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;


public class USOneFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private EditText courseNumberInput;
    private EditText timeInput;
    private EditText courseNameInput;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    // A list to store the course information at runtime
    private final List<Course> courses = new ArrayList<Course>();

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // ... (existing code)
            USTwoViewModel galleryViewModel =
                    new ViewModelProvider(this).get(USTwoViewModel.class);

            binding = FragmentSlideshowBinding.inflate(inflater, container, false);
            View root = binding.getRoot();




            // Initialize buttons
            addButton = binding.addButton; // The ID should match the one in your XML layout
            editButton = binding.editButton; // The ID should match the one in your XML layout
            deleteButton = binding.deleteButton; // The ID should match the one in your XML layout

            // Set onClickListener for Add button
            addButton.setOnClickListener(v -> {
                addCourse();
            });

            editButton.setOnClickListener(v -> {
               editCourse();
            });

            deleteButton.setOnClickListener(v -> {
               deleteCourse();
            });

            CalendarView calendarView = binding.calendarView;
            TextView classesTextView = binding.classesTextView; // Assuming you've added this to your binding class

            calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                // Format the date selected
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                String selectedDate = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(calendar.getTime());

                // Filter classes for this date
                List<String> classesForDay = getClassesForDay(year, month, dayOfMonth);

                // Update TextView or show a dialog
                classesTextView.setText(TextUtils.join("\n", classesForDay));
            });

            return root;
        }

    private List<String> getClassesForDay(int year, int month, int dayOfMonth) {
        List<String> classesForDay = new ArrayList<>();

        // Create a Calendar object for the selected date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        // Convert calendar day of week to string format
        String dayOfWeekSelected = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());

        // Filter courses based on this day of the week
        for (Course course : courses) {
            if (course.getDayOfWeek().equalsIgnoreCase(dayOfWeekSelected)) {
                classesForDay.add(course.getName() + " at " + course.getTime());
            }
        }

        return classesForDay;
    }


    private void addCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usoneadd, null);

        final EditText editTextCourseName = dialogView.findViewById(R.id.editTextCourseName);
        final Spinner spinnerDayOfWeek = dialogView.findViewById(R.id.spinnerDayOfWeek);
        final EditText editTextTime = dialogView.findViewById(R.id.editTextTime);
        final EditText editTextInstructor = dialogView.findViewById(R.id.editTextInstructor);

        editTextTime.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTime.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        builder.setView(dialogView)
                .setTitle("Add Course")
                .setPositiveButton("Submit", (dialog, id) -> {
                    String courseName = editTextCourseName.getText().toString();
                    String instructor = editTextInstructor.getText().toString();

                    // Assuming spinnerDayOfWeek and editTextTime are initialized similarly in addCourse method
                    String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
                    String time = editTextTime.getText().toString();
                    // Combine day and time into a single string or adjust based on your preference
                    String courseDateTime = dayOfWeek + " at " + time;

                    // Add course information to the list with the combined date and time
                    courses.add(new Course(courseName, dayOfWeek, time, instructor));
                    Toast.makeText(getContext(), "Course added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void editCourse() {
        if (courses.isEmpty()) {
            Toast.makeText(getContext(), "No courses to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare an array of course names for the selection dialog
        final String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseNames[i] = courses.get(i).getName();
        }

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose a course to edit")
                .setItems(courseNames, (dialog, which) -> {
                    // 'which' is the index of the selected item
                    showEditDialog(courses.get(which));
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }

    private void showEditDialog(final Course courseToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usoneadd, null);

        final EditText editTextCourseName = dialogView.findViewById(R.id.editTextCourseName);
        final Spinner spinnerDayOfWeek = dialogView.findViewById(R.id.spinnerDayOfWeek);
        final EditText editTextTime = dialogView.findViewById(R.id.editTextTime);
        final EditText editTextInstructor = dialogView.findViewById(R.id.editTextInstructor);

        editTextTime.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTime.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        // Pre-fill dialog with course data
        editTextCourseName.setText(courseToEdit.getName());
        editTextInstructor.setText(courseToEdit.getInstructor());
        // Assuming the Course class has a getTime() method returning a String in "HH:mm" format
        editTextTime.setText(courseToEdit.getTime());


        builder.setView(dialogView)
                .setTitle("Edit Course")
                .setPositiveButton("Submit", (dialog, id) -> {
                    // Assuming courseToEdit references the correct Course object you want to update
                    String courseName = editTextCourseName.getText().toString();
                    String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
                    String time = editTextTime.getText().toString();
                    String instructor = editTextInstructor.getText().toString();

                    // Update the properties of courseToEdit
                    courseToEdit.setName(courseName);
                    courseToEdit.setDayOfWeek(dayOfWeek); // Ensure your Course class has this setter
                    courseToEdit.setTime(time); // Ensure your Course class has this setter
                    courseToEdit.setInstructor(instructor);

                    Toast.makeText(getContext(), "Course updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void deleteCourse() {
        if (courses.isEmpty()) {
            Toast.makeText(getContext(), "No courses to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare an array of course names for the selection dialog
        final String[] courseNames = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseNames[i] = courses.get(i).getName();
        }

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose a course to delete")
                .setItems(courseNames, (dialog, which) -> {
                    // 'which' is the index of the selected item
                    courses.remove(which);
                    Toast.makeText(getContext(), "Course deleted", Toast.LENGTH_SHORT).show();
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }



    @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

    private class Course {
        private String name;
        private String dayOfWeek; // To store the day of the week
        private String time; // To store the time
        private String instructor;

        // Updated constructor to include dayOfWeek and time
        public Course(String name, String dayOfWeek, String time, String instructor) {
            this.name = name;
            this.dayOfWeek = dayOfWeek;
            this.time = time;
            this.instructor = instructor;
        }

        // Getters and setters
        public void setName(String name) { this.name = name; }
        // Updated to accommodate dayOfWeek and time
        public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
        public void setTime(String time) { this.time = time; }
        public void setInstructor(String instructor) { this.instructor = instructor; }
        public String getName() { return name; }
        // Getter for dayOfWeek
        public String getDayOfWeek() { return dayOfWeek; }
        // Getter for time
        public String getTime() { return time; }
        public String getInstructor() { return instructor; }
    }



}
