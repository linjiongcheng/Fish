package com.example.factory;

import android.content.res.Resources;

import com.example.object.BigFish100000;
import com.example.object.GameObject;
import com.example.object.MiddleFish1000;
import com.example.object.MiddleFish10000;
import com.example.object.MyFish;
import com.example.object.SmallFish1;
import com.example.object.SmallFish10;
import com.example.object.SmallFish100;

/*游戏对象的工厂类*/
public class GameObjectFactory {
	//创建分值1的小型鱼的方法
	public GameObject createSmallFish1(Resources resources){
		return new SmallFish1(resources);
	}
	//创建分值10的小型鱼的方法
	public GameObject createSmallFish10(Resources resources){
		return new SmallFish10(resources);
	}
	//创建分值100的小型鱼的方法
	public GameObject createSmallFish100(Resources resources){
		return new SmallFish100(resources);
	}
	//创建分值1000的中型鱼的方法
	public GameObject createMiddleFish1000(Resources resources){
		return new MiddleFish1000(resources);
	}
	//创建分值10000的中型鱼的方法
	public GameObject createMiddleFish10000(Resources resources){
		return new MiddleFish10000(resources);
	}
	//创建分值100000的大型鱼的方法
	public GameObject createBigFish100000(Resources resources){
		return new BigFish100000(resources);
	}
	//创建玩家鱼的方法
	public GameObject createMyFish(Resources resources){
		return new MyFish(resources);
	}
}
