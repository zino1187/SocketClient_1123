package com.solu.socketclient;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    String TAG;
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    Toolbar toolbar;
    MyOpenHelper myOpenHelper;
    ChatDAO chatDAO;
    Chat chat;

    /*안드로이드는 자바 se api 지원한다*/
    Socket socket;
    String ip;
    int port;
    Thread connectThread;/*접속용*/
    ClientThread clientThread;/*대화용*/
    MainActivity mainActivity;
    Handler handler;
    ChatFragment chatFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getName();
        mainActivity=this;

        Log.d(TAG, "main "+this);

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        viewPager.addOnPageChangeListener(this);

        /*앱바로 적용되는 시점!!*/
        setSupportActionBar(toolbar);
        init();

        chatFragment=(ChatFragment) myPagerAdapter.getItem(0);

        /*핸들러 재정의*/
        handler = new Handler(){
            public void handleMessage(Message message) {
                /*프레그먼트의 대화내역창에 서버의 메세지 출력!!*/
                String msg=message.getData().getString("msg");
                chatFragment.txt_receive.append(msg+"\n");
            }
        };

    }

    /*데이터베이스 초기화 및 SQLiteDatabase 객체 얻기*/
    public void init(){
        myOpenHelper =new MyOpenHelper(this);

        /*아래의 메서드가 호출될때, onCreate, onUpgrade
        * 호출된다...*/
        SQLiteDatabase db=myOpenHelper.getWritableDatabase();

        chatDAO = new ChatDAO(db);
        chat=chatDAO.select(0);
        Log.d(TAG, "Activity의 chat is "+chat);

        /*접속 관련 정보 세팅*/
        ip=chat.getIp();
        port=Integer.parseInt(chat.getPort());

    }


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
            case R.id.menu_connect:
                /*접속시도!!*/
                connectServer();
                ;break;

            case R.id.menu_chat:
                viewPager.setCurrentItem(0);
                ;break;

            case R.id.menu_config:
                viewPager.setCurrentItem(1);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*페이지 관련 이벤트 */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /*사용자가 해당 페이지를 확정 지을때 ( commit시)*/
    public void onPageSelected(int position) {
        if(position==1) { //설정 화면일때만...
            ConfigFragment configFragment=(ConfigFragment) myPagerAdapter.fragments[position];
            configFragment.initData(chat);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /*--------------------------------------------------------
     SQLite에 등록된 정보대로,  소켓 서버에 접속하자!!
    --------------------------------------------------------*/
    public void connectServer(){
        /*socket 메모리에 올리는 행위 = 접속시도!!*/
        connectThread= new Thread(){
            public void run() {
                try {
                    socket = new Socket(ip, port);

                    clientThread= new ClientThread(mainActivity, socket);
                    clientThread.start(); //작동시작!!
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connectThread.start();
    }

}






