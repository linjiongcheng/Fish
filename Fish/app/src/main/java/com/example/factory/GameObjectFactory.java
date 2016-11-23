package com.example.factory;

import android.content.res.Resources;

import com.example.object.BigFish;
import com.example.object.GameObject;
import com.example.object.MiddleFish10000;
import com.example.object.MiddleFish12000;
import com.example.object.MyFish;
import com.example.object.SmallFish100;
import com.example.object.SmallFish1000;
import com.example.object.SmallFish1200;

/*游戏对象的工厂类*/
public class GameObjectFactory {
	//创建分值100的小型鱼的方法
	public GameObject createSmallFish100(Resources resources){
		return new SmallFish100(resources);
	}
	//创建分值1000的小型鱼的方法
	public GameObject createSmallFish1000(Resources resources){
		return new SmallFish1000(resources);
	}
	//创建分值1200的小型鱼的方法
	public GameObject createSmallFish1200(Resources resources){
		return new SmallFish1200(resources);
	}
	//创建分值10000的中型鱼的方法
	public GameObject createMiddleFish10000(Resources resources){
		return new MiddleFish10000(resources);
	}
	//创建分值12000的中型鱼的方法
	public GameObject createMiddleFish12000(Resources resources){
		return new MiddleFish12000(resources);
	}
	//创建大型鱼的方法
	public GameObject createBigFish(Resources resources){
		return new BigFish(resources);
	}
	//创建玩家鱼的方法
	public GameObject createMyFish(Resources resources){
		return new MyFish(resources);
	}
}
