package org.ia.practiques;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class generates a DialogFragment filled with a bitmap and a string
 * 
 * @author Victor Martinez
 */
public class ImageDialog  extends DialogFragment {

	
	private Bitmap mBitmap;
	private String mString;
	
	public ImageDialog (Bitmap bitmap, String string) {
		mBitmap = bitmap;
		mString = string;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.image_dialog, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.image);
        iv.setImageBitmap(mBitmap);
        
        
        TextView tv = (TextView) layout.findViewById(R.id.color);
        tv.setText(mString);
        
        
        builder.setView(layout);  
        builder.setMessage("")
               .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
}
