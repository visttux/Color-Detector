package org.ia.practiques;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * This class contain the fragment which download the bitmap
 * 
 * @author Victor Martinez
 * @see Bitmap
 */
public class DownloadFragment extends Fragment {

	private Bitmap loadedImage;
	private String imageURL;
	private DownloadImageTask ImageTask = null;
	private KmeansTask kmeansTask = null;
	private Bitmap bitmap;
	private ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.download_fragment2, container,
				false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);

		Button download_button = (Button) view
				.findViewById(R.id.download_button);

		download_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				EditText URL = (EditText) getView()
						.findViewById(R.id.url_input);
				imageURL = URL.getText().toString();

				ImageTask = new DownloadImageTask();
				// aqui llamaremos al metodo de descarga
				ImageTask.execute(imageURL);

			}

		});

		return view;
	}

	private void setImage() {
		try {
			bitmap = ImageTask.get();
			kmeansTask = new KmeansTask();
			kmeansTask.execute(bitmap);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method generate a DialogFragment with the predominant colours of the
	 * bitmap
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void setDialog() throws InterruptedException, ExecutionException {

		String string = kmeansTask.get();
		DialogFragment id = new ImageDialog(bitmap, string);
		id.show(getFragmentManager(), "image");
	}

	/**
	 * This thread download and fills the bitmap on the background
	 * 
	 * @see AsyncTask
	 */
	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			setImage();

			super.onPostExecute(result);

		}

		// descargar el bitmap en background
		@Override
		protected Bitmap doInBackground(String... imageHttpAddress) {
			URL imageUrl = null;
			try {
				imageUrl = new URL(imageHttpAddress[0]);
				HttpURLConnection conn = (HttpURLConnection) imageUrl
						.openConnection();
				conn.connect();
				loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return loadedImage;
		}

	}

	/**
	 * This thread execute the k-means algorithm on the background
	 * 
	 * @see AsyncTask
	 */
	public class KmeansTask extends AsyncTask<Bitmap, Void, String> {

		private Rgb[] mClusters;

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
				setDialog();
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
