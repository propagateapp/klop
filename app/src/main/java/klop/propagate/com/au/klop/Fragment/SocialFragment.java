package klop.propagate.com.au.klop.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import klop.propagate.com.au.klop.Adapter.GridInstagramAdapter;
import klop.propagate.com.au.klop.Model.JSONParser;
import klop.propagate.com.au.klop.R;

public class SocialFragment extends Fragment {
    private ImageView btnShare;
    private GridView gvAllImages;
    private Button btnold,btnnew;
    private String endcusor;
    private Context context;
    private ProgressDialog pd;
    private ArrayList<String> imageThumbList = new ArrayList<String>();
    private ArrayList<String> page = new ArrayList<String>();
    private int count = 0;
    private int WHAT_FINALIZE = 0;
    private static int WHAT_ERROR = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String TAG_DATA = "user";
    public static final String TAG_MEDIA = "media";
    public static final String TAG_NODES = "nodes";
    public static final String TAG_THUMBNAIL = "thumbnail_src";
    public static final String TAG_CUSOR = "end_cursor";
    public static final String TAG_INFO = "page_info";
    private String  end_cusor ="";
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_FINALIZE) {
                setImageGridAdapter();
            } else {
                Toast.makeText(context, "Check your network.",
                        Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    public static SocialFragment newInstance() {
        SocialFragment fragment = new SocialFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_social, container, false);

        gvAllImages = (GridView)rootView.findViewById(R.id.gridView);
        context = getActivity();
        getAllMediaImages();
        gvAllImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = "https://www.instagram.com/borntoklop/";
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(a));
                    startActivity(myIntent);
            }
        });

        btnShare = (ImageView)rootView.findViewById(R.id.BtnCamera);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        btnold = (Button) rootView.findViewById(R.id.buttonnext);
        btnold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageThumbList.clear();
                end_cusor = endcusor.toString();
                if (count == 0){
                    if (page.indexOf("") == -1){
                        page.add("");
                    }
                }else {
                    for (int i =0 ; i<= page.size(); i++){
                        if (page.indexOf(end_cusor) == -1){
                            page.add(end_cusor);
                        }
                    }
                }
                count++;
                getAllMediaImages();
            }
        });
        btnnew = (Button)rootView.findViewById(R.id.buttonpre);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageThumbList.clear();
                count = count - 1;
                end_cusor = page.get(count);
                getAllMediaImages();
            }
        });

        return rootView;
    }
    private void setImageGridAdapter() {
        gvAllImages.setAdapter(new GridInstagramAdapter(context, imageThumbList));
    }


    private void getAllMediaImages() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    // URL url = new URL(mTokenUrl + "&code=" + code);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = jsonParser
                            .getJSONFromUrlByGet("https://www.instagram.com/borntoklop/?__a=1&max_id="+end_cusor);
                    JSONObject data = jsonObject.getJSONObject(TAG_DATA);
                    JSONObject media = data.getJSONObject(TAG_MEDIA);
                    JSONObject info = media.getJSONObject(TAG_INFO);
                    endcusor = info.getString(TAG_CUSOR);
                    JSONArray nodes = media.getJSONArray(TAG_NODES);

                    for (int nodes_i = 0; nodes_i < nodes.length(); nodes_i++) {
                        JSONObject data_obj = nodes.getJSONObject(nodes_i);
//                        JSONObject images_obj = data_obj
//                                .getJSONObject(TAG_IMAGES);

//                        JSONObject thumbnail_obj = data_obj
//                                .getJSONObject(TAG_THUMBNAIL);

                        //String link_obj = String.valueOf(data.get(TAG_LINK));
                        // String str_height =
                        // thumbnail_obj.getString(TAG_HEIGHT);
                        //
                        // String str_width =
                        // thumbnail_obj.getString(TAG_WIDTH);

                        String str_url = data_obj.getString(TAG_THUMBNAIL);
                        imageThumbList.add(str_url);
//                        linkImage.add(link_obj);
                    }

                    System.out.println("jsonObject::" + jsonObject);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }
                // pd.dismiss();
                handler.sendEmptyMessage(what);
            }
        }).start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap image = (Bitmap) extras.get("data");
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            Uri tempUri = data.getData();
            share.putExtra(Intent.EXTRA_STREAM,tempUri);
            share.putExtra(Intent.EXTRA_TITLE,"#borntoklop");
            share.putExtra(Intent.EXTRA_SUBJECT,"#borntoklop");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(share, "Share Image"));
        }
    }
}
