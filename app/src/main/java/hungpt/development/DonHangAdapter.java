package hungpt.development;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hung Phan on 3/26/2017.
 */

public class DonHangAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<DonHang> arrayDonHang;

    public DonHangAdapter(Context context, int layout, List<DonHang> donHangList){
        myContext = context;
        myLayout = layout;
        arrayDonHang = donHangList;

    }

    @Override
    public int getCount() {
       return arrayDonHang.size();
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
        Username.setText(arrayDonHang.get(position).Username);
        TextView MoTa = (TextView) convertView.findViewById(R.id.tvMota);
        MoTa.setText(arrayDonHang.get(position).MoTa);
        TextView VTN = (TextView) convertView.findViewById(R.id.tvVTN);
        VTN.setText(arrayDonHang.get(position).VTN);
        TextView ThoiGian = (TextView) convertView.findViewById(R.id.tvThoiGian);
        ThoiGian.setText(arrayDonHang.get(position).ThoiGian);


        return  convertView;
    }
}
