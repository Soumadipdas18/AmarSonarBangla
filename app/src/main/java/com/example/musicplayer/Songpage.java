package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Songpage extends AppCompatActivity {

    Button playBtn;

    SeekBar positionBar, volumeBar;
    TextView elapsedTimeLabel, remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;
    boolean looping;
    private Handler mainHandler = new Handler();
    MainActivity ma=new MainActivity();
    //On completion
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.



            playBtn.setBackgroundResource(R.drawable.play);


        }
    };




    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        mp.stop();
        mp.release();
        mp=null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songpage);
        //I need to change the raw file and the background
        // Media Player
        releaseMediaPlayer();
        creation();

        playBtn = findViewById(R.id.playBtn);
        elapsedTimeLabel = findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel);


        //Set looping
        Button loopbtn=(Button)findViewById(R.id.Loopbtn);
                loopbtn.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            if(looping){
                                mp.setLooping(false);
                                looping=false;
                                Toast toast = Toast. makeText(getApplicationContext(), "Looping set to false", Toast. LENGTH_SHORT);
                                toast.show();
                            }
                            else{
                                mp.setLooping(true);
                                looping=true;
                                Toast toast = Toast. makeText(getApplicationContext(), "Looping set to true", Toast. LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                //Set stop button
        Button stopbtn=(Button)findViewById(R.id.stopbtn);
        stopbtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mp.stop();
                        if(mp.isPlaying())
                            mp.stop();
                        mp  = MediaPlayer.create(Songpage.this,ma.mword.getIndex() );
                        positionBar.setProgress(0);
                        playBtn.setBackgroundResource(R.drawable.play);

                    }
                });




        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);

        totalTime = mp.getDuration();

        // Position Bar
        positionBar = findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);//seeking progress of a song
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );


        // Volume Bar
        volumeBar = findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float volumeNum = progress / 100f;//100converted to float so 100f
                        mp.setVolume(volumeNum, volumeNum);//takes two arguments i.e the current volume. getVolume function can help us get the Volume
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
        // Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();
        mp.setOnCompletionListener(mCompletionListener);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = "- " + createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText(remainingTime);

            return true;
        }
    });


public void creation(){
    mp = MediaPlayer.create(this, ma.mword.getIndex());
}

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }



    public void playBtnClick(View view) {

        if (!mp.isPlaying()) {
            // Stopping


            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }

    }
    //Release mediaplayer on completion
    private void releaseMediaPlayer () {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;
        }
    }


    }
