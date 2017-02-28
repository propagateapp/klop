package klop.propagate.com.au.klop;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.NewPlayers;

public class AddPlayerActivity extends AppCompatActivity {
    private TextView btnCancel,btnSave,btnDelete;
    private EditText edFirstN,edLastN;
    private ImageView imv;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        final Boolean a =  intent.getBooleanExtra("update",false);
        final String fname = intent.getStringExtra("fName");
        final String lname = intent.getStringExtra("lName");
        byte[] image = intent.getByteArrayExtra("image");
        final Integer id = intent.getIntExtra("id",0);

        btnCancel = (TextView)findViewById(R.id.tvCancel);
        btnSave = (TextView)findViewById(R.id.tvSave);
        btnDelete = (Button)findViewById(R.id.btnDeletePlayer);
        edFirstN = (EditText)findViewById(R.id.etFistName);
        edLastN = (EditText)findViewById(R.id.etLastName);
        imv = (ImageView)findViewById(R.id.imageVPhoto);

        final DatabasePlayers data = new DatabasePlayers(this);
        final NewPlayers players = new NewPlayers();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = edFirstN.getText().toString().trim();
                String lastName = edLastN.getText().toString().trim();
                String name = firstName + "_" + lastName;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (imageBitmap == null){
                    imageBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.iconplayer2);
                }
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte imginByte[] = stream.toByteArray();
                if (firstName.equals("")||firstName == null){
                    Toast.makeText(getApplicationContext(),"Firt Name",Toast.LENGTH_SHORT).show();
                }else {
                    if (a == true){
                        players.setFirstName(name);
                        players.setImg(imginByte);
                        players.setId(id);
                        data.updatePlayers(players);
                        onBackPressed();
                    }else {
                        data.addPlayers(new NewPlayers(name,imginByte));
                        finish();
                    }
                }
            }
        });
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.deletePlayers(id);
                data.deletePlayerinGame(fname+"_"+lname);
                finish();
            }
        });

        if (a == true){
            btnDelete.setVisibility(View.VISIBLE);
            edFirstN.setText(fname);
            edLastN.setText(lname);
            ByteArrayInputStream imgStream = new ByteArrayInputStream(image);
            imageBitmap = BitmapFactory.decodeStream(imgStream);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(null,imageBitmap);
            dr.setCornerRadius(Math.max(imageBitmap.getWidth(),imageBitmap.getHeight())/2.0f);
            imv.setImageDrawable(dr);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            imageBitmap = (Bitmap) extras.get("data");

            if (imageBitmap.getWidth() >= imageBitmap.getHeight()){

                imageBitmap = Bitmap.createBitmap(
                        imageBitmap,
                        imageBitmap.getWidth()/2 - imageBitmap.getHeight()/2,
                        0,
                        imageBitmap.getHeight(),
                        imageBitmap.getHeight()
                );

            }else{

                imageBitmap = Bitmap.createBitmap(
                        imageBitmap,
                        0,
                        imageBitmap.getHeight()/2 - imageBitmap.getWidth()/2,
                        imageBitmap.getWidth(),
                        imageBitmap.getWidth()
                );
            }
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(null,imageBitmap);
            dr.setCornerRadius(Math.max(imageBitmap.getWidth(),imageBitmap.getHeight())/2.0f);
            imv.setImageDrawable(dr);
//            Uri tempUri = data.getData();
//            File mFile = new File(getRealPathFromURI(tempUri));
//            mFile.isHidden();
//            mFile.delete();
        }
    }
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        return cursor.getString(idx);
//    }

}
