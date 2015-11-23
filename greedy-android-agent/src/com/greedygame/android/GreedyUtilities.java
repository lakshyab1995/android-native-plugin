package com.greedygame.android;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GreedyUtilities {

	public static Bitmap getBitmapByResid(GreedyGameAgent ggAgent, int resid){ 
		if(GreedyGameAgent.AppContext == null){
			return null;
		}
		
	    String resName = GreedyGameAgent.AppContext.getResources().getResourceEntryName(resid);
	    File file = new File(ggAgent.getActivePath() + "/" + resName+".png");
        if(file.exists()){
        	return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
	    
	    return null;
	}
}
