package ie.ul.cs4084;

import android.content.Context;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter{
    private  ArrayList<UserModal> userData;
    private Context context;

    public DeckAdapter(ArrayList<UserModal> userData, Context context){
        this.userData = userData;
        this.context = context;
    }

    @Override
    public int getCount(){
        return userData.size();
    }

    @Override
    public Object getItem(int position){
        return userData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v== null){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rv_item, parent, false);
        }

        ((TextView) v.findViewById(R.id.idUserName)).setText(userData.get(position).getName());
        return v;
    }
}
