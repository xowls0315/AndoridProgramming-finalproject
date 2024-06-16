package com.cookandroid.final_project;

import android.app.DatePickerDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private int[] songs = {R.raw.song1, R.raw.song2, R.raw.song3, R.raw.song4};
    private int currentSongIndex = 0;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startBtn);
        Button endButton = findViewById(R.id.endBtn);
        Button playButton = findViewById(R.id.playBtn);
        Button stopButton = findViewById(R.id.stopBtn);
        Button nextButton = findViewById(R.id.nextBtn);

        final String[] startDate = {""};
        final String[] endDate = {""};

        final Calendar calendar_start = Calendar.getInstance();
        final Calendar calendar_end = Calendar.getInstance();

        startButton.setOnClickListener(view -> {

            GregorianCalendar today = new GregorianCalendar();
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DATE);

            DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    startDate[0] = year + Integer.toString(month + 1) + dayOfMonth;

                    calendar_start.set(year, month, dayOfMonth);

                    Log.d("day : ", startDate[0]);
                }
            }, year, month, day);
            dlg.show();
        });

        endButton.setOnClickListener(view -> {
            GregorianCalendar today = new GregorianCalendar();
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DATE);

            DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    endDate[0] = year + Integer.toString(month + 1) + dayOfMonth;

                    calendar_end.set(year, month, dayOfMonth);

                    long finalDate = TimeUnit.MILLISECONDS.toDays(calendar_end.getTimeInMillis() - calendar_start.getTimeInMillis());

                    Log.d("day : ", endDate[0]);

                    TextView textArea = findViewById(R.id.finalDate);

                    textArea.setText(Long.toString(finalDate + 1));
                }
            }, year, month, day);
            dlg.show();
        });

        playButton.setOnClickListener(view -> {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.song1);
                mediaPlayer.seekTo(currentPosition);
            }
            mediaPlayer.start();
        });

        stopButton.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            }
        });

        nextButton.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            Random random = new Random();
            currentSongIndex = random.nextInt(songs.length);
            mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
            mediaPlayer.start();
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
