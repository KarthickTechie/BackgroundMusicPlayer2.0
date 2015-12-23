package musicapp.karthick.com.backgroundmusicplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Player extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView mLyricTxt;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private Handler mHandler = new Handler();
    private TimerHelper timerHelper;
    private Resources resources;
    private InputStream iS;
    private ByteArrayOutputStream oS;


    // Media Player
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        resources = getResources();
        toolbar= (Toolbar) findViewById(R.id.id_appBar);
        setSupportActionBar(toolbar);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        mLyricTxt = (TextView)findViewById(R.id.tv_songTitle);

        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);

        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

        currentSongIndex=getIntent().getIntExtra("songIndex",0);
        try {
            playSong(getIntent().getIntExtra("songIndex",0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mediaplayer

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


                mp.reset();

                /*
                try {
                    if (currentSongIndex < (Songs.songsListArray.length - 1)) {
                        playSong(currentSongIndex + 1);
                        currentSongIndex = currentSongIndex + 1;
                    } else
                        playSong(0);
                    currentSongIndex = 0;
                } catch (Exception e) {
                    Log.e("MUSIC SERVICE", "Error setting data source", e);
                }
                mp.prepareAsync();
                /*
                if (mp.getCurrentPosition()== mp.getDuration()){

                    if(currentSongIndex < (Songs.songsListArray.length - 1)){
                        mp.reset();
                        playSong(currentSongIndex + 1);
                        currentSongIndex = currentSongIndex + 1;
                    }else{
                        // play first song
                        mp.reset();
                        playSong(0);
                        currentSongIndex = 0;
                    }

                }
            */
            }
        });

        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int mProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = mp.getDuration();
                int currentPosition = timerHelper.progressToTimer(mProgress, totalDuration);

                // forward or backward to certain seconds
                mp.seekTo(currentPosition);

                // update timer progress again
                updateProgressBar();

            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        timerHelper = new TimerHelper();
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if(mp.isPlaying()){
                    if(mp!=null){
                        mp.pause();
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.btn_play);
                    }
                }else{
                    // Resume song
                    if(mp!=null){
                        mp.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        });


        // song forward
        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mp.getDuration()){
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
            }
        });

        /**
         * Backward button click event
         * Backward song to specified seconds
         * */
        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if(currentPosition - seekBackwardTime >= 0){
                    // forward song
                    mp.seekTo(currentPosition - seekBackwardTime);
                }else{
                    // backward to starting position
                    mp.seekTo(0);
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check if next song is there or not

                if(currentSongIndex < (Songs.songsListArray.length - 1)){
                    mp.reset();
                    try {
                        playSong(currentSongIndex + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentSongIndex = currentSongIndex + 1;
                }else{
                    // play first song
                    mp.reset();
                    try {
                        playSong(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentSongIndex = 0;
                }

            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(currentSongIndex > 0){
                    mp.reset();
                    try {
                        playSong(currentSongIndex - 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentSongIndex = currentSongIndex - 1;
                }else{
                    // play last song
                    mp.reset();
                    try {
                        playSong(Songs.songsListArray.length - 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentSongIndex = Songs.songsListArray.length - 1;
                }

            }
        });




    }


    public void  playSong(int songIndex) throws IOException {
        // Play song


            mp = MediaPlayer.create(getApplicationContext(), Songs.songsListArray[songIndex]);
            mp.start();
            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);
            mLyricTxt.setText(updateTextView(Songs.englishLyrics[songIndex]));
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
        updateProgressBar();



    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+ timerHelper.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+ timerHelper.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(timerHelper.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);


            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_next) {
            /*
            Toast.makeText(getApplicationContext(),"Clickeed",Toast.LENGTH_SHORT).show();
            Intent in = new Intent(this,SecondActivity.class);
            startActivity(in);
            */
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select language");
            builder.setItems(R.array.languages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (which) {
                        case 0:
                            //Toast.makeText(getApplicationContext(), "Language  :  English", Toast.LENGTH_SHORT).show();
                            try {

                                mLyricTxt.setText(updateTextView(Songs.englishLyrics[currentSongIndex]));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;
                        case 1:

                            try {
                                mLyricTxt.setText(updateTextView(Songs.kannadaLyrics[currentSongIndex]));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 2:

                            try {
                                mLyricTxt.setText(updateTextView(Songs.sanskritLyrics[currentSongIndex]));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:

                            try {
                                mLyricTxt.setText(updateTextView(Songs.tamilLyrics[currentSongIndex]));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:

                            try {
                                mLyricTxt.setText(updateTextView(Songs.teluguLyrics[currentSongIndex]));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                    }
                }
            });

            builder.create().show();

        }
        return true;
    }

    public String updateTextView(int mValue) throws IOException {
        iS = resources.openRawResource(mValue);
        byte[] buffer = new byte[iS.available()];
        //read the text file as a stream, into the buffer
        iS.read(buffer);
        oS = new ByteArrayOutputStream();
        //write this buffer to the output stream
        oS.write(buffer);
        //Close the Input and Output streams
        oS.close();
        iS.close();
        return oS.toString();

    }

}
