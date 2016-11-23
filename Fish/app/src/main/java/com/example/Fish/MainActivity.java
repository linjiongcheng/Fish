package com.example.Fish;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.constant.ConstantUtil;
import com.example.view.EndView;
import com.example.view.MainView;
import com.example.view.ReadyView;

public class MainActivity extends AppCompatActivity {
    private EndView endView;
    private MainView mainView;
    private ReadyView readyView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == ConstantUtil.TO_MAIN_VIEW){
                toMainView();
            }
            else  if(msg.what == ConstantUtil.TO_END_VIEW){
                toEndView(msg.arg1);
            }
            else  if(msg.what == ConstantUtil.END_GAME){
                endGame();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);         //强制为横屏
        readyView = new ReadyView(this);
        setContentView(readyView);

    }
    //显示游戏的主界面
    public void toMainView(){
        if(mainView == null){
            mainView = new MainView(this);
        }
        setContentView(mainView);
        readyView = null;
        endView = null;
    }
    //显示游戏结束的界面
    public void toEndView(int score){
        if(endView == null){
            endView = new EndView(this);
            endView.setScore(score);
        }
        setContentView(endView);
        mainView = null;
    }
    //结束游戏
    public void endGame(){
        if(readyView != null){
            readyView.setThreadFlag(false);
        }
        else if(mainView != null){
            mainView.setThreadFlag(false);
        }
        else if(endView != null){
            endView.setThreadFlag(false);
        }
        this.finish();
    }
    //getter和setter方法
    public Handler getHandler() {
        return handler;
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
