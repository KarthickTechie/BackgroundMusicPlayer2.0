package musicapp.karthick.com.backgroundmusicplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 12/22/2015.
 */
public class NavigationDrawerListAdapter extends BaseAdapter {

    private Context context;
    private String[]mOptionsArray;

    NavigationDrawerListAdapter(Context context,String[]mOptionsArray)
    {
        this.context=context;
        this.mOptionsArray=mOptionsArray;
    }

    @Override
    public int getCount() {
        return mOptionsArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mOptionsArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.drawerlist, parent, false);
        TextView option = (TextView)view.findViewById(R.id.tv_optionsList);
        ImageView logo = (ImageView)view.findViewById(R.id.nav_imageView);
        option.setText(mOptionsArray[position]);
        logo.setImageResource(R.drawable.om);


        return view;
    }
}
