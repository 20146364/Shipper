package hungpt.development;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hung Phan on 4/5/2017.
 */

public class SuaDonHang extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    UserSessionManager session;
    SharedPreferences sharedPreferences;

    private static final String LOG_TAG = "SuaDonHang";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView, mAutocompleteTextView2;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(21.028148, 105.836963), new LatLng(21.028148, 106.836963));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_don_hang);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .etVTD);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        mAutocompleteTextView2 = (AutoCompleteTextView) findViewById(R.id
                .etVTN);
        mAutocompleteTextView2.setThreshold(3);
        mAutocompleteTextView2.setOnItemClickListener(mAutocompleteClickListener);
        mAutocompleteTextView2.setAdapter(mPlaceArrayAdapter);

        final TextView etSoTK = (TextView)findViewById(R.id.id) ;
        final EditText etMota = (EditText)findViewById(R.id.etMota);
        final EditText etVTN = (EditText)findViewById(R.id.etVTN);
        final EditText etVTD = (EditText)findViewById(R.id.etVTD);
        final EditText etThoiGian = (EditText)findViewById(R.id.etTime);
        final Button btSave = (Button) findViewById(R.id.btSave);


        session = new UserSessionManager(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("My app", 0);
        final String Mota = sharedPreferences.getString("MoTa", null);
        final String VTN = sharedPreferences.getString("VTN", null);
        final String VTD = sharedPreferences.getString("VTD", null);
        final String Time = sharedPreferences.getString("ThoiGian", null);
        final String quyen = sharedPreferences.getString("quyen", null);
        final String sotk = sharedPreferences.getString("sotk", null);
        final String MaHH = sharedPreferences.getString("MaHH", null);
        etSoTK.setText(sotk);
        etMota.setText(Mota);
        etVTN.setText(VTN);
        etVTD.setText(VTD);
        etThoiGian.setText(Time);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  mahh = MaHH.toString();
                final String sotk = etSoTK.getText().toString();
                final String mota = etMota.getText().toString();
                final String vtn = etVTN.getText().toString();
                final String vtd = etVTD.getText().toString();
                final String time = etThoiGian.getText().toString();
                Response.Listener<String>responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new  AlertDialog.Builder(SuaDonHang.this);
                                builder.setMessage("Cập nhật thành công")
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(getApplicationContext(), ChuDonActivity.class);
                                startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new  AlertDialog.Builder(SuaDonHang.this);
                                builder.setMessage("Có Lỗi Xảy ra")
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                Edit_DonHang edit_donHang = new Edit_DonHang(mahh, sotk, mota, vtn, vtd,time,  responseListener);
                RequestQueue queue = Volley.newRequestQueue(SuaDonHang.this);
                queue.add(edit_donHang);


            }
        });

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

    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)findViewById(R.id.etTime);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

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

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

}
