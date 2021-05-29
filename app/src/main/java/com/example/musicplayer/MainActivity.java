package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import android.os.Handler;
public class MainActivity extends AppCompatActivity {

public static Word mword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//Disable dark mode
        setContentView(R.layout.activity_main);
         final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("আমার সোনার বাংলা", "Nobel" ,R.raw.a));
        words.add(new Word("মাঝে মাঝে তব", "Prashmita Paul",R.raw.b));
        words.add(new Word("আমি বাংলায় গান গাই", "Pratul Mukhopadhyay",R.raw.c));
        words.add(new Word("তুমি আসবে বলে", "Nachiketa",R.raw.d));
        words.add(new Word("ধনধান্য পুষ্প ভরা", "Dwijendralal Roy",R.raw.e));
        words.add(new Word("বাংলার মাটি, বাংলার জল", "Rabindranath Tagore",R.raw.f));
        words.add(new Word("আমার হাত বান্ধবী", "Sahana Bajpaie",R.raw.g));
        words.add(new Word("দেখো আলোয় আলো আকাশ", "Arijit Singh",R.raw.h));

        WordAdapter itemsAdapter = new WordAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //storing the outcome of get() in word variable

                Word word = words.get(position);
                mword=word;
                //Word is class/data type, word is object, (words is arraylist and get() is arraylist methods)

                Intent playsong= new Intent(MainActivity.this, Songpage.class);
                startActivity(playsong);

            }
        });

    }
public int sendPosition(int position){
        return position;
}
}