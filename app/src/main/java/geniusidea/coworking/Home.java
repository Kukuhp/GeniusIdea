package geniusidea.coworking;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;

import java.util.ArrayList;
import java.util.List;

import geniusidea.coworking.LocalDatabase.Data_User_Login;
import geniusidea.coworking.LocalDatabase.Data_User_Token;
import geniusidea.coworking.Model.Get_All_Picture_response;
import geniusidea.coworking.Model.HomeResponse;
import geniusidea.coworking.Model.ListEventSpace;
import geniusidea.coworking.Model.ListLivingSpace;
import geniusidea.coworking.Model.ListOfficeSpace;
import geniusidea.coworking.Model.ResponseHomeMenu;
import geniusidea.coworking.Service.Retrofit.APIService;
import geniusidea.coworking.Service.Retrofit.APIClient;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

import static android.content.Context.WINDOW_SERVICE;

public class Home extends Fragment implements View.OnClickListener {

    View rootview;
    BannerSlider bannerSlider1,bannerSlider2,bannerSlider3,bannerSlider4;
    String[] url;
    String[] kategori;
    String[] kota;
    RotatingTextWrapper custom_switcher;
    LinearLayout linearLayout;
    List<Get_All_Picture_response> dailyRent = new ArrayList<>();
    List<ListEventSpace> eventSpaces = new ArrayList<>();
    List<ListOfficeSpace> officeSpaces = new ArrayList<>();
    List<ListLivingSpace> livingSpaces = new ArrayList<>();
    RelativeLayout cardOffice,cardEvent,cardDaily,cardLiving;
    BoomMenuButton bmb;
    LinearLayout lin1,lin2,lin3,lin4;
    int press = 0;
    private int[] menuIcons = {
            R.drawable.ic_profile_white,
            R.drawable.ic_book_white,
            R.drawable.ic_news_white
    };
    private String[] header = {"Profile","My Reservation","Help"};
    private String[] subText = {"See your profile","Show your reservation list","If you want some question or help"};
    public Home(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
       // setContentView(R.layout.activity_home);
//        bannerSlider1 = (BannerSlider) findViewById(R.id.banner_slider1);
//        bannerSlider2 = (BannerSlider) findViewById(R.id.banner_slider2);
//        bannerSlider3 = (BannerSlider) findViewById(R.id.banner_slider3);
//        bannerSlider4 = (BannerSlider) findViewById(R.id.banner_slider4);
//        bannerSlider1.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void onClick(int position) {
//                startActivity(new Intent(Home.this,Pick_City.class));
//            }
//        });
//        bannerSlider2.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void onClick(int position) {
//                startActivity(new Intent(Home.this,Pick_City.class));
//            }
//        });
//        bannerSlider3.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void onClick(int position) {
//                startActivity(new Intent(Home.this,Pick_City.class));
//            }
//        });
//        bannerSlider4.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void onClick(int position) {
//                startActivity(new Intent(Home.this,Pick_City.class));
//            }
//        });
//        lin1 = findViewById(R.id.lin1);
//        lin2 = findViewById(R.id.lin2);
//        lin3 = findViewById(R.id.lin3);
//        lin4 = findViewById(R.id.lin4);
//        lin1.setOnClickListener(this);
//        lin2.setOnClickListener(this);
//        lin3.setOnClickListener(this);
//        lin4.setOnClickListener(this);
//        bannerSlider2.setOnClickListener(this);
//        bannerSlider3.setOnClickListener(this);
//        bannerSlider4.setOnClickListener(this);
//        cardOffice = findViewById(R.id.cardOffice);
//        cardOffice.setOnClickListener(this);
//        cardDaily = findViewById(R.id.cardDaily);
//        cardDaily.setOnClickListener(this);
//        cardEvent = findViewById(R.id.cardEvent);
//        cardEvent.setOnClickListener(this);
//        cardLiving = findViewById(R.id.cardLiving);
//        cardLiving.setOnClickListener(this);
//        bmb = findViewById(R.id.bmb);
//        final Data_User_Login data_user_login = Data_User_Login.findById(Data_User_Login.class,1L);
//   //     bmb.setButtonEnum(ButtonEnum.Ham);
//        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
//            HamButton.Builder builder = new HamButton.Builder()
//                    .normalImageRes(menuIcons[i])
//                    .imagePadding(new Rect(5,5,5,5))
//                    .listener(new OnBMClickListener() {
//                        @Override
//                        public void onBoomButtonClick(int index) {
//                            if (index==0){
//                                if (data_user_login.Login){
//                                    startActivity(new Intent(Home.this,Profile.class));
//                                }else {
//                                    startActivity(new Intent(Home.this,Login.class));
//                                }
//                            }else if (index==1){
//                                startActivity(new Intent(Home.this,BookingList.class));
//                            }else if (index==2){
//                               // startActivity(new Intent(Home.this,BookingList.class));
//                            }
//                        }
//                    })
//                    .normalText(header[i])
//                    .subNormalText(subText[i]);
//            bmb.addBuilder(builder);
//        }
//
//        //}
//        getPict();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootview==null){
            rootview = inflater.inflate(R.layout.activity_home, container, false);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            bannerSlider1 = (BannerSlider) rootview.findViewById(R.id.banner_slider1);
            bannerSlider2 = (BannerSlider) rootview.findViewById(R.id.banner_slider2);
            bannerSlider3 = (BannerSlider) rootview.findViewById(R.id.banner_slider3);
            bannerSlider4 = (BannerSlider) rootview.findViewById(R.id.banner_slider4);
            bannerSlider1.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onClick(int position) {
                    startActivity(new Intent(getActivity(),Pick_City.class));
                }
            });
            bannerSlider2.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onClick(int position) {
                    startActivity(new Intent(getActivity(),Booking_Date.class));
                }
            });
            bannerSlider3.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onClick(int position) {
                    startActivity(new Intent(getActivity(),Pick_City.class));
                }
            });
            bannerSlider4.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onClick(int position) {
                    startActivity(new Intent(getActivity(),Pick_City.class));
                }
            });
            lin1 = rootview.findViewById(R.id.lin1);
            lin2 = rootview.findViewById(R.id.lin2);
            lin3 = rootview.findViewById(R.id.lin3);
            lin4 = rootview.findViewById(R.id.lin4);
            lin1.setOnClickListener(this);
            lin2.setOnClickListener(this);
            lin3.setOnClickListener(this);
            lin4.setOnClickListener(this);
            bannerSlider2.setOnClickListener(this);
            bannerSlider3.setOnClickListener(this);
            bannerSlider4.setOnClickListener(this);
            cardOffice = rootview.findViewById(R.id.cardOffice);
            cardOffice.setOnClickListener(this);
            cardDaily = rootview.findViewById(R.id.cardDaily);
            cardDaily.setOnClickListener(this);
            cardEvent = rootview.findViewById(R.id.cardEvent);
            cardEvent.setOnClickListener(this);
            cardLiving = rootview.findViewById(R.id.cardLiving);
            cardLiving.setOnClickListener(this);
            bmb = rootview.findViewById(R.id.bmb);
            final Data_User_Login data_user_login = Data_User_Login.findById(Data_User_Login.class,1L);
   //     bmb.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(menuIcons[i])
                    .imagePadding(new Rect(5,5,5,5))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (index==0){
                                if (data_user_login.Login){
                                    startActivity(new Intent(getActivity(),Profile.class));
                                }else {
                                    startActivity(new Intent(getActivity(),Login.class));
                                }
                            }else if (index==1){
                                startActivity(new Intent(getActivity(),BookingList.class));
                            }else if (index==2){
                               // startActivity(new Intent(Home.this,BookingList.class));
                            }
                        }
                    })
                    .normalText(header[i])
                    .subNormalText(subText[i]);
            bmb.addBuilder(builder);
        }
