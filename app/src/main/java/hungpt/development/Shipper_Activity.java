package hungpt.development;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Shipper_Activity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    UserSessionManager session;
    SharedPreferences sharedPreferences;

    ListView lvSinhVien;
    ArrayList<DonHang> mangDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);

        lvSinhVien = (ListView)findViewById(R.id.lvDonHang);
        mangDonHang = new ArrayList<DonHang>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJASON().execute("http://192.168.1.52/myshipper/db_DonHang.php");
            }
        });
//        mangDonHang.add(new DonHang("hungpt", "Mo ta", "Ha Noi", "20/11/2017"));




        
        //tạo Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        //tạo nav menu
        mDrawerLayout =(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set click item nav menu
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case  R.id.nav_home:
                        Intent intent =new Intent(getApplicationContext(), Shipper_Activity.class);

                        //SharedPreferences.Editor edit= sharedPreferences.edit();

                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        session.logoutUser();
                        return true;
                    default:
                    Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                    return true;
                    case R.id.nav_acount:
                        Intent intentt1 = new Intent(Shipper_Activity.this, Acount_Activity.class);
                        Shipper_Activity.this.startActivity(intentt1);
                        return true;

                }
            }
        });
        //Tạo thanh TabHost
        TabHost tabHost = (TabHost)findViewById(R.id.TabHost);
        tabHost.setup();
        TabHost.TabSpec spe1 = tabHost.newTabSpec("TAB 1");
        spe1.setIndicator("TAB 1");
        spe1.setContent(R.id.layout1);
        spe1.setIndicator("", getResources().getDrawable(R.mipmap.ic_home_black_24dp));
        tabHost.addTab(spe1);
        //load listview cho trang dơn hàng



        //Tạo thanh TabHost
        TabHost.TabSpec spe2 = tabHost.newTabSpec("TAB 2");
        spe2.setIndicator("TAB 2");
        spe2.setContent(R.id.layout2);
        spe2.setIndicator("", getResources().getDrawable(R.mipmap.ic_note_add_black_24dp));
        tabHost.addTab(spe2);

        //Tạo thanh TabHost
        TabHost.TabSpec spe3 = tabHost.newTabSpec("TAB 3");
        spe3.setIndicator("TAB 3");
        spe3.setIndicator("", getResources().getDrawable(R.mipmap.ic_message_black_24dp));
        spe3.setContent(R.id.layout3);
        tabHost.addTab(spe3);

        //Tạo thanh TabHost
        TabHost.TabSpec spe4 = tabHost.newTabSpec("TAB 4");
        spe4.setIndicator("TAB 4");
        spe4.setContent(R.id.layout4);
        spe4.setIndicator("TAB4", getResources().getDrawable(R.mipmap.ic_notifications_black_24dp));
        tabHost.addTab(spe4);


        //Tạo thanh TabHost
        TabHost.TabSpec spe5 = tabHost.newTabSpec("TAB 5");
        spe5.setIndicator("TAB 5");
        spe5.setContent(R.id.layout5);
        spe5.setIndicator("", getResources().getDrawable(R.drawable.ic_map_black_24dp));
        tabHost.addTab(spe5);
    }
    class  docJASON extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root = new JSONObject(s);
                JSONArray mang = root.getJSONArray("donhang");
                for (int i=0; i< mang.length();i++){
                    JSONObject Username = mang.getJSONObject(i);
                    JSONObject MoTa = mang.getJSONObject(i);
                    JSONObject VTN = mang.getJSONObject(i);
                    JSONObject ThoiGian = mang.getJSONObject(i);
                    Username.getString("Username");
                    MoTa.getString("MoTa");
                    VTN.getString("VTN");
                    ThoiGian.getString("ThoiGian");
                    mangDonHang.add(new DonHang(Username.getString("Username"),
                            MoTa.getString("MoTa"),VTN.getString("VTN"),ThoiGian.getString("ThoiGian") ));

                }
                DonHangAdapter adapter = new DonHangAdapter(Shipper_Activity.this, R.layout.lv_donhang, mangDonHang);
                lvSinhVien.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }


    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }


    // tạo option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.option_menu, menu);
        inflate.inflate(R.menu.search, menu);
        return  true;
    }

    //set click item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Acount:
                Intent intentt = new Intent(Shipper_Activity.this, Acount_Activity.class);
                Shipper_Activity.this.startActivity(intentt);

                return true;
            case R.id.Setting:
                return true;
            case R.id.logout:
                session.logoutUser();
        }


        //set click icon menu navigation menu
        if (mToggle.onOptionsItemSelected(item)) {
            final TextView hello = (TextView) findViewById(R.id.hello);
            session = new UserSessionManager(getApplicationContext());
            sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
            final String ten = sharedPreferences.getString("username", null);
            hello.setText("Hello" + " " + ten);
            return true;


        }
        return super.onOptionsItemSelected(item);

    }
}