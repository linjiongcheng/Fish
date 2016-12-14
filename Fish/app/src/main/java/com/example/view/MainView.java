package com.example.view;

import com.example.constant.ConstantUtil;
import com.example.factory.GameObjectFactory;
import com.example.Fish.R;
import com.example.object.BigFish100000;
import com.example.object.EnemyFish;
import com.example.object.GameObject;
import com.example.object.MiddleFish1000;
import com.example.object.MiddleFish10000;
import com.example.object.MyFish;
import com.example.object.SmallFish1;
import com.example.object.SmallFish10;
import com.example.object.SmallFish100;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

/*游戏进行的主界面*/
public class MainView extends BaseView {
	private int sumScore;            // 游戏总得分
	private int speedTime;            // 游戏速度的倍数
	private float bg_y;                // 图片的坐标
	private float play_bt_w;
	private float play_bt_h;
	private boolean isPlay;            // 标记游戏运行状态
	private boolean isTouchFish;    // 判断玩家是否按下屏幕
	private Bitmap background;        // 背景图片
	private Bitmap playButton;        // 开始/暂停游戏的按钮图片
	private MyFish myFish;            // 玩家的鱼
	private List<EnemyFish> enemyFishs;
	private GameObjectFactory factory;

	public MainView(Context context,int speed) {
		super(context);
		// TODO Auto-generated constructor stub
		isPlay = true;
		speedTime = speed;
		factory = new GameObjectFactory();                          //工厂类
		enemyFishs = new ArrayList<EnemyFish>();
		myFish = (MyFish) factory.createMyFish(getResources());//生产玩家的鱼
		myFish.setMainView(this);
		for (int i = 0; i < SmallFish1.sumCount; i++) {
			//生产分值1的小型鱼
			SmallFish1 smallFish1 = (SmallFish1) factory.createSmallFish1(getResources());
			enemyFishs.add(smallFish1);
		}
		for (int i = 0; i < SmallFish10.sumCount; i++) {
			//生产分值10的小型鱼
			SmallFish10 smallFish10 = (SmallFish10) factory.createSmallFish10(getResources());
			enemyFishs.add(smallFish10);
		}
		for (int i = 0; i < SmallFish100.sumCount; i++) {
			//生产分值100的小型鱼
			SmallFish100 smallFish100 = (SmallFish100) factory.createSmallFish100(getResources());
			enemyFishs.add(smallFish100);
		}
		for (int i = 0; i < MiddleFish1000.sumCount; i++) {
			//生产分值1000的中型鱼
			MiddleFish1000 middleFish1000 = (MiddleFish1000) factory.createMiddleFish1000(getResources());
			enemyFishs.add(middleFish1000);
		}
		for (int i = 0; i < MiddleFish10000.sumCount; i++) {
			//生产分值10000的中型鱼
			MiddleFish10000 middleFish10000 = (MiddleFish10000) factory.createMiddleFish10000(getResources());
			enemyFishs.add(middleFish10000);
		}
		for (int i = 0; i < BigFish100000.sumCount; i++) {
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
		initBitmap(); // 初始化图片资源
		for (GameObject obj : enemyFishs) {
			obj.setScreenWH(screen_width, screen_height);
		}
		myFish.setScreenWH(screen_width, screen_height);
		myFish.setAlive(true);
		if (thread.isAlive()) {
			thread.start();
		} else {
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
		if (event.getAction() == MotionEvent.ACTION_UP) {
			isTouchFish = false;
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			if (x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h) {
				if (isPlay) {
					isPlay = false;
				} else {
					isPlay = true;
					synchronized (thread) {
						thread.notify();
					}
				}
				return true;
			}
			//判断玩家鱼是否被按下
			else if (x > myFish.getObject_x() && x < myFish.getObject_x() + myFish.getObject_width()
					&& y > myFish.getObject_y() && y < myFish.getObject_y() + myFish.getObject_height()) {
				if (isPlay) {
					isTouchFish = true;
				}
				return true;
			}
		}
		//响应手指在屏幕移动的事件
		else if (event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1) {
			//判断触摸点是否为玩家的鱼
			if (isTouchFish) {
				float x = event.getX();
				float y = event.getY();
				if (x > myFish.getMiddle_x() + 20) {
					myFish.setDirection("right");
					int height = myFish.getMyFish().getHeight();
					int width = myFish.getMyFish().getWidth();
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.myfish_right), width, height, true));
					if (myFish.getMiddle_x() + myFish.getSpeed() <= screen_width) {
						myFish.setMiddle_x(myFish.getMiddle_x() + myFish.getSpeed());
					}
				} else if (x < myFish.getMiddle_x() - 20) {
					myFish.setDirection("left");
					int height = myFish.getMyFish().getHeight();
					int width = myFish.getMyFish().getWidth();
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.myfish_left), width, height, true));
//					myFish.setMyFish(BitmapFactory.decodeResource(getResources(),left[myFish.getIDIndex()]));
					if (myFish.getMiddle_x() - myFish.getSpeed() >= 0) {
						myFish.setMiddle_x(myFish.getMiddle_x() - myFish.getSpeed());
					}
				}
				if (y > myFish.getMiddle_y() + 20) {
					if (myFish.getMiddle_y() + myFish.getSpeed() <= screen_height) {
						myFish.setMiddle_y(myFish.getMiddle_y() + myFish.getSpeed());
					}
				} else if (y < myFish.getMiddle_y() - 20) {
					if (myFish.getMiddle_y() - myFish.getSpeed() >= 0) {
						myFish.setMiddle_y(myFish.getMiddle_y() - myFish.getSpeed());
					}
				}
				return true;
			}
		}
		return false;
	}

	// 初始化图片资源方法
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		playButton = BitmapFactory.decodeResource(getResources(), R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.ocean);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		play_bt_w = playButton.getWidth();
		play_bt_h = playButton.getHeight() / 2;
		bg_y = 0;
	}

	//初始化游戏对象
	public void initObject() {
		for (EnemyFish obj : enemyFishs) {
			if (!obj.isAlive()) {
				obj.initial(speedTime, 0, 0);
				break;
			}
		}
	}

	// 释放图片资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for (GameObject obj : enemyFishs) {
			obj.release();
		}
		myFish.release();
		if (!playButton.isRecycled()) {
			playButton.recycle();
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
			canvas.drawColor(Color.BLACK); // 绘制背景色
			canvas.save();
			// 计算背景图片与屏幕的比例
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, bg_y, paint);   // 绘制背景图
			canvas.restore();
			//绘制按钮
			canvas.save();
			canvas.clipRect(10, 10, 10 + play_bt_w, 10 + play_bt_h);
			if (isPlay) {
				canvas.drawBitmap(playButton, 10, 10, paint);
			} else {
				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
			}
			canvas.restore();
			//绘制其他鱼
			for (EnemyFish obj : enemyFishs) {
				if (obj.isAlive()) {
					obj.drawSelf(canvas);
					//检测其他鱼是否与玩家的鱼碰撞
					if (obj.isCanCollide() && myFish.isAlive()) {
						if (obj.isCollide(myFish)) {
							if (obj.getScore() > myFish.getScore() + 1) {
								myFish.setAlive(false);
							} else {
								setBitmap(myFish.getScore(), obj.getScore());
								myFish.setScore(myFish.getScore() + obj.getScore());
								sumScore += obj.getScore();
								obj.setAlive(false);
							}
						}
					}
				}
			}
			if (!myFish.isAlive()) {
				threadFlag = false;
			}
			myFish.drawSelf(canvas);    //绘制玩家的鱼
			//绘制积分文字
			paint.setTextSize(30);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText("积分:" + String.valueOf(sumScore), 30 + play_bt_w, 40, paint);        //绘制文字
			canvas.drawText("等级 X " + String.valueOf(speedTime), screen_width - 150, 40, paint); //绘制文字
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void setBitmap(int score, int addScore) {
		int height = myFish.getMyFish().getHeight();
		int width = myFish.getMyFish().getWidth();

		if (myFish.getDirection().equals("left")) {
			if ((addScore == 1 && score < 10) || (addScore == 10 && score < 100 && score >= 10)
					|| (addScore == 100 && score < 1000 && score >= 100) || (addScore == 1000 && score < 10000 && score >= 1000)
					|| (addScore == 10000 && score < 100000 && score >= 10000)) {
				myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.myfish_left), width + 4, height + 2, true));
			} else if ((score < 100 && score >= 10) && width <= 106) {
				myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.myfish_left), 106, 53, true));
			} else if ((score < 1000 && score >= 100) && width <= 146) {
				myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.myfish_left), 146, 73, true));
			} else if ((score < 10000 && score >= 1000) && width <= 186) {
				myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.myfish_left), 186, 93, true));
			} else if ((score < 100000 && score >= 10000) && width <= 226) {
				myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.myfish_left), 226, 113, true));
			} else if (myFish.getDirection().equals("right")) {
				if ((addScore == 1 && score < 10) || (addScore == 10 && score < 100 && score >= 10)
						|| (addScore == 100 && score < 1000 && score >= 100) || (addScore == 1000 && score < 10000 && score >= 1000)
						|| (addScore == 10000 && score < 100000 && score >= 10000)) {
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.myfish_right), width + 4, height + 2, true));
				} else if ((score < 100 && score >= 10) && width <= 106) {
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.myfish_right), 106, 53, true));
				} else if ((score < 1000 && score >= 100) && width <= 146) {
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.myfish_right), 146, 73, true));
				} else if ((score < 10000 && score >= 1000) && width <= 186) {
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.myfish_right), 186, 93, true));
				} else if ((score < 100000 && score >= 10000) && width <= 226) {
					myFish.setMyFish(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
							R.drawable.myfish_right), 226, 113, true));
				}
			}
			myFish.initBitmap();
			myFish.drawSelf(canvas);
		}
	}
	// 线程运行的方法
	@Override
	public void run () {
		// TODO Auto-generated method stub
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
//			initID();
			initObject();
			drawSelf();
			if (sumScore >= 100000) {
				threadFlag = false;
			}
			long endTime = System.currentTimeMillis();
			if (!isPlay) {
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message = new Message();
		message.what = ConstantUtil.TO_END_VIEW;
		message.arg1 = Integer.valueOf(sumScore);
		message.obj = "失败";
		if (sumScore >= 100000) {
			message.obj = "你赢了";
		}
		mainActivity.getHandler().sendMessage(message);
	}
}
