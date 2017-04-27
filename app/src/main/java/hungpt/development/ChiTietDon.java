package hungpt.development;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class ChiTietDon extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    UserSessionManager session;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_don);
        final TextView name = (TextView)findViewById(R.id.tvUsername);
        final TextView mahh = (TextView)findViewById(R.id.tvMaHH);
        final TextView mota = (TextView)findViewById(R.id.tvMota);
        final TextView vtn = (TextView)findViewById(R.id.tvVTN);
        final TextView vtd = (TextView)findViewById(R.id.tvVTD);
        final TextView time = (TextView)findViewById(R.id.tvThoiGian);
        session = new UserSessionManager(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
        final String Name = sharedPreferences.getString("Username", null);
        final String MaHH = sharedPreferences.getString("MaHH", null);
        final String Mota = sharedPreferences.getString("MoTa", null);
        final String VTN = sharedPreferences.getString("VTN", null);
        final String VTD = sharedPreferences.getString("VTD", null);
        final String Time = sharedPreferences.getString("ThoiGian", null);
        final String quyen = sharedPreferences.getString("quyen", null);
        final String ten = sharedPreferences.getString("username", null);
        name.setText(Name);
        mahh.setText(MaHH);
        mota.setText(Mota);
        vtn.setText(VTN);
        vtd.setText(VTD);
        time.setText(Time);

        if(quyen.equals("1")){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.conent, new fragment1()).commit();
        }
        else {
            if(Name.equals(ten)){
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.conent, new fragment2()).commit();



            }
            else {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.conent, new fragment3()).commit();
            }
        }





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
                        Intent intentt1 = new Intent(getApplicationContext(), Acount_Activity.class);
                        startActivity(intentt1);
                        return true;

                }
            }
        });

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
                Intent intent = new Intent(getApplicationContext(), Acount_Activity.class);
                startActivity(intent);

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