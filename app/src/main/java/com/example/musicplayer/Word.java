package com.example.musicplayer;

public class Word {

        /** Default translation for the word */
        private String mSongName;

        /** Miwok translation for the word */
        private String mSongWriter;

        private int mImageResourceId;
        private int mindex;
    public Word(String SongName, String SongWriter,int index) {
        mSongWriter=SongWriter;
        mSongName=SongName;
        mindex=index;

    }


        public String getSongWriter() {
            return mSongWriter;
        }

        public String getSongName() {
            return mSongName;
        }

    public int getIndex() { return mindex;}
    }


