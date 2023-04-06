package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {
    Button plusButton;
    Button minusButton;
    EditText topText;
    EditText bottomText;
    TextView result;
    Button navigate_to_secondary;
    private IntentFilter intentFilter = new IntentFilter();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        plusButton = findViewById(R.id.plus_button);
        minusButton = findViewById(R.id.minus_button);
        topText = findViewById(R.id.top_edit_text);
        bottomText = findViewById(R.id.bottom_edit_text);
        result = findViewById(R.id.result_text);
        navigate_to_secondary = findViewById(R.id.navigate_to_secondary_activity);

        plusButton.setOnClickListener(it -> {
            startPracticalService();
            try {
                int top = Integer.parseInt(topText.getText().toString());
                int bottom = Integer.parseInt(bottomText.getText().toString());
                int resultt = top + bottom;
                result.setText(top + " + " + bottom + " = " + resultt);

            } catch (NumberFormatException f) {
                Toast.makeText(this, "not a number", Toast.LENGTH_LONG).show();
            }
        });

        minusButton.setOnClickListener(it -> {
            startPracticalService();
            try {
                int top = Integer.parseInt(topText.getText().toString());
                int bottom = Integer.parseInt(bottomText.getText().toString());
                int resultt = top - bottom;
                result.setText(top + " - " + bottom + " = " + resultt);

            } catch (NumberFormatException f) {
                Toast.makeText(this, "not a number", Toast.LENGTH_LONG).show();
            }
        });

        navigate_to_secondary.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
            intent.putExtra(Constants.RESULT, result.getText().toString());
            startActivityForResult(intent, Constants.REQUEST_CODE);
        });

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.TOP_TEXT, topText.getText().toString());
        outState.putString(Constants.BOTTOM_TEXT, bottomText.getText().toString());
        outState.putString(Constants.RESULT, result.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        topText.setText(savedInstanceState.getString(Constants.TOP_TEXT));
        bottomText.setText(savedInstanceState.getString(Constants.BOTTOM_TEXT));
        result.setText(savedInstanceState.getString(Constants.RESULT));

        Toast.makeText(this, "top: " + topText.getText().toString() + " bottom: " + bottomText.getText().toString() + " result: " + result.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
        getApplicationContext().stopService(intent);
    }

    private void startPracticalService() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
        intent.putExtra(Constants.TOP_TEXT, Integer.parseInt(topText.getText().toString()));
        intent.putExtra(Constants.BOTTOM_TEXT, Integer.parseInt(bottomText.getText().toString()));
        getApplicationContext().startService(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
            if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
        }
    }
}