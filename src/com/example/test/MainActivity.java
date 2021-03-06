package com.example.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.downloadtest.R;
import com.example.test.DownloadUtil.OnDownloadListener;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private ProgressBar mProgressBar;
	private boolean isDelete;
	private Button start;
	private Button pause;
	private Button delete;
	private Button reset;
	private TextView total;

	private long max;

	private DownloadUtil mDownloadUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		start = (Button) findViewById(R.id.button_start);
		pause = (Button) findViewById(R.id.button_pause);
		delete = (Button) findViewById(R.id.button_delete);
		reset = (Button) findViewById(R.id.button_reset);
		total = (TextView) findViewById(R.id.textView_total);
		pause.setEnabled(false);
		delete.setEnabled(false);
		reset.setEnabled(false);
		String urlString = "http://bbra.cn/Uploadfiles/imgs/20110303/fengjin/013.jpg";
		String localPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/local";
		mDownloadUtil = new DownloadUtil(2, localPath, "test.jpg", urlString,
				this);
		mDownloadUtil.setOnDownloadListener(new OnDownloadListener() {

			@Override
			public void downloadStart(long fileSize) {
				// TODO Auto-generated method stub
				Log.w(TAG, "fileSize::" + fileSize);
				max = fileSize;
				mProgressBar.setMax((int) fileSize);
			}

			@Override
			public void downloadProgress(long downloadedSize) {
				// TODO Auto-generated method stub
				Log.w(TAG, "Compelete::" + downloadedSize);
				if(isDelete){
					isDelete = false;
					mProgressBar.setProgress(0);
					total.setText("0%");
				}else{
					mProgressBar.setProgress((int)downloadedSize);
					total.setText(downloadedSize*100 /max+ "%");
				}
				
			}

			@Override
			public void downloadEnd() {
				// TODO Auto-generated method stub
				start.setEnabled(false);
				pause.setEnabled(false);
				delete.setEnabled(true);
				reset.setEnabled(true);
				Log.w(TAG, "ENd");
			}
		});
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDownloadUtil.start();
				start.setEnabled(false);
				start.setText("继续");
				pause.setEnabled(true);
				delete.setEnabled(false);
				reset.setEnabled(false);
			}
		});
		pause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDownloadUtil.pause();
				start.setEnabled(true);
				pause.setEnabled(false);
				delete.setEnabled(true);
				reset.setEnabled(true);
				
				
			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub		
				mDownloadUtil.delete();
				isDelete = true;
				mProgressBar.setProgress(0);
				total.setText("0%");
				start.setEnabled(false);
				pause.setEnabled(false);
				delete.setEnabled(false);
				reset.setEnabled(true);
			}
		});
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mDownloadUtil.reset();
				start.setEnabled(false);
				pause.setEnabled(true);
				delete.setEnabled(false);
				reset.setEnabled(false);
			}
		});
	}

}
