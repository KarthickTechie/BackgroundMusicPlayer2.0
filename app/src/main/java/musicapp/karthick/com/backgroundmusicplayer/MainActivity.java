package musicapp.karthick.com.backgroundmusicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;
    Intent playerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.songsList);
        toolbar= (Toolbar) findViewById(R.id.id_appBar);
        setSupportActionBar(toolbar);
        SongListAdapter adapter = new SongListAdapter(MainActivity.this,Songs.songsListArray,Songs.songNameArray);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

// getting listitem index
                int songIndex = i;

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        Player.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                // setResult(100, in);
                startActivityForResult(in, 100);
                // Closing PlayListView
                finish();

            }
        });

    }


}