//            Typeface typeface = ResourcesCompat.getFont(getActivity(), biko_black);
//            custom_switcher = rootview.findViewById(R.id.custom_switcher);
//            custom_switcher.setSize(25);
//            custom_switcher.setTypeface(typeface);
//            custom_switcher.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//            Rotatable rotatable = new Rotatable(Color.parseColor("#F5FF3E"),1000,"Welcome","To","Genius Idea","Coworking Space");
//            rotatable.setSize(25);
//            rotatable.setTypeface(typeface);
//            rotatable.setCenter(true);
//            rotatable.setAnimationDuration(400);

          //  custom_switcher.setContent("?",rotatable);
            getPict();
        }
        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardOffice:
                startActivity(new Intent(getActivity(),Pick_City.class));
               // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardEvent:
                startActivity(new Intent(getActivity(),Booking_Date.class));
                break;
            case R.id.cardDaily:
                startActivity(new Intent(getActivity(),Pick_City.class));
                break;
            case R.id.cardLiving:
                startActivity(new Intent(getActivity(),Pick_City.class));
                break;
            case R.id.lin1:
                startActivity(new Intent(getActivity(),Pick_City.class));
                break;
            case R.id.lin2:
                startActivity(new Intent(getActivity(),Booking_Date.class));
                break;
            case R.id.lin3:
                startActivity(new Intent(getActivity(),Pick_City.class));
                break;
            case R.id.lin4:
                startActivity(new Intent(getActivity(),Pick_City.class));
                break;
        }
    }

    public void getPict(){
        APIService service = APIClient.getClient().create(APIService.class);
//        OkHttpClient httpClient = new OkHttpClient.Builder().build();
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl("https://guruhebat.com/genius/").client(httpClient).build();
        HomeResponse homeResponse = new HomeResponse("load_home");
        Data_User_Login data_user_login = Data_User_Login.findById(Data_User_Login.class,1L);
        Data_User_Token data_user_token = Data_User_Token.findById(Data_User_Token.class,1L);
        String token,userId;
        if (data_user_login.Login){
            token = FirebaseInstanceId.getInstance().getToken();
            userId = data_user_token.token;
        }else {
            token = "-";
            userId = "-";
        }
     //   APIService service1 = retrofit.create(APIService.class);
        Call<ResponseHomeMenu> getResponse = service.getPict("load_home",userId,token);
     //   Toast.makeText(getApplicationContext(),"masuk get",Toast.LENGTH_SHORT).show();
        getResponse.enqueue(new Callback<ResponseHomeMenu>() {
            @Override
            public void onResponse(Call<ResponseHomeMenu> call, Response<ResponseHomeMenu> response) {
                if (response.isSuccessful()){
                 //   Toast.makeText(getApplicationContext(),"berhasil",Toast.LENGTH_SHORT).show();
                    dailyRent = response.body().getDailyRent();
                    officeSpaces = response.body().getOfficeSpaces();
                    eventSpaces = response.body().getEventSpaces();
                    livingSpaces = response.body().getLivingSpaces();
                 //   List<Get_All_Picture_response> data = response.body();
                    DisplayMetrics dm = new DisplayMetrics();
                    WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                    windowManager.getDefaultDisplay().getMetrics(dm);
                    int widthInDP = Math.round(dm.widthPixels / dm.density);

                    int i = widthInDP/16*9;
                    int h = i + 30;

                    final float scale = getActivity().getResources().getDisplayMetrics().density;
                    int pixel1 = (int) (widthInDP * scale +0.5f);
                    int pixel2 = (int) (i * scale +0.5f);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(pixel1, pixel2);
                    if (officeSpaces.size()!=0){
                        url = new String[officeSpaces.size()];
                        kategori = new String[officeSpaces.size()];
                        kota = new String[officeSpaces.size()];
                        List<Banner> banners=new ArrayList<>();
                        for (int ii = 0; ii < officeSpaces.size(); ii++) {
                            url[ii] = ""+officeSpaces.get(ii).getUrl_pict();
                            kategori[ii] = ""+officeSpaces.get(ii).getKategori();
                            kota[ii] = ""+officeSpaces.get(ii).getCity();
                            banners.add(new RemoteBanner(""+url[ii]));
                        }
                            bannerSlider1.setBanners(banners);
                            bannerSlider1.setLayoutParams(layoutParams);
                    }

                    if (eventSpaces.size()!=0){
                        url = new String[eventSpaces.size()];
                        kategori = new String[eventSpaces.size()];
                        kota = new String[eventSpaces.size()];
                        List<Banner> banners=new ArrayList<>();
                        for (int ii = 0; ii < eventSpaces.size(); ii++) {
                            url[ii] = ""+eventSpaces.get(ii).getUrl_pict();
                            kategori[ii] = ""+eventSpaces.get(ii).getKategori();
                            kota[ii] = ""+eventSpaces.get(ii).getCity();
                            banners.add(new RemoteBanner(""+url[ii]));
                        }
                        bannerSlider2.setBanners(banners);
                        bannerSlider2.setLayoutParams(layoutParams);
                    }
                    if (dailyRent.size()!=0){
                        url = new String[dailyRent.size()];
                        kategori = new String[dailyRent.size()];
                        kota = new String[dailyRent.size()];
                        List<Banner> banners=new ArrayList<>();
                        for (int ii = 0; ii < dailyRent.size(); ii++) {
                            url[ii] = ""+dailyRent.get(ii).getUrl_pict();
                            kategori[ii] = ""+dailyRent.get(ii).getKategori();
                            kota[ii] = ""+dailyRent.get(ii).getCity();
                            banners.add(new RemoteBanner(""+url[ii]));
                        }
                        bannerSlider3.setBanners(banners);
                        bannerSlider3.setLayoutParams(layoutParams);
                    }
                    if (livingSpaces.size()!=0){
                        url = new String[livingSpaces.size()];
                        kategori = new String[livingSpaces.size()];
                        kota = new String[livingSpaces.size()];
                        List<Banner> banners=new ArrayList<>();
                        for (int ii = 0; ii < livingSpaces.size(); ii++) {
                            url[ii] = ""+livingSpaces.get(ii).getUrl_pict();
                            kategori[ii] = ""+livingSpaces.get(ii).getKategori();
                            kota[ii] = ""+livingSpaces.get(ii).getCity();
                            banners.add(new RemoteBanner(""+url[ii]));
                        }
                        bannerSlider4.setBanners(banners);
                        bannerSlider4.setLayoutParams(layoutParams);
                    }

                }else {
                  //  Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHomeMenu> call, Throwable t) {
             //   Toast.makeText(getApplicationContext(),""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//      //  super.onBackPressed();
//        if(press==0)
//        {
//            Toast.makeText(getActivity(), "Press again to exit", Toast.LENGTH_SHORT).show();
//            press = 1;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    press = 0;
//                }
//            },3000);
//        }
//        else {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            press = 0;
//        }
//    }
}
