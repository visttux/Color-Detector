package org.ia.practiques;

import java.util.concurrent.ExecutionException;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * This class contain the fragment which get the bitmap from the camera using
 * the intent provided by MediaStore
 * 
 * @author Victor Martinez
 * @see Camera
 */
public class PhotoFragment extends Fragment {

	int CAMERA_PIC_REQUEST = 2;
	int TAKE_PICTURE = 0;
	Camera camera;
	private View view;
	private KmeansTask kmeansTask;
	private Bitmap bitmap;
	private ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.camera_layout, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
		progressBar.setVisibility(View.INVISIBLE);

		Button button = (Button) view.findViewById(R.id.camera_button);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_PIC_REQUEST) {
			bitmap = (Bitmap) data.getExtras().get("data");
			ImageView image = (ImageView) getView().findViewById(
					R.id.imageView1);
			image.setImageBitmap(bitmap);
			kmeansTask = new KmeansTask();
			kmeansTask.execute(bitmap);

		} else {

		}

	}

	public void setText() throws InterruptedException, ExecutionException {

		String string = kmeansTask.get();
		DialogFragment id = new ImageDialog(bitmap, string);
		id.show(getFragmentManager(), "image");

	}

	public class KmeansTask extends AsyncTask<Bitmap, Void, String> {

		private Rgb[] mClusters;

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Bitmap... params) {

			Kmeans k = new Kmeans(2500, 10, params[0]);

			k.initCLusters();
			k.startKmeans();
			mClusters = k.getClusters();
			int[] cnt = k.getCnt();
			ClusterToString cTs = new ClusterToString(mClusters, cnt);
			String string = cTs.getColours();

			return string;
		}

		@Override
		protected void onPostExecute(String result) {

			try {
				setText();
				progressBar.setProgress(0);
				progressBar.setVisibility(View.INVISIBLE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

	}

}
