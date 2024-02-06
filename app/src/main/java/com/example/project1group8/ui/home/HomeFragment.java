package com.example.project1group8.ui.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.project1group8.R;


public class HomeFragment extends Fragment {

    private EditText courseNumberInput;
    private EditText timeInput;
    private Button signUpButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        courseNumberInput = view.findViewById(R.id.course_number_input);
        timeInput = view.findViewById(R.id.time_input);
        signUpButton = view.findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseNumber = courseNumberInput.getText().toString();
                String time = timeInput.getText().toString();

                // Here, you can store the courseNumber and time data as needed
                // For example, you can save it to SharedPreferences or a database

                // Show a toast message to confirm the data capture
                Toast.makeText(requireContext(), "Course Number: " + courseNumber + ", Time: " + time, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
