package com.example.object;

import android.content.res.Resources;
import android.graphics.Canvas;
/*其他鱼类*/
public class EnemyFish extends GameObject{
	protected int score;						 // 对象的分值
	protected boolean isVisible;		 		 //	 对象是否为可见状态
	public EnemyFish(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();			// 初始化图片资源
	}
	//初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){

	}
	// 初始化图片资源
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub

	}
	// 对象的绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub

	}
	// 释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub

	}
	// 对象的逻辑函数
	@Override
	public void logic() {
		// TODO Auto-generated method stub
		if (object_x < screen_width) {
			object_x += speed;
		}
		else {
			isAlive = false;
		}
		if(object_x + object_width > 0){
			isVisible = true;
		}
		else{
			isVisible = false;
		}
	}
	// 检测碰撞
	@Override
	public boolean isCollide(GameObject obj) {
		// 矩形1位于矩形2的左侧
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x()) {
			return false;
		}
		// 矩形1位于矩形2的右侧
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}
		// 矩形1位于矩形2的上方
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y()) {
			return false;
		}
		// 矩形1位于矩形2的下方
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
			return false;
		}
		return true;
	}
	// 判断能否被检测碰撞
	public boolean isCanCollide() {
		// TODO Auto-generated method stub
		return isAlive && isVisible;
	}
	//getter和setter方法
	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}
}

