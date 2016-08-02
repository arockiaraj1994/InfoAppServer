package com.info.util;

import java.util.UUID;

public class RandomIDGenerationUtil {
	private static RandomIDGenerationUtil instance = null;
	public static RandomIDGenerationUtil getInstance(){
		if(instance == null){
			instance = new RandomIDGenerationUtil();
		}
		return instance;
	}
	public String getId(){
		return UUID.randomUUID().toString();
	}
}
