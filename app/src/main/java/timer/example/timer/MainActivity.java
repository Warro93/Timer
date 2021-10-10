package timer.example.timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.timer.R;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button startButton,stopButton, startSecondActivity;
    private TextView timerText;
    private CountDownTimer countDownTimer;
    private int count=0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        startSecondActivity = findViewById(R.id.startSecondActivity);

        timerText = findViewById(R.id.timerText);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimerText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        startButton.setOnClickListener(view -> {
            seekBar.setEnabled(false);
            if(count==0) {
                countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000L + 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        updateTimerText((int) l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sveglia);
                        mediaPlayer.start();
                        resetTimer();
                    }
                };
                count++;
            }
            countDownTimer.start();
        });

        stopButton.setOnClickListener(view -> {
            if(count>0)
            resetTimer();
        });

        //Second Activity
        startSecondActivity.setOnClickListener(view -> {
            Intent intent = SecondActivity.getIntentInstance(MainActivity.this,
                                                            timerText.getText().toString());
            if(count>0)
                countDownTimer.cancel();


            startActivity(intent);
        });
    }


    @SuppressLint("SetTextI18n")
    private void resetTimer() {
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        timerText.setText("00:30");
        countDownTimer.cancel();
        count=0;
    }

    private void updateTimerText(int secondLeft) {
        int minute = secondLeft / 60;
        int seconds = secondLeft - minute*60;

        String minuteString = String.valueOf(minute);
        String secondString = String.valueOf(seconds);

        if(seconds < 10)
            secondString = "0" + secondString;

        String finalString = minuteString + ":" + secondString;
        timerText.setText(finalString);
    }


}