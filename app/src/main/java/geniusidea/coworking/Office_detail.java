package geniusidea.coworking;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.usb.UsbConstants;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import geniusidea.coworking.Adapter.AdapterIconFacility;
import geniusidea.coworking.Model.GetIconResponse;
import geniusidea.coworking.Model.ListOfficeSpace;
import geniusidea.coworking.Model.PostIdOffice;
import geniusidea.coworking.Model.ReponseRoomDetail;
import geniusidea.coworking.Model.SearchOffice;
import geniusidea.coworking.Service.Retrofit.APIService;
import geniusidea.coworking.Service.ImageHandling.HeightEvaluator;
import geniusidea.coworking.Service.Retrofit.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Office_detail extends AppCompatActivity implements OnMapReadyCallback {
    ImageView imgMainPict,secondPict,thirdPict,fourthPict,fivePict,imgBack;
    TextView txtOfficeName,txtLocation,txtDescription,txtAddress;
    GoogleMap map;
  //  ListView listView;
    LinearLayout layLoading,layDetail;
    AVLoadingIndicatorView avi;
    ShimmerFrameLayout shimmerFrameLayout;

    String urlPict[];
    String id[];
    String nama_ruangan[];
    String id_office[];
    String main_picture[];
    String facilities[];
    String price[];
    String roomCapacity[];
    String roomSize[];
    String room_plan[];
    String about_room[];
    String[] icon,nameFacility;
    List<ListOfficeSpace> data = new ArrayList<>();
    List<ReponseRoomDetail> roomDetail = new ArrayList<>();
    List<GetIconResponse> iconFacility = new ArrayList<>();
    RecyclerView rcFacility;
    AdapterIconFacility myAdapterIcon;
    int press=0;

    AdapterRoom myAdapter= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);


        imgMainPict = findViewById(R.id.imgMainPict);
        secondPict = findViewById(R.id.secondPict);
        thirdPict = findViewById(R.id.thirdPict);
        fourthPict = findViewById(R.id.fourthPict);
        fivePict = findViewById(R.id.fivePict);
        txtOfficeName = findViewById(R.id.txtOfficeName);
        txtLocation = findViewById(R.id.txtLocation);
        txtDescription = findViewById(R.id.txtDescription);
        txtAddress = findViewById(R.id.txtAddress);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        layDetail = findViewById(R.id.lay_detail);
        layLoading = findViewById(R.id.layLoading);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        avi = findViewById(R.id.avLoading);
        avi.show();
        rcFacility = findViewById(R.id.rcFacility);
        rcFacility.setLayoutManager(new LinearLayoutManager(Office_detail.this,LinearLayoutManager.HORIZONTAL,false));
        myAdapterIcon = new AdapterIconFacility(Office_detail.this,rcFacility,iconFacility);

        getDetailRoom(getIntent().getStringExtra("idOffice"));

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) Office_detail.this.getSystemService(WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(dm);
        }
        int heightInDP = Math.round(dm.heightPixels / dm.density);
        int myHeight = heightInDP - 70;
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final int pixel1 = (int) (myHeight * scale +0.5f);

        final View slideList = findViewById(R.id.slideList);
        final View handle = findViewById(R.id.handle);
        final ImageView imgHandle= findViewById(R.id.imgHandle);
        imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black);

        handle.setOnTouchListener(new View.OnTouchListener() {
            /* Starting Y point (where touch started). */
            float yStart = 0;
            /* Default height when in the open state. */
            float closedHeight = 150;
            /* Default height when in the closed state. */
            float openHeight = pixel1;
            /* The height during the transition (changed on ACTION_MOVE). */
            float currentHeight;
            /* The last y touch that occurred. This is used to determine if the view should snap up or down on release.
             * Used in conjunction with directionDown boolean. */
            float lastY = 0;
            boolean directionDown = false;
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch(event.getAction()) {

                    /* User tapped down on screen. */
                    case MotionEvent.ACTION_DOWN:
                        // User has tapped the screen
                        yStart = event.getRawY();
                        lastY = event.getRawY();
                        currentHeight = slideList.getHeight();
                        press = 1;
                        break;

                    /* User is dragging finger. */
                    case MotionEvent.ACTION_MOVE:

                        // Calculate the total height change thus far.
                        float totalHeightDiff = event.getRawY() - yStart;

                        // Adjust the slide down height immediately with touch movements.
                        ViewGroup.LayoutParams params = slideList.getLayoutParams();
                        params.height = (int)(currentHeight - totalHeightDiff);
                        slideList.setLayoutParams(params);

                        // Check and set which direction drag is moving.
                        if (event.getRawY() > lastY) {
                            directionDown = true;
                        } else {
                            directionDown = false;
                        }

                        // Set the lastY for comparison in the next ACTION_MOVE event.
                        lastY = event.getRawY();
                        break;

                    /* User lifted up finger. */
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:

                        /*
                         * Need to snap either up or down. Using ValueAnimator to "finish" the action.
                         * HeightEvaluator is a custom class.
                         *
                         * NOTE: I'm using the nineoldandroids library for
                         */
                        if (directionDown) {

                            // Close the sliding view.
                            press = 0;
                            int startHeight = slideList.getHeight();
                            ValueAnimator animation = ValueAnimator.ofObject(
                                    new HeightEvaluator(slideList),
                                    startHeight,
                                    (int) closedHeight).setDuration(300);
                            animation.setInterpolator(new OvershootInterpolator(1));
                            animation.start();
                            imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black);

                        } else {

                            press = 1;
                            // Open the sliding view.
                            int startHeight = slideList.getHeight();

                            ValueAnimator animation = ValueAnimator.ofObject(
                                    new HeightEvaluator(slideList),
                                    startHeight,
                                    (int) openHeight).setDuration(300);

                            // See Table 3 for other interpolator options
                            // - http://developer.android.com/guide/topics/graphics/prop-animation.html
                            animation.setInterpolator(new OvershootInterpolator(1));
                            animation.start();
                            imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black);
                        }
                        break;

                }
                return true;
            }
        });


        //Toast.makeText(getApplicationContext(),""+getIntent().getStringExtra("idOffice"),Toast.LENGTH_SHORT).show();
    }

    public void getRoom(String idOffice){
        PostIdOffice postIdOffice = new PostIdOffice(idOffice,"load_detail_space_detail");
        APIService service = APIClient.getClient().create(APIService.class);
        Call<SearchOffice> get = service.detailOffice("load_detail_space_detail",idOffice);
        get.enqueue(new Callback<SearchOffice>() {
            @Override
            public void onResponse(Call<SearchOffice> call, Response<SearchOffice> response) {

            }

            @Override
            public void onFailure(Call<SearchOffice> call, Throwable t) {

            }
        });

    }

    public void getDetailRoom(final String idOffice){
        iconFacility.clear();
        final ListView listView = (ListView) findViewById(R.id.listRoom);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Office_detail.this,Room_detail.class)
                .putExtra("idRoom",""+id[i]));
            }
        });
        ViewCompat.setNestedScrollingEnabled(listView, true);
        PostIdOffice postIdOffice = new PostIdOffice(idOffice,"load_detail_space_detail");
        APIService service = APIClient.getClient().create(APIService.class);
        Call<SearchOffice> getDetail = service.detailOffice("load_detail_space_detail",idOffice);
        getDetail.enqueue(new Callback<SearchOffice>() {
            @Override
            public void onResponse(Call<SearchOffice> call, Response<SearchOffice> response) {
                if (response.isSuccessful()){
                    Picasso.with(Office_detail.this)
                            .load("" + response.body().getMain_picture())
                            .noPlaceholder()
                            .resize(810, 453)
                            .centerCrop()
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(imgMainPict);
                    txtOfficeName.setText(response.body().getNama_tempat());
                    txtAddress.setText(response.body().getAddress());
                    txtDescription.setText(response.body().getDescription());
                    txtLocation.setText(response.body().getCity()+", Indonesia");
                    data = response.body().getPhotos();
                  //  Toast.makeText(getApplicationContext(),""+data,Toast.LENGTH_SHORT).show();
                    if (data.size()!=0){
                        urlPict = new String[data.size()];
                        for (int i = 0; i < data.size(); i++) {
                            if (i==0) {
                                Picasso.with(Office_detail.this)
                                        .load("" + data.get(i).getUrl_pict())
                                        .noPlaceholder()
                                        .resize(810, 453)
                                        .centerCrop()
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        .into(secondPict);
                            }else if (i==1){
                                Picasso.with(Office_detail.this)
                                        .load("" + data.get(i).getUrl_pict())
                                        .noPlaceholder()
                                        .resize(810, 453)
                                        .centerCrop()
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        .into(thirdPict);
                            }else if (i==2){
                                Picasso.with(Office_detail.this)
                                        .load("" + data.get(i).getUrl_pict())
                                        .noPlaceholder()
                                        .resize(810, 453)
                                        .centerCrop()
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        .into(fourthPict);
                            }else if (i==3){
                                Picasso.with(Office_detail.this)
                                        .load("" + data.get(i).getUrl_pict())
                                        .noPlaceholder()
                                        .resize(810, 453)
                                        .centerCrop()
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        .into(fivePict);
                            }
                        }
                    }
                    roomDetail = response.body().getAvailableRoom();
              //      Toast.makeText(getApplicationContext(),""+roomDetail,Toast.LENGTH_SHORT).show();
                    if (roomDetail.size()!=0){
                        id = new String[roomDetail.size()];
                        nama_ruangan = new String[roomDetail.size()];
                        id_office = new String[roomDetail.size()];
                        main_picture = new String[roomDetail.size()];
                        facilities = new String[roomDetail.size()];
                        price = new String[roomDetail.size()];
                        roomCapacity = new String[roomDetail.size()];
                        roomSize = new String[roomDetail.size()];
                        room_plan = new String[roomDetail.size()];
                        about_room = new String[roomDetail.size()];

                        for (int i=0;i<roomDetail.size();i++){
                            id[i] = roomDetail.get(i).getId();
                            nama_ruangan[i] = roomDetail.get(i).getNamaRuangan();
                            id_office[i] = roomDetail.get(i).getIdOffice();
                            main_picture[i] = roomDetail.get(i).getMain_picture();
                            facilities[i] = roomDetail.get(i).getFacilities();
                            price[i] = roomDetail.get(i).getPrice();
                            roomCapacity[i] = roomDetail.get(i).getRoomCapacity();
                            room_plan[i] = roomDetail.get(i).getRoomPlan();
                            roomSize[i] = roomDetail.get(i).getRoomSize();
                            about_room[i] = roomDetail.get(i).getAboutRoom();
                        }
                        myAdapter = new AdapterRoom(Office_detail.this,id,nama_ruangan,id_office,main_picture,facilities,price,roomSize,roomCapacity,
                                    room_plan,about_room);
                        listView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();

                        List<GetIconResponse> datas = response.body().getIconResponses();

                        if (datas.size()!=0){
                            icon = new String[datas.size()];
                            nameFacility = new String[datas.size()];
                            for (int i=0;i<datas.size();i++){
                                icon[i] = datas.get(i).getIcon();
                                nameFacility[i] = datas.get(i).getName();

                                GetIconResponse getIconResponse = new GetIconResponse(icon[i],nameFacility[i]);
                                iconFacility.add(getIconResponse);
                            }
                            rcFacility.setAdapter(myAdapterIcon);
                            myAdapterIcon.notifyDataSetChanged();
                            myAdapterIcon.setLoaded();

                        }
                    }
                    layLoading.setVisibility(View.GONE);
                    layDetail.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    avi.hide();
                }else {
                    Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchOffice> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Intent getdata = getIntent();
        Double Lat = Double.parseDouble("" + getdata.getStringExtra("Lat"));
        Double Lng = Double.parseDouble("" + getdata.getStringExtra("Lng"));
        LatLng posisi = new LatLng(Lat, Lng);
        map.addMarker(new MarkerOptions()
                .position(posisi));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(posisi)
                .zoom(17)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    public class AdapterRoom extends ArrayAdapter<String>{
        String id[];
        String nama_ruangan[];
        String id_office[];
        String main_picture[];
        String facilities[];
        String price[];
        String roomCapacity[];
        String roomSize[];
        String room_plan[];
        String about_room[];
        Context c;
        public AdapterRoom(Context context, String[] id,String[] nama_ruangan,String[] id_office,String[] main_picture,String[] facilities,
                           String[] price,String[] roomSize,String[] roomCapacity,String[] room_plan,String[] about_room) {
            super(context, R.layout.model_list_room, id);
            this.c = context;
            this.about_room = about_room;
            this.facilities = facilities;
            this.id = id;
            this.id_office = id_office;
            this.main_picture = main_picture;
            this.nama_ruangan = nama_ruangan;
            this.price = price;
            this.room_plan = room_plan;
            this.roomCapacity = roomCapacity;
            this.roomSize = roomSize;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.model_list_room,parent,false);
            ImageView imgMainRoom = view.findViewById(R.id.imgMainRoom);
            TextView txtRoomName = view.findViewById(R.id.txtRoomName);
            TextView txtPrice = view.findViewById(R.id.txtPrice);
            TextView txtRoomCapacity = view.findViewById(R.id.txtRoomCapacity);
            TextView txtRoomSize = view.findViewById(R.id.txtRoomSize);
            Picasso.with(c)
                    .load("" + main_picture[position])
                    .noPlaceholder()
                    .resize(810, 453)
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imgMainRoom);
            txtRoomCapacity.setText(roomCapacity[position]+" person");
            txtRoomName.setText(nama_ruangan[position]);
            txtRoomSize.setText(roomSize[position]+" sqm");
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            int prices = Integer.parseInt(price[position]);
            txtPrice.setText("Rp. "+prices+",-");
            return view;
        }
    }

    @Override
    public void onBackPressed() {
//        if(press==0)
//        {
//         //   Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
//            press = 1;
            super.onBackPressed();
//
////            new Handler().postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    press = 0;
////                }
////            },3000);
//        }
//        else {
//            press = 0;
//            int startHeight = slideList.getHeight();
//            ValueAnimator animation = ValueAnimator.ofObject(
//                    new HeightEvaluator(slideList),
//                    startHeight,
//                    (int) closedHeight).setDuration(300);
//            animation.setInterpolator(new OvershootInterpolator(1));
//            animation.start();
//            imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black);

          //  press = 0;
   //     }

    }
}
