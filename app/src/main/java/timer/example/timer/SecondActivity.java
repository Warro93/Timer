package timer.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timer.R;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = SecondActivity.class.getName();
    private static final String KEY_VALUE = TAG + ".extras.value";
    TextView timerText2;
    Button backBtn,thirdActivity;
    EditText pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        timerText2 = findViewById(R.id.timerText2);
        timerText2.setText(getIntent().getStringExtra(KEY_VALUE));
        backBtn = findViewById(R.id.home);
        pin = findViewById(R.id.editTextNumberPassword);

        backBtn.setOnClickListener(view -> backToHome());

        thirdActivity = findViewById(R.id.thirdActivity);
        thirdActivity.setOnClickListener(view -> startThirdActivity());

    }

    private void startThirdActivity() {

        Intent thirdIntent = new Intent(this, ThirdActivity.class);
        if(pin.getText().toString().equals("1234"))
            startActivity(thirdIntent);
        else {
            Toast.makeText(getApplicationContext(), "PIN ERRATO!", Toast.LENGTH_SHORT).show();
            pin.setText("");
        }
    }

    private void backToHome() {

        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    public static Intent getIntentInstance(Context context, String value){
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(KEY_VALUE, value);
        return intent;
    }
}