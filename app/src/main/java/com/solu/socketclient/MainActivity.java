package com.solu.socketclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    Toolbar toolbar;
    MyOpenHelper myOpenHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        /*앱바로 적용되는 시점!!*/
        setSupportActionBar(toolbar);
    }

    /*데이터베이스 초기화 및 SQLiteDatabase 객체 얻기*/
    public void init(){
        myOpenHelper =new MyOpenHelper(this);

        /*아래의 메서드가 호출될때, onCreate, onUpgrade
        * 호출된다...*/
        myOpenHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*회원 에니메이션 효과주기*/
    public void setRotate(View view){
        Animation ani= AnimationUtils.loadAnimation(this, R.anim.ani_config);
        view.startAnimation(ani);
    }

    /*메뉴를 선택하면...*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_chat:
                viewPager.setCurrentItem(0);
                ;break;

            case R.id.menu_config:
                viewPager.setCurrentItem(1);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }
}






