package org.ttaluri.csc472assignemnt1_ttaluri;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private EditText inputField;
    private TextView resultView, historyView;
    private RadioGroup radioGroup;
    private Button calculateButton, clearButton;

    private StringBuilder historyBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputField = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.resultView);
        historyView = findViewById(R.id.historyView);
        radioGroup = findViewById(R.id.radioGroup);
        calculateButton = findViewById(R.id.calculateButton);
        clearButton = findViewById(R.id.clearButton);
        historyBuilder = new StringBuilder();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDateTime();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });
    }

    private void calculateDateTime() {
        String input = inputField.getText().toString();
        if (input.isEmpty()) {
            resultView.setText("Please enter a value");
            return;
        }

        int valueToAdd = Integer.parseInt(input);
        Calendar calendar = Calendar.getInstance();
        int selectedUnit = getSelectedUnit();

        calendar.add(selectedUnit, valueToAdd);
        Date newDate = calendar.getTime();
        String result = newDate.toString();

        resultView.setText(result);

        // Update history
        String historyEntry = (valueToAdd >= 0 ? "+" : "") + valueToAdd + " " + getSelectedUnitText() + ": " + result;
        historyBuilder.insert(0, historyEntry + "\n");
        historyView.setText(historyBuilder.toString());
    }

    private int getSelectedUnit() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioHours) {
            return Calendar.HOUR;
        } else if (selectedId == R.id.radioDays) {
            return Calendar.DAY_OF_YEAR;
        } else if (selectedId == R.id.radioWeeks) {
            return Calendar.WEEK_OF_YEAR;
        } else {
            return Calendar.MONTH;
        }
    }

    private String getSelectedUnitText() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioHours) {
            return "Hours";
        } else if (selectedId == R.id.radioDays) {
            return "Days";
        } else if (selectedId == R.id.radioWeeks) {
            return "Weeks";
        } else {
            return "Months";
        }
    }

    private void clearAll() {
        inputField.setText("");
        resultView.setText("");
        historyView.setText("");
        historyBuilder.setLength(0);  // clear the history
        radioGroup.check(R.id.radioHours);  // reset to Hours
    }
}
