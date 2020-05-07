package com.example.sum;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText number1;
    EditText number2;
    Button add_button;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.editText_first_no);
        number2 = findViewById(R.id.editText_second_no);
        add_button = findViewById(R.id.add_button);
        result = findViewById(R.id.textView_answer);

        add_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                double num1 = Double.parseDouble(number1.getText().toString());
                double num2 = Double.parseDouble(number2.getText().toString());

                double sum = num1 + num2;

                result.setText(Double.toString(sum));
            }
        });
    }
}
