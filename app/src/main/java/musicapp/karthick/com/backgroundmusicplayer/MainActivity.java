package musicapp.karthick.com.backgroundmusicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private String[]optionsList;
    Intent playerIntent,chooser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        drawerListView= (ListView) findViewById(R.id.drawerMenuList);
        listView=(ListView)findViewById(R.id.songsList);
        toolbar= (Toolbar) findViewById(R.id.id_appBar);
        setSupportActionBar(toolbar);
        SongListAdapter adapter = new SongListAdapter(MainActivity.this,Songs.songsListArray,Songs.songNameArray);
        listView.setAdapter(adapter);
        optionsList = getResources().getStringArray(R.array.menuitems);

        drawerListView.setAdapter(new NavigationDrawerListAdapter(this,optionsList));
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",100);
                        playerIntent.putExtra("AboutSaiAarthi",DataModel.mAbout);
                        startActivity(playerIntent);
                        break;
                    case 1:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",101);
                        playerIntent.putExtra("AboutSaiBaba",DataModel.mAbout);
                        startActivity(playerIntent);
                        break;
                    case 2:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",102);
                        playerIntent.putExtra("AboutThursdayPooja",DataModel.mThursdayPooja);
                        startActivity(playerIntent);
                        break;
                    case 3:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",103);
                        playerIntent.putExtra("AboutSpecialEvent",DataModel.mSpeciaEvent);
                        startActivity(playerIntent);
                        break;
                    case 4:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",104);
                        playerIntent.putExtra("AboutAnnathanam",DataModel.mAnnathanam);
                        startActivity(playerIntent);
                        break;
                    case 5:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",105);
                        playerIntent.putExtra("AboutUpahara",DataModel.mUpahara);
                        startActivity(playerIntent);
                        break;
                    case 6:
                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",106);
                        playerIntent.putExtra("AboutBalaVikas",DataModel.mBalaVikas);
                        startActivity(playerIntent);
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        playerIntent = new Intent(Intent.ACTION_VIEW);
                        playerIntent.setData(Uri.parse(DataModel.mLink));
                        chooser=Intent.createChooser(playerIntent,"MapIntent");
                        startActivityForResult(playerIntent,109);
                        break;
                    case 10:
                        playerIntent = new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",110);
                        playerIntent.putExtra("AboutDeveloper",DataModel.mAboutDeveloper);
                        startActivity(playerIntent);
                        break;
                    case 11:
                        playerIntent = new Intent(Intent.ACTION_SEND);
                        playerIntent.setData(Uri.parse("mailto:"));
                        String[]to = {"saisamsthan@gmail.com","karthick_smartgenn@hotmail.com"};
                        playerIntent.putExtra(Intent.EXTRA_EMAIL,to);
                        playerIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback :");
                        playerIntent.putExtra(Intent.EXTRA_TEXT, "My Feedback :");
                        playerIntent.setType("message/rfc822");
                        startActivity(playerIntent);
                        break;
                    case 12:
                        playerIntent = new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode", 112);
                        playerIntent.putExtra("AboutFaceBookShare",DataModel.mFaceBook);
                        startActivity(playerIntent);
                        break;

                    case 13:

                        playerIntent=new Intent(getApplicationContext(),WebViewActivity.class);
                        playerIntent.putExtra("requestCode",113);
                        playerIntent.putExtra("AboutDeveloper",DataModel.mAboutDeveloper);
                        startActivity(playerIntent);
                        break;
                }
                Toast.makeText(getApplicationContext(), "Clicked at position " + position, Toast.LENGTH_SHORT).show();


            }
        });
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
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.string_open,R.string.string_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
