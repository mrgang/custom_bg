package com.example.custom_bg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.example.custom_bg.mydialogs.ColorPickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private Button btn_start, btn_middle, btn_end, btn_save;
    private ImageView img_bg;
    private GradientDrawable gd;
    private int i,num_type=0;
    private int colors[] = {-65531, -15735040, -16776961};
    private Spinner spinner;
    private GradientDrawable.Orientation[] types = {
            GradientDrawable.Orientation.TOP_BOTTOM,GradientDrawable.Orientation.BL_TR,
            GradientDrawable.Orientation.BOTTOM_TOP,GradientDrawable.Orientation.BR_TL,
            GradientDrawable.Orientation.LEFT_RIGHT,GradientDrawable.Orientation.RIGHT_LEFT,
            GradientDrawable.Orientation.TL_BR,GradientDrawable.Orientation.TR_BL
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupViewComponent();

    }

    private void setupViewComponent() {
        btn_start = (Button) this.findViewById(R.id.start_color);
        btn_middle = (Button) this.findViewById(R.id.middle_color);
        btn_end = (Button) this.findViewById(R.id.end_color);
        btn_save = (Button)this.findViewById(R.id.save_color);
        spinner = (Spinner)this.findViewById(R.id.sp_chk);
        String [] str_sp = {"从上到下","下左到上右","从下到上","下右到上左","从左到右","从右到左","上左到下右","上右到下左"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,str_sp);
        spinner.setAdapter(adapter);
        spinner.setSelection(4);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num_type = position;
                init_bg();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        img_bg = (ImageView) this.findViewById(R.id.img_bg);

        int h = getWindowManager().getDefaultDisplay().getHeight();
        int w = getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams params = img_bg.getLayoutParams();
        params.height = w;
        params.width = w;
        img_bg.setLayoutParams(params);
        init_bg();

        btn_start.setOnClickListener(this);
        btn_middle.setOnClickListener(this);
        btn_end.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    private void init_bg() {
        Log.i("set color:",colors[0]+"_"+colors[1]+"_"+colors[2]);
        gd = new GradientDrawable(types[num_type], colors);
        img_bg.setImageDrawable(gd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_color:
                Log.i("MyActivity", "start-color");
                i = 0;
                Dialog dialog = new ColorPickerDialog(MyActivity.this, Color.WHITE,
                        new ColorPickerDialog.OnColorChangedListener() {

                            @Override
                            public void colorChanged(int color) {
                                colors[i] = color;
                                init_bg();
                            }
                        });
                dialog.setTitle(R.string.dialog_title);
                dialog.show();
                break;
            case R.id.middle_color:
                Log.i("MyActivity", "middle_color");
                i = 1;
                Dialog dialog1 = new ColorPickerDialog(MyActivity.this, Color.WHITE,
                        new ColorPickerDialog.OnColorChangedListener() {

                            @Override
                            public void colorChanged(int color) {
                                colors[i] = color;
                                init_bg();
                            }
                        });
                dialog1.setTitle(R.string.dialog_title);
                dialog1.show();
                break;
            case R.id.end_color:
                Log.i("MyActivity", "end_color");
                i = 2;
                Dialog dialog2 = new ColorPickerDialog(MyActivity.this, Color.WHITE,
                        new ColorPickerDialog.OnColorChangedListener() {

                            @Override
                            public void colorChanged(int color) {
                                colors[i] = color;
                                init_bg();
                            }
                        });
                dialog2.setTitle(R.string.dialog_title);
                dialog2.show();
                break;
            case R.id.save_color:
                Log.i("MyActivity", "save_color");
                byte[] pic_data = null;
                FileOutputStream fos = null;
                Date date = new Date();
                String name = "bg_"+date.getYear()+date.getMonth()+date.getDay()+
                        "_"+date.getHours()+date.getMinutes()+date.getSeconds();
                File fDir = new File("/sdcard/customBG");
                if (!fDir.exists()){
                        fDir.mkdir();

                }
                String fpath = Environment.getExternalStorageDirectory()+
                        File.separator+"customBG"+File.separator+name+".png";
                img_bg.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(img_bg.getDrawingCache());
                img_bg.setDrawingCacheEnabled(false);
                if (bitmap != null){
                    try{
                        fos = new FileOutputStream(fpath);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                    try{
                        fos.flush();
                        fos.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
               Toast.makeText(MyActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }
}
