package com.example.object;

import java.util.Random;
import com.example.Fish.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*分值100000大型鱼的类*/
public class BigFish100000 extends EnemyFish{
	private static int currentCount = 0;	 //	对象当前的数量
	public static int sumCount = 1;	 	 	 //	对象总的数量
	private Bitmap bigFish; // 对象图片
	public BigFish100000(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 100000;		// 为对象设置分数
	}
	//初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		Random ran = new Random();
		speed = ran.nextInt(6) + 4 * arg0;
		object_y = ran.nextInt((int)(screen_height - object_height));
		object_x = -object_width * (currentCount*2 + 2);
		currentCount++;
		if(currentCount >= sumCount){
			currentCount = 0;
		}
	}
	// 初始化图片资源
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bigFish = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.big), 260, 130, true);
		object_width = bigFish.getWidth();			//获得每一帧位图的宽
		object_height = bigFish.getHeight();		//获得每一帧位图的高
	}
	// 对象的绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			if(isVisible){
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(bigFish, object_x, object_y,paint);
				canvas.restore();
			}
			logic();
		}
	}
	// 释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bigFish.isRecycled()){
			bigFish.recycle();
		}
	}
}
