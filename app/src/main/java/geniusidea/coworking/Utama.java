package geniusidea.coworking;

import android.content.Intent;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Utama extends AppCompatActivity implements View.OnClickListener{

    TabLayout tabLayout;
    ViewPager container;
    Fragment fragmentNow;
    private int[] tabIcons = {
            R.drawable.ic_home_orange,
            R.drawable.ic_news_orange,
            R.drawable.ic_book_black_24dp,
            R.drawable.ic_profile_orange
    };
    int press = 0;
    private int[] tabIcons2 = {
            R.drawable.ic_home_white,
            R.drawable.ic_news_white,
            R.drawable.ic_book_white,
            R.drawable.ic_profile_white
    };
    View v1,v2,v3,v4, v5;
    TextView text1,text2,text3,text4;
    ImageView img1,img2,img3,img4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        container = findViewById(R.id.viewpager);
        setupViewPager(container);
//        tabLayout.addTab(tabLayout.newTab());
//        tabLayout.addTab(tabLayout.newTab());
//        tabLayout.addTab(tabLayout.newTab());
//        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setupWithViewPager(container);
        tabLayout.setSelectedTabIndicatorHeight(0);

        setupViewTab();
//        replaceFragment(new News_());
      //  fragmentNow = new News_();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
//                    replaceFragment(new Home());
                    fragmentNow = new Home();
                    img1.setBackgroundResource(tabIcons[0]);
                    text1.setTextColor(getResources().getColor(R.color.orange));
                }else if(tab.getPosition()==1){
//                    replaceFragment(new News_());
                    fragmentNow = new News_();
                    img2.setBackgroundResource(tabIcons[1]);
                    text2.setTextColor(getResources().getColor(R.color.orange));
                }else if(tab.getPosition()==2){
//                    replaceFragment(new BookingList());
                    fragmentNow = new BookingList();
                    img3.setBackgroundResource(tabIcons[2]);
                    text3.setTextColor(getResources().getColor(R.color.orange));
                }else if(tab.getPosition()==3){
//                    replaceFragment(new Profile());
                    fragmentNow = new Profile();
                    img4.setBackgroundResource(tabIcons[3]);
                    text4.setTextColor(getResources().getColor(R.color.orange));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    //     replaceFragment(new Home());
                    img1.setBackgroundResource(tabIcons2[0]);
                    text1.setTextColor(getResources().getColor(R.color.white));
                }else if(tab.getPosition()==1){
                    //   replaceFragment(new News_());
                    img2.setBackgroundResource(tabIcons2[1]);
                    text2.setTextColor(getResources().getColor(R.color.white));
                }else if(tab.getPosition()==2){
                    //  replaceFragment(new BookingList());
                    img3.setBackgroundResource(tabIcons2[2]);
                    text3.setTextColor(getResources().getColor(R.color.white));
                }else if(tab.getPosition()==3){
                    //  replaceFragment(new Profile());
                    img4.setBackgroundResource(tabIcons2[3]);
                    text4.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public void setupViewTab(){

        v1 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img1 = (ImageView) v1.findViewById(R.id.icontab);
        img1.setBackgroundResource(tabIcons[0]);
        text1 = (TextView) v1.findViewById(R.id.texttab);
        text1.setText("Home");
        text1.setTextColor(getResources().getColor(R.color.orange));

        tabLayout.getTabAt(0).setCustomView(v1);

        v2 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img2 = (ImageView) v2.findViewById(R.id.icontab);
        img2.setBackgroundResource(tabIcons2[1]);
        text2 = (TextView) v2.findViewById(R.id.texttab);
        text2.setText("News");
//        text2.setTypeface(custom_font);
        text2.setTextColor(getResources().getColor(R.color.white));
        tabLayout.getTabAt(1).setCustomView(v2);

        v3 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img3 = (ImageView) v3.findViewById(R.id.icontab);
        img3.setBackgroundResource(tabIcons2[2]);
        text3 = (TextView) v3.findViewById(R.id.texttab);
        text3.setText("Book");
//        text3.setTypeface(custom_font);
        text3.setTextColor(getResources().getColor(R.color.white));
        tabLayout.getTabAt(2).setCustomView(v3);

        v4 = getLayoutInflater().inflate(R.layout.custome_tab, null);
        img4 = (ImageView) v4.findViewById(R.id.icontab);
        img4.setBackgroundResource(tabIcons2[3]);
        text4 = (TextView) v4.findViewById(R.id.texttab);
        text4.setText("Account");
//        text4.setTypeface(custom_font);
        text4.setTextColor(getResources().getColor(R.color.white));
        tabLayout.getTabAt(3).setCustomView(v4);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = (FragmentTransaction) fragmentManager.beginTransaction();
        transaction.replace(R.id.viewpager,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(press==0)
        {
            Fragment frg = fragmentNow;
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
            Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
            press = 1;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    press = 0;
                }
            },3000);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            press = 0;
        }
    }

    @Override
    public void onClick(View view) {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Home(), "");
        adapter.addFragment(new News_(), "");
        adapter.addFragment(new BookingList(), "");
        adapter.addFragment(new Profile(), "");
//        adapter.addFragment(new My_Account_Menu(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }
}
