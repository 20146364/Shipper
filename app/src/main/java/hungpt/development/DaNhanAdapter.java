package hungpt.development;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hung Phan on 4/18/2017.
 */

public class DaNhanAdapter extends BaseAdapter{
    Context myContext;
    int myLayout;
    List<DaNhan> arrayDaNhan;

    public DaNhanAdapter(Context context, int layout, List<DaNhan> daNhanList){
        myContext = context;
        myLayout = layout;
        arrayDaNhan= daNhanList;
    }

    @Override
    public int getCount() {
        return arrayDaNhan.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        TextView Username = (TextView) convertView.findViewById(R.id.tvUsername);
        Username.setText(arrayDaNhan.get(position).Username);
        TextView MoTa = (TextView) convertView.findViewById(R.id.tvMota);
        MoTa.setText(arrayDaNhan.get(position).MoTa);
        TextView VTN = (TextView) convertView.findViewById(R.id.tvVTN);
        VTN.setText(arrayDaNhan.get(position).VTN);
//        TextView ThoiGian = (TextView) convertView.findViewById(R.id.tvThoiGian);
//        ThoiGian.setText(arrayDaNhan.get(position).ThoiGian);


        return  convertView;
    }
}


