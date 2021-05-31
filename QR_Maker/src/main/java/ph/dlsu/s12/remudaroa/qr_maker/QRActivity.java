package ph.dlsu.s12.remudaroa.qr_maker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRActivity extends AppCompatActivity {

    private ImageView IV_qrcode;
    private EditText ET_fullname, ET_address, ET_contactnumber, ET_age;
    private String completeInfo = "", sBitmap;
    private Button btn_create;
    private MakerPrefs makerPrefs;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        makerPrefs = new MakerPrefs(getApplicationContext());

        //for clearing out preferences
        //makerPrefs.removePreferences();

        IV_qrcode = findViewById(R.id.IV_qrcode);
        ET_fullname = findViewById(R.id.ET_fullname);
        ET_address = findViewById(R.id.ET_address);
        ET_contactnumber = findViewById(R.id.ET_contactNumber);
        ET_age = findViewById(R.id.ET_age);
        btn_create = findViewById(R.id.btn_create);

        fillMaker();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ET_fullname.getText().toString())||TextUtils.isEmpty(ET_address.getText().toString())||TextUtils.isEmpty(ET_contactnumber.getText().toString())||TextUtils.isEmpty(ET_age.getText().toString())) {
                    Toast.makeText(QRActivity.this, "Please fill completely fill ou the form", Toast.LENGTH_SHORT).show();
                } else {

                    completeInfo = ET_fullname.getText().toString() + "//" + ET_address.getText().toString() + "//" + ET_contactnumber.getText().toString() + "//" + ET_age.getText().toString();
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Display display = manager.getDefaultDisplay();

                    Point point = new Point();
                    display.getSize(point);

                    int width = point.x;
                    int height = point.y;
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    qrgEncoder = new QRGEncoder(completeInfo, null, QRGContents.Type.TEXT, dimen);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        IV_qrcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.e("Tag", e.toString());
                    }
                }
            }
        });

        }

        //Saves values inside EditText to preferences upon exit
    private void backgroundFocus(){

        makerPrefs= new MakerPrefs(getApplicationContext());

        makerPrefs.saveStringPreferences("name", ET_fullname.getText().toString());
        makerPrefs.saveStringPreferences("address", ET_address.getText().toString());
        makerPrefs.saveStringPreferences("contactNumber", ET_contactnumber.getText().toString());
        makerPrefs.saveStringPreferences("age", ET_age.getText().toString());

        if(!(IV_qrcode.getDrawable() == null) ) {
            sBitmap = BitMapToString(bitmap);
            makerPrefs.saveStringPreferences("qrCode", sBitmap);
        }
        System.out.println(IV_qrcode.getDrawable());
    }

    // restores values to EditText once user returns to application
    private void fillMaker(){
        makerPrefs= new MakerPrefs(getApplicationContext());

        if(!(makerPrefs.getStringPreferences("name") == "Nothing Saved"))
            ET_fullname.setText(makerPrefs.getStringPreferences("name"));

        if(!(makerPrefs.getStringPreferences("address") == "Nothing Saved"))
            ET_address.setText(makerPrefs.getStringPreferences("address"));

        if(!(makerPrefs.getStringPreferences("contactNumber") == "Nothing Saved"))
         ET_contactnumber.setText(makerPrefs.getStringPreferences("contactNumber"));

        if(!(makerPrefs.getStringPreferences("age") == "Nothing Saved"))
            ET_age.setText(makerPrefs.getStringPreferences("age"));

        if(!(makerPrefs.getStringPreferences("qrCode") == "Nothing Saved")) {
            sBitmap = makerPrefs.getStringPreferences("qrCode");
            bitmap = StringToBitMap(sBitmap);

            IV_qrcode.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backgroundFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundFocus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillMaker();
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}