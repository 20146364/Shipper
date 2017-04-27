package hungpt.development;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hung Phan on 4/10/2017.
 */

public class ThongBaoAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<ThongBao> arrayThongBao;

    public ThongBaoAdapter(Context context, int layout, List<ThongBao> thongBaoList){
        myContext = context;
        myLayout = layout;
        arrayThongBao = thongBaoList;

    }

    @Override
    public int getCount() {
        return arrayThongBao.size();
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
        Username.setText(arrayThongBao.get(position).Username);
        TextView TGNhan = (TextView) convertView.findViewById(R.id.tvTGNhan);
        TGNhan.setText(arrayThongBao.get(position).TGNhan);
        TextView MaHH = (TextView) convertView.findViewById(R.id.tvMa);
        MaHH.setText(arrayThongBao.get(position).MaHH);


        return  convertView;
    }
}
