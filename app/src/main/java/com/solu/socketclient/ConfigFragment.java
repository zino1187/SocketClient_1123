package com.solu.socketclient;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/*
 * Fragment 란? 액티비티내의 화면의 일부를 관리하는 객체
 * 따라서 액티비티의 생명주기에 의존적이며,  화면을 관리한다는
 * 점에 있어서는 액티비티와 상당히 유사하므로, 자체적으로
 * 생명주기 메서드가 있다..
 */
public class ConfigFragment extends Fragment{
    String TAG;
    EditText txt_ip, txt_port, txt_nickname;
    ImageView img;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG=this.getClass().getName();
        View view=inflater.inflate(R.layout.configfragment_layout,null);

        txt_ip = (EditText)view.findViewById(R.id.txt_ip);
        txt_port = (EditText)view.findViewById(R.id.txt_port);
        txt_nickname = (EditText)view.findViewById(R.id.txt_nickname);
        img = (ImageView)view.findViewById(R.id.img);

        Log.d(TAG,"onCreateView의 txt_ip  is "+txt_ip);

        return view;
    }

    /* 각 뷰에 알맞는 데이터 넣기*/
    public void initData(Chat chat){
        Log.d(TAG,"setData 메서드로 전달된  chat is "+chat);

        txt_ip.setText(chat.getIp());
        txt_port.setText(chat.getPort());
        txt_nickname.setText(chat.getNickname());
    }
}











