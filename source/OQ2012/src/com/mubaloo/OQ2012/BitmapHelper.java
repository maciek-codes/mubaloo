package com.mubaloo.OQ2012;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHelper {
	private static Bitmap bitmap = null;
	private static BitmapHelper bitmapHelper = null;
	private static URL url;
	private BitmapHelper() {
	}
	
	public static Bitmap get(URL imageUrl) throws IOException
	{
		if(bitmapHelper == null)
			bitmapHelper = new BitmapHelper();
		
		if(bitmap != null && !imageUrl.equals(url))
			bitmap.recycle();
		
		url = imageUrl;
		
		if(bitmap == null)
		{
			Bitmap newBitmap = BitmapFactory.decodeStream(
					(InputStream)imageUrl.getContent());
			
			bitmap = newBitmap;
		}
		return bitmap;
	}
}
