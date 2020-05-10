package com.example.workspacex.ui.workspace.contents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.callbacks.PhotosCallback;
import com.example.workspacex.data.model.Workspace;
import com.example.workspacex.endpoints.Photos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {
    Workspace workspace;

    public PhotosFragment(Workspace ws) {
        workspace = ws;
    }

    private void displayPhotos(final LinearLayout linearLayout, final Context context) throws JSONException {
        Photos.getWorkspacePhotos(workspace.getId(), new PhotosCallback() {
            @Override
            public void onSuccess(JSONArray result, JSONArray images) {
                System.out.println(result.length());
                System.out.println(images.length());
                for (int i = 0; i < images.length(); i++){
                    ImageView imageView = new ImageView(context);
                    TextView caption = new TextView(context);
                    try {
                        JSONObject jsonObject = images.getJSONObject(i);
                        String curImg = jsonObject.getString("img");
                        curImg = curImg.substring("data:image/png;base64,".length());
                        byte[] decodedString = Base64.decode(curImg, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);
                        linearLayout.addView(imageView);
                        if (i < result.length()) {
                            String captionValue = ((JSONObject)(result.get(i))).getString("description");
                            caption.setText(captionValue);
                            caption.setGravity(Gravity.CENTER);
                            linearLayout.addView(caption);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_photos, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.photos_content_ll);
        try {
            displayPhotos(linearLayout, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}
