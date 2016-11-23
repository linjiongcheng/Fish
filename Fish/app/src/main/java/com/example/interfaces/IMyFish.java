package com.example.interfaces;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface IMyFish {
	public float getMiddle_x();
	public void setMiddle_x(float middle_x);
	public float getMiddle_y();
	public void setMiddle_y(float middle_y);
	public void setMyFish(Bitmap myfish);
	public Bitmap getMyFish();
	public void setScore(int score);
	public int getScore();
	public void setIDIndex(int IDIndex);
	public int getIDIndex();
	public void setDirection(String derection);
	public String getDirection();
}
