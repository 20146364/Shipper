package hungpt.development;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckShip extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    UserSessionManager session;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ship);
        final TextView Username = (TextView) findViewById(R.id.id);
        final TextView Mota = (TextView) findViewById(R.id.etMota);
        final TextView VTN = (TextView) findViewById(R.id.etVTN);
        final TextView VTD = (TextView) findViewById(R.id.etVTD);
        final TextView TG = (TextView) findViewById(R.id.etTime);
        final TextView Ma = (TextView) findViewById(R.id.tvMaHH);
        final TextView stkn = (TextView) findViewById(R.id.stkn);
        final Button nhan = (Button) findViewById(R.id.btYes);

        session = new UserSessionManager(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
        final String mahH = sharedPreferences.getString("MaHH", null);
        final String username = sharedPreferences.getString("Username", null);
        final String mota = sharedPreferences.getString("MoTa", null);
        final String vtn = sharedPreferences.getString("VTN", null);
        final String vtd = sharedPreferences.getString("VTD", null);
        final String Time = sharedPreferences.getString("ThoiGian", null);
        final String tgnhan = sharedPreferences.getString("TGNhan", null);
        final String sotkn = sharedPreferences.getString("SoTKNhan", null);
        Username.setText(username);
        Mota.setText(mota);
        VTN.setText(vtn);
        VTD.setText(vtd);
        TG.setText(Time);
        Ma.setText("Mã đơn hàng : "+mahH);
        stkn.setText(sotkn);

         String MaHh = mahH.toString();
         String soTKNhan = sotkn.toString();
         final Response.Listener<String>responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        };
        LuotXem luotXem = new LuotXem(MaHh, soTKNhan, responseListener1);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(luotXem);


        nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String soTknhan = stkn.getText().toString();
                final String MAHH = Ma.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), ChuDonActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Có lỗi xảy ra")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                NhanShiper nhanShiper = new NhanShiper(soTknhan, MAHH, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(nhanShiper);
            }
        });


        //Season ghi nhớ đăng nhập
        session = new UserSessionManager(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
        final String quyen = sharedPreferences.getString("quyen", null);

        // tạo thanh toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);

        // Tạo navigation menu

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);

        //Set click item cho navigation menu
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        if(quyen.equals("1")){
                            Intent intent =new Intent(getApplicationContext(), Shipper_Activity.class);
                            startActivity(intent);}
                        else {
                            Intent intent =new Intent(getApplicationContext(), ChuDonActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    case R.id.nav_logout:
                        session.logoutUser();
                        return true;
                    case R.id.nav_acount:
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
    }

//tạo option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.option_menu, menu);
        return true;
    }

    //set click item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Acount:
                Intent intentt1 = new Intent(getApplicationContext(), Acount_Activity.class);
                startActivity(intentt1);
                return true;
            case R.id.Setting:
                return true;
            case R.id.logout:
                session.logoutUser();
                return true;
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
