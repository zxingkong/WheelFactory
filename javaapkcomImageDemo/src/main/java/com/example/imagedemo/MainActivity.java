package com.example.imagedemo;

import com.example.cropimage.ChooseDialog;
import com.example.cropimage.CropHelper;
import com.example.utils.OSUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private ImageView headImage;
	private CropHelper mCropHelper;
	private ChooseDialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		headImage=(ImageView)findViewById(R.id.headImage);
		mCropHelper=new CropHelper(this, OSUtils.getSdCardDirectory()+"/head.png");
		mDialog=new ChooseDialog(this, mCropHelper);
		headImage.setOnClickListener(this);

		TextView textView = new TextView(this);
	}
	@Override
	public void onClick(View arg0) {
		mDialog.popSelectDialog();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("onActivityResult", requestCode+"**"+resultCode);
		if(requestCode==RESULT_CANCELED){
			return;
		}else{
			switch (requestCode) {
			case CropHelper.HEAD_FROM_ALBUM:
				mCropHelper.getDataFromAlbum(data);
				Log.e("onActivityResult", "接收到图库图片");
				break;
			case CropHelper.HEAD_FROM_CAMERA:
				mCropHelper.getDataFromCamera(data);
				Log.e("onActivityResult", "接收到拍照图片");
				break;
			case CropHelper.HEAD_SAVE_PHOTO:
				if(data!=null&&data.getParcelableExtra("data")!=null){
					headImage.setImageBitmap((Bitmap)data.getParcelableExtra("data"));
					mCropHelper.savePhoto(data, OSUtils.getSdCardDirectory()+"/myHead.png");
				}
				break;
			default:
				break;
			}
		}
	}
	
	
	
}
