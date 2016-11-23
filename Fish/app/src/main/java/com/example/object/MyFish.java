package com.example.object;

import com.example.factory.GameObjectFactory;
import com.example.interfaces.IMyFish;
import com.example.Fish.R;
import com.example.view.MainView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*玩家鱼的类*/
public class MyFish extends GameObject implements IMyFish{
	private int score;
	private float middle_x;			 // 鱼的中心坐标
	private float middle_y;
	private long startTime;	 	 	 // 开始的时间
	private long endTime;	 	 	 // 结束的时间
	private Bitmap myfish;			 // 鱼的图片
	private MainView mainView;
	private GameObjectFactory factory;
	private int IDIndex;
	private String direction;
	public MyFish(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		myfish = BitmapFactory.decodeResource(resources, R.drawable.myfish_left);
		initBitmap();
		this.score = 0;
		this.speed = 20;
		this.IDIndex = 0;
		this.direction = "left";
	}
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	// 设置屏幕宽度和高度
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width/2;
		middle_y = object_y + object_height/2;
	}
	// 初始化图片资源的
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		object_width = myfish.getWidth(); 		// 获得每一帧位图的宽
		object_height = myfish.getHeight(); 	// 获得每一帧位图的高
	}
	// 对象的绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myfish, object_x, object_y, paint);
			canvas.restore();
		}
	}
	// 释放资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!myfish.isRecycled()){
			myfish.recycle();
		}
	}
	//getter和setter方法
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Override
	public float getMiddle_x() {
		return middle_x;
	}
	@Override
	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}
	@Override
	public float getMiddle_y() {
		return middle_y;
	}
	@Override
	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}
	@Override
	public void setMyFish(Bitmap myfish){
		this.myfish = myfish;
	}
	@Override
	public Bitmap getMyFish(){
		return this.myfish;
	}
	@Override
	public void setScore(int score){
		this.score = score;
	}
	@Override
	public int getScore(){
		return this.score;
	}
	@Override
	public int getIDIndex(){
		return this.IDIndex;
	}
	@Override
	public void setIDIndex(int IDIndex){
		this.IDIndex = IDIndex;
	}
	@Override
	public void setDirection(String direction){
		this.direction = direction;
	}
	@Override
	public String getDirection(){
		return this.direction;
	}
}
