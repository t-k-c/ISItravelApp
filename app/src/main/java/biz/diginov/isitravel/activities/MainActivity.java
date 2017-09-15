package biz.diginov.isitravel.activities;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import biz.diginov.isitravel.R;
import biz.diginov.isitravel.entities.LocationService;
import biz.diginov.isitravel.entities.LocationAddress;
import biz.diginov.isitravel.utils.DateUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        displayStyledDate();

        locationService = new LocationService(MainActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startGettingAddress();
            }
        }, 2000);
    }

    private void displayStyledDate() {
        dayOfWeek.setText(DateUtils.getDayOfWeek());
        dayOfMonth.setText(DateUtils.getDayOfMonth());
        monthAndYear.setText(DateUtils.getMonthOfYear() + " " + DateUtils.getYear());
    }

    private void startGettingAddress() {
        Location location = locationService.getLocation();
        //you can hard-code the lat & long if you have issues with getting it
        //remove the below if-condition and use the following couple of lines
        //double latitude = 37.422005;
        //double longitude = -122.084095

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress.getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeoCoderHandler());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGettingAddress();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            city.setText(locationAddress);
        }

    }

    //My Views : Time Based
    @BindView(R.id.tv_day_of_month)
    TextView dayOfMonth;
    @BindView(R.id.tv_day_of_week)
    TextView dayOfWeek;
    @BindView(R.id.tv_month_year)
    TextView monthAndYear;

    //My Views : Driver Based
    @BindView(R.id.profile_pic)
    ImageView profilePicture;
    @BindView(R.id.tv_driver_name)
    TextView driverName;
    @BindView(R.id.tv_driver_number)
    TextView diverIndex;
    @BindView(R.id.tv_client_name)
    TextView driversCompany;
    @BindView(R.id.tv_city)
    TextView city;
}
