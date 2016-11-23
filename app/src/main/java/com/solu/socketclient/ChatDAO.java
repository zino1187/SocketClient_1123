package com.solu.socketclient;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ChatDAO {
    SQLiteDatabase db;

    public ChatDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(){
    }

    public Chat select(int chat_id){
        Cursor rs=db.rawQuery("select * from chat", null);
        Chat chat=null;
        if(rs.moveToNext()){//레코드가 있다면...
            chat = new Chat();
            chat.setChat_id(rs.getInt(rs.getColumnIndex("chat_id")));
            chat.setIp(rs.getString(rs.getColumnIndex("ip")));
            chat.setPort(rs.getInt(rs.getColumnIndex("port")));
            chat.setNickname(rs.getString(rs.getColumnIndex("nickname")));
            chat.setImg(rs.getString(rs.getColumnIndex("img")));
        }
        return chat;
    }

    public void update(Chat chat){

    }
    public void delete(int chat_id){

    }
}
















