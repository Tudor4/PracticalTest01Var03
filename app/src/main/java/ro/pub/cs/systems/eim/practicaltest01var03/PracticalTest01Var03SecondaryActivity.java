package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {
    Button correct;
    Button incorrect;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        result = findViewById(R.id.result_secondary);

        correct.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        incorrect.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            String resultt = extras.getString(Constants.RESULT);
            result.setText(resultt);
        } else {
            result.setText(savedInstanceState.getString("resultText"));
        }
    }
}