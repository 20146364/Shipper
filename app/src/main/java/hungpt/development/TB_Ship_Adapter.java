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

public class TB_Ship_Adapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<TB_Ship> arrayTB_Ship;

    public TB_Ship_Adapter(Context context, int layout, List<TB_Ship> tb_ShipList){
        myContext = context;
        myLayout = layout;
        arrayTB_Ship = tb_ShipList;

    }

    @Override
    public int getCount() {
        return arrayTB_Ship.size();
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
        Username.setText(arrayTB_Ship.get(position).Username);
        TextView MaHH = (TextView) convertView.findViewById(R.id.tvMa);
        MaHH.setText(arrayTB_Ship.get(position).MaHH);
        return  convertView;
    }
}
