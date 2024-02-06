package com.example.project1group8.ui.home;

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
import com.example.project1group8.databinding.FragmentSlideshowBinding;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private EditText courseNumberInput;
    private EditText timeInput;
    private EditText courseNameInput;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;


    // Private variables above

    // ------------------------------------------------------ //

    // Initial setup below; DO NOT EDIT BELOW!!!

    // A list to store the course information at runtime
    private final List<Course> courses = new ArrayList<Course>();
    private final List<Assignments> assignments = new ArrayList<Assignments>();
    private final List<Exams> exams = new ArrayList<Exams>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // ... (existing code)
        HomeViewModel galleryViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        // Initialize buttons
        addButton = binding.addButton; // The ID should match the one in your XML layout
        editButton = binding.editButton; // The ID should match the one in your XML layout
        deleteButton = binding.deleteButton; // The ID should match the one in your XML layout

        // Set onClickListener for Add button
        addButton.setOnClickListener(v -> {
            viewOptionsAdd();
        });

        editButton.setOnClickListener(v -> {
            viewOptionsEdit();
        });

        deleteButton.setOnClickListener(v -> {
            viewOptionsDelete();
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

    // Initial setup above; DO NOT EDIT ABOVE!!!

    // ------------------------------------------------------ //

    // Calendar functionality below

    private List<String> getClassesForDay(int year, int month, int dayOfMonth) {
        List<String> activitiesForDay = new ArrayList<>();

        // Create a Calendar object for the selected date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        // Convert calendar day of week to string format
        String dayOfWeekSelected = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());

        // Filter courses based on this day of the week
        for (Course course : courses) {
            if (course.getDayOfWeek().equalsIgnoreCase(dayOfWeekSelected)) {
                activitiesForDay.add("Course: " + course.getName() + " at " + course.getTime());
            }
        }

        // Filter assignments based on this day of the week
        for (Assignments assignment : assignments) {
            if (assignment.getDayOfWeekAss().equalsIgnoreCase(dayOfWeekSelected)) {
                activitiesForDay.add("Assignment: " + assignment.getNameAss() + " for " + assignment.getCourseNameAss() + " at " + assignment.getTimeAss());
            }
        }

        for (Exams exam : exams) {
            if (exam.getDayOfWeekExam().equalsIgnoreCase(dayOfWeekSelected)) {
                activitiesForDay.add("Exam: " + exam.getNameExam() + " for " + exam.getClassExam() + " at " + exam.getTimeExam());
            }
        }

        return activitiesForDay;
    }


    // Calendar functionality above

    // ------------------------------------------------------ //

    // View options below

    private void viewOptionsAdd() {

        // Prepare an array of course names for the selection dialog
        String[] choiceNames = {"Courses", "Assignments", "Exams", "To-Do Lists"};

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an option")
                .setItems(choiceNames, (dialog, which) -> {
                    switch (which) {
                        case 0: // "Courses" was selected
                            addCourse();
                            break;
                        case 1: // "Assignments" was selected
                            addAssignment();
                            break;
                        case 2: // "Exams" was selected
                            addExam();
                            break;
                        case 3: // "To-Do Lists" was selected
                            addTodo();
                            break;
                        default:
                            // Handle unexpected value (if necessary)
                            break;
                    }
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }

    private void viewOptionsEdit() {

        // Prepare an array of course names for the selection dialog
        String[] choiceNames = {"Courses", "Assignments", "Exams", "To-Do Lists"};

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an option")
                .setItems(choiceNames, (dialog, which) -> {
                    switch (which) {
                        case 0: // "Courses" was selected
                            editCourse();
                            break;
                        case 1: // "Assignments" was selected
                            editAssignment();
                            break;
                        case 2: // "Exams" was selected
                            editExam();
                            break;
                        case 3: // "To-Do Lists" was selected
                            editTodo();
                            break;
                        default:
                            // Handle unexpected value (if necessary)
                            break;
                    }
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }

    private void viewOptionsDelete() {

        // Prepare an array of course names for the selection dialog
        String[] choiceNames = {"Courses", "Assignments", "Exams", "To-Do Lists"};

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an option")
                .setItems(choiceNames, (dialog, which) -> {
                    switch (which) {
                        case 0: // "Courses" was selected
                            deleteCourse();
                            break;
                        case 1: // "Assignments" was selected
                            deleteAssignment();
                            break;
                        case 2: // "Exams" was selected
                            deleteExam();
                            break;
                        case 3: // "To-Do Lists" was selected
                            deleteTodo();
                            break;
                        default:
                            // Handle unexpected value (if necessary)
                            break;
                    }
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }




    // View options above

    // ------------------------------------------------------ //

    // Buttons below


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


    // Course buttons above

    // ------------------------------------------------------ //

    // Assignment buttons below

    private void addAssignment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usoneass, null);

        final EditText editTextAssignmentName = dialogView.findViewById(R.id.editTextAssignmentNameAss);
        final Spinner spinnerDayOfWeek = dialogView.findViewById(R.id.spinnerDayOfWeekAss);
        final EditText editTextTime = dialogView.findViewById(R.id.editTextTimeAss);
        final EditText editTextCourseName = dialogView.findViewById(R.id.editTextCourseNameAss); // Now represents course name

        editTextTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTime.setText(time);
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        builder.setView(dialogView)
                .setTitle("Add Assignment")
                .setPositiveButton("Submit", (dialog, id) -> {
                    String assignmentName = editTextAssignmentName.getText().toString();
                    String courseName = editTextCourseName.getText().toString();
                    String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
                    String time = editTextTime.getText().toString();

                    assignments.add(new Assignments(assignmentName, dayOfWeek, time, courseName));
                    Toast.makeText(getContext(), "Assignment added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void editAssignment() {
        if (assignments.isEmpty()) {
            Toast.makeText(getContext(), "No assignments to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare an array of assignment names for the selection dialog
        final String[] assignmentNames = new String[assignments.size()];
        for (int i = 0; i < assignments.size(); i++) {
            assignmentNames[i] = assignments.get(i).getNameAss();
        }

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an assignment to edit")
                .setItems(assignmentNames, (dialog, which) -> {
                    // 'which' is the index of the selected item
                    showEditDialogAss(assignments.get(which));
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }

    private void showEditDialogAss(final Assignments assignmentToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usoneass, null);

        final EditText editTextAssignmentName = dialogView.findViewById(R.id.editTextAssignmentNameAss);
        final Spinner spinnerDayOfWeek = dialogView.findViewById(R.id.spinnerDayOfWeekAss);
        final EditText editTextTime = dialogView.findViewById(R.id.editTextTimeAss);
        final EditText editTextCourseName = dialogView.findViewById(R.id.editTextCourseNameAss); // Used for course name in assignments

        editTextTime.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTime.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        // Pre-fill dialog with assignment data
        editTextAssignmentName.setText(assignmentToEdit.getNameAss());
        editTextTime.setText(assignmentToEdit.getTimeAss());
        editTextCourseName.setText(assignmentToEdit.getCourseNameAss());

        builder.setView(dialogView)
                .setTitle("Edit Assignment")
                .setPositiveButton("Submit", (dialog, id) -> {
                    // Collecting updated information from the dialog
                    String assignmentName = editTextAssignmentName.getText().toString();
                    String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
                    String time = editTextTime.getText().toString();
                    String courseName = editTextCourseName.getText().toString();

                    // Updating the properties of assignmentToEdit
                    assignmentToEdit.setNameAss(assignmentName);
                    assignmentToEdit.setDayOfWeekAss(dayOfWeek);
                    assignmentToEdit.setTimeAss(time);
                    assignmentToEdit.setCourseNameAss(courseName);

                    Toast.makeText(getContext(), "Assignment updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void deleteAssignment() {
        if (assignments.isEmpty()) {
            Toast.makeText(getContext(), "No assignments to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        final String[] assignmentNames = new String[assignments.size()];
        for (int i = 0; i < assignments.size(); i++) {
            assignmentNames[i] = assignments.get(i).getNameAss();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an assignment to delete")
                .setItems(assignmentNames, (dialog, which) -> {
                    assignments.remove(which);
                    Toast.makeText(getContext(), "Assignment deleted", Toast.LENGTH_SHORT).show();
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }


    // Assignment buttons above

    // ------------------------------------------------------ //

    // Exam buttons below



    private void addExam() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usoneexams, null);

        final EditText editTextNameExam = dialogView.findViewById(R.id.editTextAssignmentNameExams); // Update ID for exam
        final Spinner spinnerDayOfWeekExam = dialogView.findViewById(R.id.spinnerDayOfWeekExams); // Update ID for exam if necessary
        final EditText editTextTimeExam = dialogView.findViewById(R.id.editTextTimeExams); // Update ID for exam if necessary
        final EditText editTextClassExam = dialogView.findViewById(R.id.editTextCourseNameExams); // Update ID for exam if necessary

        editTextTimeExam.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTimeExam.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        builder.setView(dialogView)
                .setTitle("Add Exam")
                .setPositiveButton("Submit", (dialog, id) -> {
                    String nameExam = editTextNameExam.getText().toString();
                    String classExam = editTextClassExam.getText().toString();
                    String dayOfWeekExam = spinnerDayOfWeekExam.getSelectedItem().toString();
                    String timeExam = editTextTimeExam.getText().toString();

                    // Add exam information to the list
                    exams.add(new Exams(nameExam, dayOfWeekExam, timeExam, classExam));
                    Toast.makeText(getContext(), "Exam added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editExam() {
        if (exams.isEmpty()) {
            Toast.makeText(getContext(), "No exams to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare an array of exam names for the selection dialog
        final String[] examNames = new String[exams.size()];
        for (int i = 0; i < exams.size(); i++) {
            examNames[i] = exams.get(i).getNameExam();
        }

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an exam to edit")
                .setItems(examNames, (dialog, which) -> {
                    // 'which' is the index of the selected item
                    showEditDialogExam(exams.get(which));
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }

    private void showEditDialogExam(final Exams examToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usoneexams, null); // Make sure this layout has the appropriate IDs for exams

        final EditText editTextNameExam = dialogView.findViewById(R.id.editTextAssignmentNameExams); // Update ID for exam
        final Spinner spinnerDayOfWeekExam = dialogView.findViewById(R.id.spinnerDayOfWeekExams); // Update ID for exam if necessary
        final EditText editTextTimeExam = dialogView.findViewById(R.id.editTextTimeExams); // Update ID for exam if necessary
        final EditText editTextClassExam = dialogView.findViewById(R.id.editTextCourseNameExams); // Update ID for exam if necessary

        editTextTimeExam.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTimeExam.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        // Pre-fill dialog with exam data
        editTextNameExam.setText(examToEdit.getNameExam());
        editTextTimeExam.setText(examToEdit.getTimeExam());
        editTextClassExam.setText(examToEdit.getClassExam());

        // Assuming your spinnerDayOfWeekExam is properly initialized elsewhere with day names
        // You may need to set the spinner to show the correct day of the week

        builder.setView(dialogView)
                .setTitle("Edit Exam")
                .setPositiveButton("Submit", (dialog, id) -> {
                    // Collecting updated information from the dialog
                    String nameExam = editTextNameExam.getText().toString();
                    String dayOfWeekExam = spinnerDayOfWeekExam.getSelectedItem().toString();
                    String timeExam = editTextTimeExam.getText().toString();
                    String classExam = editTextClassExam.getText().toString();

                    // Updating the properties of examToEdit
                    examToEdit.setNameExam(nameExam);
                    examToEdit.setDayOfWeekExam(dayOfWeekExam);
                    examToEdit.setTimeExam(timeExam);
                    examToEdit.setClassExam(classExam);

                    Toast.makeText(getContext(), "Exam updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteExam() {
        if (exams.isEmpty()) {
            Toast.makeText(getContext(), "No exams to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        final String[] examNames = new String[exams.size()];
        for (int i = 0; i < exams.size(); i++) {
            examNames[i] = exams.get(i).getNameExam();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an exam to delete")
                .setItems(examNames, (dialog, which) -> {
                    exams.remove(which);
                    Toast.makeText(getContext(), "Exam deleted", Toast.LENGTH_SHORT).show();
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }





























    private final List<ToDoItem> toDoItem = new ArrayList<>();

//    private void addToDoList() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.usonetodo, null);
//
//        final EditText editTextListName = dialogView.findViewById(R.id.editTextListNameTodo);
//        final EditText editTextItemName = dialogView.findViewById(R.id.editTextItemNameTodo);
//        final Spinner spinnerDayOfWeek = dialogView.findViewById(R.id.spinnerDayOfWeekTodo);
//        final EditText editTextTime = dialogView.findViewById(R.id.editTextTimeTodo);
//        Button buttonAddItem = dialogView.findViewById(R.id.buttonAddItem); // The button to add new to-do items
//        LinearLayout container = dialogView.findViewById(R.id.linearLayoutToDoItems); // Container for to-do items
//
//        // Set up the button click listener
//        buttonAddItem.setOnClickListener(v -> {
//            // Inflate the to-do item layout and add it to the container
//            View itemLayout = inflater.inflate(R.layout.fragment_gallery, container, false); // Inflate the item layout
//            container.addView(itemLayout); // Add the item view to the container
//        });
//
//
//        // Configure the TimePickerDialog
//        editTextTime.setOnClickListener(v -> {
//            Calendar mcurrentTime = Calendar.getInstance();
//            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//            int minute = mcurrentTime.get(Calendar.MINUTE);
//
//            TimePickerDialog mTimePicker;
//            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
//                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
//                editTextTime.setText(time);
//            }, hour, minute, true); // True for 24-hour time
//            mTimePicker.setTitle("Select Time");
//            mTimePicker.show();
//        });
//
//        builder.setView(dialogView)
//                .setTitle("Add To-Do List")
//                .setPositiveButton("Submit", (dialog, id) -> {
//                    String listName = editTextListName.getText().toString();
//                    String itemName = editTextItemName.getText().toString();
//                    String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
//                    String time = editTextTime.getText().toString();
//
//                    // Check if the list name is empty
//                    if (TextUtils.isEmpty(listName)) {
//                        Toast.makeText(getContext(), "List name cannot be empty", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    // Find if the list already exists
//                    ToDoList existingList = null;
//                    for (ToDoList list : toDoLists) {
//                        if (list.getListNameTodo().equals(listName)) {
//                            existingList = list;
//                            break;
//                        }
//                    }
//
//                    if (existingList == null) {
//                        // If the list does not exist, create a new one
//                        ToDoList newToDoList = new ToDoList(listName);
//                        toDoLists.add(newToDoList);
//                        existingList = newToDoList;
//                    }
//
//                    // Add the new item to the list
//                    if (!TextUtils.isEmpty(itemName)) {
//                        ToDoItem newItem = new ToDoItem(itemName, dayOfWeek, time);
//                        existingList.addItemTodo(newItem);
//                    }
//
//                    Toast.makeText(getContext(), "To-Do List updated", Toast
//                            .LENGTH_SHORT).show();
//                })
//                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }
//
//    private void editToDoList() {
//        // Get the list of to-do list names
//        String[] toDoListNames = new String[toDoLists.size()];
//        for (int i = 0; i < toDoLists.size(); i++) {
//            toDoListNames[i] = toDoLists.get(i).getListNameTodo();
//        }
//
//        // Show a dialog to pick a list to edit
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Choose a To-Do List to edit")
//                .setItems(toDoListNames, (dialog, which) -> {
//                    // Pass the selected ToDoList to the add/edit method
//                    editSelectedToDoList(toDoLists.get(which));
//                });
//
//        AlertDialog selectionDialog = builder.create();
//        selectionDialog.show();
//    }
//
//    private void editSelectedToDoList(ToDoList selectedToDoList) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.usonetodo, null);
//
//        final EditText editTextListName = dialogView.findViewById(R.id.editTextListNameTodo);
//        final EditText editTextItemName = dialogView.findViewById(R.id.editTextItemNameTodo);
//        final Spinner spinnerDayOfWeek = dialogView.findViewById(R.id.spinnerDayOfWeekTodo);
//        final EditText editTextTime = dialogView.findViewById(R.id.editTextTimeTodo);
//        Button buttonAddItem = dialogView.findViewById(R.id.buttonAddItem);
//        LinearLayout container = dialogView.findViewById(R.id.linearLayoutToDoItems);
//
//        // Populate the dialog fields with the selectedToDoList data
//        editTextListName.setText(selectedToDoList.getListNameTodo());
//
//        // Set up the button click listener to add new items to the list
//        buttonAddItem.setOnClickListener(v -> {
//            String itemName = editTextItemName.getText().toString();
//            String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
//            String time = editTextTime.getText().toString();
//
//            if (!TextUtils.isEmpty(itemName)) {
//                ToDoItem newItem = new ToDoItem(itemName, dayOfWeek, time);
//                selectedToDoList.addItemTodo(newItem);
//
//                // Create a view to display the added item
//                View itemLayout = inflater.inflate(R.layout.fragment_gallery, container, false);
//                TextView itemNameTextView = itemLayout.findViewById(R.id.editTextItemNameTodo);
//                TextView dayOfWeekTextView = itemLayout.findViewById(R.id.spinnerDayOfWeekTodo);
//                TextView timeTextView = itemLayout.findViewById(R.id.editTextTimeTodo);
//
//                itemNameTextView.setText(itemName);
//                dayOfWeekTextView.setText(dayOfWeek);
//                timeTextView.setText(time);
//
//                container.addView(itemLayout); // Add the item view to the container
//
//                // Clear input fields
//                editTextItemName.getText().clear();
//                editTextTime.getText().clear();
//            }
//        });
//
//        // Configure the TimePickerDialog
//        editTextTime.setOnClickListener(v -> {
//            Calendar mcurrentTime = Calendar.getInstance();
//            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//            int minute = mcurrentTime.get(Calendar.MINUTE);
//
//            TimePickerDialog mTimePicker;
//            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
//                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
//                editTextTime.setText(time);
//            }, hour, minute, true); // True for 24-hour time
//            mTimePicker.setTitle("Select Time");
//            mTimePicker.show();
//        });
//
//        builder.setView(dialogView)
//                .setTitle("Edit To-Do List")
//                .setPositiveButton("Save", (dialog, id) -> {
//                    String updatedListName = editTextListName.getText().toString();
//
//                    // Check if the updated list name is empty
//                    if (TextUtils.isEmpty(updatedListName)) {
//                        Toast.makeText(getContext(), "List name cannot be empty", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    // Update the list name
//                    selectedToDoList.setListNameTodo(updatedListName);
//
//                    Toast.makeText(getContext(), "To-Do List updated", Toast.LENGTH_SHORT).show();
//                })
//                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//
//    // Overloaded addToDoList for editing existing lists
//    // In your dialog layout XML, define a LinearLayout with the ID `linearLayoutToDoItems`
//
//    // Overloaded addToDoList for editing existing lists
//    // Overloaded addToDoList for editing existing lists
//
//
//
//
//
//
//    private void deleteToDoList() {
//        String[] toDoListNames = new String[toDoLists.size()];
//        for (int i = 0; i < toDoLists.size(); i++) {
//            toDoListNames[i] = toDoLists.get(i).getListNameTodo();
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Choose a To-Do List to delete")
//                .setItems(toDoListNames, (dialog, which) -> {
//                    toDoLists.remove(which);
//                    Toast.makeText(getContext(), "To-Do List deleted", Toast.LENGTH_SHORT).show();
//                });
//
//        AlertDialog selectionDialog = builder.create();
//        selectionDialog.show();
//    }


    private void addTodo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usonetodo, null);

        final EditText editTextNameExam = dialogView.findViewById(R.id.editTextAssignmentNameTodo); // Update ID for exam
        final Spinner spinnerDayOfWeekExam = dialogView.findViewById(R.id.spinnerDayOfWeekTodo); // Update ID for exam if necessary
        final EditText editTextTimeExam = dialogView.findViewById(R.id.editTextTimeTodo); // Update ID for exam if necessary
        final EditText editTextClassExam = dialogView.findViewById(R.id.editTextCourseNameTodo); // Update ID for exam if necessary

        editTextTimeExam.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTimeExam.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        builder.setView(dialogView)
                .setTitle("Add To do list")
                .setPositiveButton("Submit", (dialog, id) -> {
                    String nameExam = editTextNameExam.getText().toString();
                    String classExam = editTextClassExam.getText().toString();
                    String dayOfWeekExam = spinnerDayOfWeekExam.getSelectedItem().toString();
                    String timeExam = editTextTimeExam.getText().toString();

                    // Add exam information to the list
                    toDoItem.add(new ToDoItem(nameExam, dayOfWeekExam, timeExam, classExam));
                    Toast.makeText(getContext(), "To do list added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editTodo() {
        if (toDoItem.isEmpty()) {
            Toast.makeText(getContext(), "No to do lists to edit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare an array of exam names for the selection dialog
        final String[] examNames = new String[toDoItem.size()];
        for (int i = 0; i < toDoItem.size(); i++) {
            examNames[i] = toDoItem.get(i).getNameTodo();
        }

        // Create and show a selection dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose an exam to edit")
                .setItems(examNames, (dialog, which) -> {
                    // 'which' is the index of the selected item
                    showEditDialogTodo(toDoItem.get(which));
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }

    private void showEditDialogTodo(final ToDoItem examToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.usonetodo, null); // Make sure this layout has the appropriate IDs for exams

        final EditText editTextNameExam = dialogView.findViewById(R.id.editTextAssignmentNameTodo); // Update ID for exam
        final Spinner spinnerDayOfWeekExam = dialogView.findViewById(R.id.spinnerDayOfWeekTodo); // Update ID for exam if necessary
        final EditText editTextTimeExam = dialogView.findViewById(R.id.editTextTimeTodo); // Update ID for exam if necessary
        final EditText editTextClassExam = dialogView.findViewById(R.id.editTextCourseNameTodo); // Update ID for exam if necessary

        editTextTimeExam.setOnClickListener(v -> {
            // Initialize a new instance of TimePickerDialog
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                editTextTimeExam.setText(time);
            }, hour, minute, true); // True for 24-hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        // Pre-fill dialog with exam data
        editTextNameExam.setText(examToEdit.getNameTodo());
        editTextTimeExam.setText(examToEdit.getTimeTodo());
        editTextClassExam.setText(examToEdit.getThingTodo());

        // Assuming your spinnerDayOfWeekExam is properly initialized elsewhere with day names
        // You may need to set the spinner to show the correct day of the week

        builder.setView(dialogView)
                .setTitle("Edit To Do list")
                .setPositiveButton("Submit", (dialog, id) -> {
                    // Collecting updated information from the dialog
                    String nameExam = editTextNameExam.getText().toString();
                    String dayOfWeekExam = spinnerDayOfWeekExam.getSelectedItem().toString();
                    String timeExam = editTextTimeExam.getText().toString();
                    String classExam = editTextClassExam.getText().toString();

                    // Updating the properties of examToEdit
                    examToEdit.setNameTodo(nameExam);
                    examToEdit.setDayOfWeekTodo(dayOfWeekExam);
                    examToEdit.setTimeTodo(timeExam);
                    examToEdit.setThingTodo(classExam);

                    Toast.makeText(getContext(), "To do list updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteTodo() {
        if (toDoItem.isEmpty()) {
            Toast.makeText(getContext(), "No exams to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        final String[] examNames = new String[toDoItem.size()];
        for (int i = 0; i < toDoItem.size(); i++) {
            examNames[i] = toDoItem.get(i).getNameTodo();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose a to do list to delete")
                .setItems(examNames, (dialog, which) -> {
                    toDoItem.remove(which);
                    Toast.makeText(getContext(), "Exam deleted", Toast.LENGTH_SHORT).show();
                });

        AlertDialog selectionDialog = builder.create();
        selectionDialog.show();
    }






    // All Buttons above

    // ------------------------------------------------------ //

    // Classes and such below


    @Override
    public void onDestroyView () {
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
        public void setName(String name) {
            this.name = name;
        }

        // Updated to accommodate dayOfWeek and time
        public void setDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setInstructor(String instructor) {
            this.instructor = instructor;
        }

        public String getName() {
            return name;
        }

        // Getter for dayOfWeek
        public String getDayOfWeek() {
            return dayOfWeek;
        }

        // Getter for time
        public String getTime() {
            return time;
        }

        public String getInstructor() {
            return instructor;
        }
    }


    private class Assignments {
        private String nameAss;
        private String dayOfWeekAss; // To store the day of the week
        private String timeAss; // To store the time
        private String courseNameAss;

        // Updated constructor to include dayOfWeek and time
        public Assignments(String nameAss, String dayOfWeekAss, String timeAss, String courseNameAss) {
            this.nameAss = nameAss;
            this.dayOfWeekAss = dayOfWeekAss;
            this.timeAss = timeAss;
            this.courseNameAss = courseNameAss;
        }

        // Getters and setters
        public void setNameAss(String nameAss) {
            this.nameAss = nameAss;
        }

        // Updated to accommodate dayOfWeek and time
        public void setDayOfWeekAss(String dayOfWeekAss) {
            this.dayOfWeekAss = dayOfWeekAss;
        }

        public void setTimeAss(String timeAss) {
            this.timeAss = timeAss;
        }

        public void setCourseNameAss(String courseNameAss) {
            this.courseNameAss = courseNameAss;
        }

        public String getNameAss() {
            return nameAss;
        }

        // Getter for dayOfWeek
        public String getDayOfWeekAss() {
            return dayOfWeekAss;
        }

        // Getter for time
        public String getTimeAss() {
            return timeAss;
        }

        public String getCourseNameAss() {
            return courseNameAss;
        }
    }


    private class Exams {
        private String nameExam;
        private String dayOfWeekExam; // To store the day of the week
        private String timeExam; // To store the time
        private String classExam;

        // Updated constructor to include dayOfWeek and time
        public Exams(String nameExam, String dayOfWeekExam, String timeExam, String classExam) {
            this.nameExam = nameExam;
            this.dayOfWeekExam = dayOfWeekExam;
            this.timeExam = timeExam;
            this.classExam = classExam;
        }

        // Getters and setters
        public void setNameExam(String nameExam) { this.nameExam = nameExam; }
        // Updated to accommodate dayOfWeek and time
        public void setDayOfWeekExam(String dayOfWeekExam) { this.dayOfWeekExam = dayOfWeekExam; }
        public void setTimeExam(String timeExam) { this.timeExam = timeExam; }
        public void setClassExam(String classExam) { this.classExam = classExam; }
        public String getNameExam() { return nameExam; }
        // Getter for dayOfWeek
        public String getDayOfWeekExam() { return dayOfWeekExam; }
        // Getter for time
        public String getTimeExam() { return timeExam; }
        public String getClassExam() { return classExam; }
    }

    private class ToDoItem {
        private String nameTodo;
        private String dayOfWeekTodo;
        private String timeTodo;

        private String thingTodo;

        public ToDoItem(String nameTodo, String dayOfWeekTodo, String timeTodo, String thingTodo) {
            this.nameTodo = nameTodo;
            this.dayOfWeekTodo = dayOfWeekTodo;
            this.timeTodo = timeTodo;
            this.thingTodo = thingTodo;
        }

        // Getters
        public String getNameTodo() {
            return nameTodo;
        }

        public String getDayOfWeekTodo() {
            return dayOfWeekTodo;
        }

        public String getTimeTodo() {
            return timeTodo;
        }

        public String getThingTodo() {
            return thingTodo;
        }

        // Setters
        public void setNameTodo(String nameTodo) {
            this.nameTodo = nameTodo;
        }

        public void setDayOfWeekTodo(String dayOfWeekTodo) {
            this.dayOfWeekTodo = dayOfWeekTodo;
        }

        public void setTimeTodo(String timeTodo) {
            this.timeTodo = timeTodo;
        }

        public void setThingTodo(String thingTodo) {
            this.thingTodo = thingTodo;
        }
    }


//    private class ToDoList {
//        private String listNameTodo;
//        private List<ToDoItem> itemsTodo;
//
//        public ToDoList(String listNameTodo) {
//            this.listNameTodo = listNameTodo;
//            this.itemsTodo = new ArrayList<>();
//        }
//
//        // Method to add a ToDoItem
//        public void addItemTodo(ToDoItem item) {
//            itemsTodo.add(item);
//        }
//
//        // Method to remove a ToDoItem
//        public void removeItemTodo(ToDoItem item) {
//            itemsTodo.remove(item);
//        }
//
//        // Getters
//        public String getListNameTodo() {
//            return listNameTodo;
//        }
//
//        public List<ToDoItem> getItemsTodo() {
//            return new ArrayList<>(itemsTodo); // Returning a copy to protect the original list
//        }
//
//        // Setters
//        public void setListNameTodo(String listNameTodo) {
//            this.listNameTodo = listNameTodo;
//        }
//
//        // Additional methods
//        // Method to get a ToDoItem by name
//        public ToDoItem getItemByNameTodo(String nameTodo) {
//            for (ToDoItem item : itemsTodo) {
//                if (item.getNameTodo().equals(nameTodo)) {
//                    return item;
//                }
//            }
//            return null; // or throw an exception if preferred
//        }
//
//        // Method to remove a ToDoItem by name
//        public void removeItemByNameTodo(String nameTodo) {
//            ToDoItem itemToRemove = getItemByNameTodo(nameTodo);
//            if (itemToRemove != null) {
//                itemsTodo.remove(itemToRemove);
//            }
//        }
//    }





}
