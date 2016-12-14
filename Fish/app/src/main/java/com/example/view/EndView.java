package com.example.view;
import com.example.constant.ConstantUtil;
import com.example.Fish.MainActivity;
import com.example.Fish.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
/*游戏结束时显示的界面*/
public class EndView extends BaseView{
	private int score;
	private float button_x;
	private float button_y;
	private float button_y2;
	private float strwid;
	private float strhei;
	private boolean isBtChange;				// 按钮图片改变的标记
	private boolean isBtChange2;
	private String epilogue;
	private String startGame = "重新挑战";	// 按钮的文字
	private String exitGame = "退出游戏";
	private Bitmap button;					// 按钮图片
	private Bitmap button2;					// 按钮图片
	private Bitmap background;				// 背景图片
	private Rect rect;						// 绘制文字的区域
	private MainActivity mainActivity;
	public EndView(Context context,String epi) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity)context;
		epilogue = epi;
		rect = new Rect();
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
		if(event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1){
			float x = event.getX();
			float y = event.getY();
			//判断第一个按钮是否被按下
			if(x > button_x && x < button_x + button.getWidth()
					&& y > button_y && y < button_y + button.getHeight())
			{
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
			}
			//判断第二个按钮是否被按下
			else if(x > button_x && x < button_x + button.getWidth()
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				isBtChange2 = true;
				drawSelf();
				threadFlag = false;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			float x = event.getX();
			float y = event.getY();
			if(x > button_x && x < button_x + button.getWidth()
					&& y > button_y && y < button_y + button.getHeight())
			{
				isBtChange = true;
			}
			else{
				isBtChange = false;
			}
			if(x > button_x && x < button_x + button.getWidth()
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				isBtChange2 = true;
			}
			else{
				isBtChange2 = false;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
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
		button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		button2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		button_x = screen_width / 2 - button.getWidth() / 2;
		button_y = screen_height / 2 + button.getHeight();
		button_y2 = button_y + button.getHeight() + 40;
		// 返回包围整个字符串的最小的一个Rect区域
		paint.setTextSize(40);
		paint.getTextBounds(startGame, 0, startGame.length(), rect);
		strwid = rect.width();
		strhei = rect.height();
	}
	// 释放图片资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
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
			paint.setTextSize(40);
			//返回包围整个字符串的最小的一个Rect区域
			paint.getTextBounds(startGame, 0, startGame.length(), rect);
			canvas.drawText(startGame, screen_width/2 - strwid/2, button_y + button.getHeight()/2 + strhei/2, paint);
			canvas.drawText(exitGame, screen_width/2 - strwid/2, button_y2 + button.getHeight()/2 + strhei/2, paint);
			paint.setTextSize(60);
			float textlong = paint.measureText("总分:"+String.valueOf(score));
			canvas.drawText("总分:"+String.valueOf(score), screen_width/2 - textlong/2, screen_height/2 - 100, paint);
			Paint paint1 = new Paint();
			paint1.setTextSize(120);
			paint1.setColor(Color.RED);
			float textlong1 = paint.measureText(epilogue);
			canvas.drawText(epilogue, screen_width/2 - textlong1/2 - 40, screen_height/2 - 200, paint1);
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
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - startTime < 400)
					Thread.sleep(400 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}
	public void setScore(int score) {
		this.score = score;
	}
}
