package com.example.view;

import com.example.constant.ConstantUtil;
import com.example.Fish.R;
import com.example.factory.GameObjectFactory;
import com.example.object.BigFish100000;
import com.example.object.EnemyFish;
import com.example.object.GameObject;
import com.example.object.MiddleFish1000;
import com.example.object.MiddleFish10000;
import com.example.object.SmallFish1;
import com.example.object.SmallFish10;
import com.example.object.SmallFish100;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/*游戏开始前的界面类*/
public class ReadyView extends BaseView{
	private float text_x;
	private float text_y;
	private float button_x;
	private float button_y;
	private float button_y2;
	private float strwid;
	private float strhei;
	private boolean isBtChange;				// 按钮图片改变的标记
	private boolean isBtChange2;
	private String startGame = "开始游戏";	// 按钮的文字
	private String exitGame = "退出游戏";
	private Bitmap text;					// 文字图片
	private Bitmap button;					// 按钮图片
	private Bitmap button2;					// 按钮图片
	private Bitmap background;				// 背景图片
	private Rect rect;						// 绘制文字的区域
	private GameObjectFactory factory;
	private List<EnemyFish> enemyFishs;
	public ReadyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint.setTextSize(40);
		rect = new Rect();
		factory = new GameObjectFactory();						  //工厂类
		enemyFishs = new ArrayList<EnemyFish>();
		for(int i = 0; i < SmallFish1.sumCount; i++){
			//生产分值1的小型鱼
			SmallFish1 smallFish1 = (SmallFish1) factory.createSmallFish1(getResources());
			enemyFishs.add(smallFish1);
		}
		for(int i = 0; i < SmallFish10.sumCount; i++){
			//生产分值10的小型鱼
			SmallFish10 smallFish10 = (SmallFish10) factory.createSmallFish10(getResources());
			enemyFishs.add(smallFish10);
		}
		for(int i = 0; i < SmallFish100.sumCount; i++){
			//生产分值100的小型鱼
			SmallFish100 smallFish100 = (SmallFish100) factory.createSmallFish100(getResources());
			enemyFishs.add(smallFish100);
		}
		for(int i = 0; i < MiddleFish1000.sumCount; i++){
			//生产分值1000的中型鱼
			MiddleFish1000 middleFish1000 = (MiddleFish1000) factory.createMiddleFish1000(getResources());
			enemyFishs.add(middleFish1000);
		}
		for(int i = 0; i < MiddleFish10000.sumCount; i++){
			//生产分值10000的中型鱼
			MiddleFish10000 middleFish10000 = (MiddleFish10000) factory.createMiddleFish10000(getResources());
			enemyFishs.add(middleFish10000);
		}
		for(int i = 0; i < BigFish100000.sumCount; i++){
			//生产分值100000大型鱼
			BigFish100000 bigFish100000 = (BigFish100000) factory.createBigFish100000(getResources());
			enemyFishs.add(bigFish100000);
		}
		thread = new Thread(this);
	}
	// 视图改变的方法
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	// 视图创建的方法
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceCreated(arg0);
		initBitmap();
		for(GameObject obj:enemyFishs){
			obj.setScreenWH(screen_width,screen_height);
		}
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
	}
	// 视图销毁的方法
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}
	// 响应触屏事件的方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getPointerCount() == 1) {
			float x = event.getX();
			float y = event.getY();
			//判断第一个按钮是否被按下
			if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y && y < button_y + button.getHeight()) {
				isBtChange = true;
				drawSelf();
				final String[] items = new String[]{"等级1","等级2","等级3","等级4","等级5"};
				AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
				builder.setTitle("请选择难度等级：");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Message message = new Message();
						message.what = ConstantUtil.TO_MAIN_VIEW;
						message.arg1 = Integer.valueOf(which+1);
						mainActivity.getHandler().sendMessage(message);
					}
				});
				builder.create().show();
				//mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
			}
			//判断第二个按钮是否被按下
			else if (x > button_x && x < button_x + button.getWidth()
					&& y > button_y2 && y < button_y2 + button.getHeight()) {
				isBtChange2 = true;
				drawSelf();
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
			}
			return true;
		}
		//响应手指离开屏幕的消息
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			isBtChange = false;
			isBtChange2 = false;
			return true;
		}
		return false;
	}
	// 初始化图片资源方法
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		background = BitmapFactory.decodeResource(getResources(),R.drawable.ocean);
		text = BitmapFactory.decodeResource(getResources(), R.drawable.text);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		button2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		text_x = screen_width / 2 - text.getWidth() / 2;
		text_y = screen_height / 2 - text.getHeight();
		button_x = screen_width / 2 - button.getWidth() / 2;
		button_y = screen_height / 2 + button.getHeight();
		button_y2 = button_y + button.getHeight() + 40;
		// 返回包围整个字符串的最小的一个Rect区域
		paint.getTextBounds(startGame, 0, startGame.length(), rect);
		strwid = rect.width();
		strhei = rect.height();
	}
	// 释放图片资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(GameObject obj:enemyFishs){
			obj.release();
		}
		if (!text.isRecycled()) {
			text.recycle();
		}
		if (!button.isRecycled()) {
			button.recycle();
		}
		if (!button2.isRecycled()) {
			button2.recycle();
		}
		if (!background.isRecycled()) {
			background.recycle();
		}
	}
	//初始化游戏对象
	public void initObject(){
		for(EnemyFish obj:enemyFishs){
			if(!obj.isAlive()){
				obj.initial(1,0,0);
				break;
			}
		}
	}
	// 绘图方法
	@Override
	public void drawSelf() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); 						// 绘制背景色
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0);					// 计算背景图片与屏幕的比例
			canvas.drawBitmap(background, 0, 0, paint); 		// 绘制背景图
			canvas.restore();
			canvas.save();
			canvas.drawBitmap(text, text_x, text_y, paint);		// 绘制文字图片
			//当手指滑过按钮时变换图片
			if (isBtChange) {
				canvas.drawBitmap(button2, button_x, button_y, paint);
			}
			else {
				canvas.drawBitmap(button, button_x, button_y, paint);
			}
			if (isBtChange2) {
				canvas.drawBitmap(button2, button_x, button_y2, paint);
			}
			else {
				canvas.drawBitmap(button, button_x, button_y2, paint);
			}
			//开始游戏的按钮
			canvas.drawText(startGame, screen_width / 2 - strwid / 2, button_y
					+ button.getHeight() / 2 + strhei / 2, paint);
			//退出游戏的按钮
			canvas.drawText(exitGame, screen_width / 2 - strwid / 2, button_y2
					+ button.getHeight() / 2 + strhei / 2, paint);
			canvas.restore();
			//绘制其他鱼
			for(EnemyFish obj:enemyFishs) {
				if (obj.isAlive()) {
					obj.drawSelf(canvas);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	// 线程运行的方法
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}
}
