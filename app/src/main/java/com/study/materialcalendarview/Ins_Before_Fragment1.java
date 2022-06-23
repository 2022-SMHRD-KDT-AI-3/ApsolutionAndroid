package com.study.materialcalendarview;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Ins_Before_Fragment1 extends Fragment {

    ImageView img;
    ImageButton camera_back;
    Button btn_gallery, btn_send, btn_capture;
    private ProgressDialog progress;

    private RequestQueue queue;
    private String currentPhotoPath;
    private Bitmap bitmap;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int GET_GALLERY_IMAGE = 2;

    static final String TAG = "카메라";
    private String imageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_camera, container, false);

        img = view.findViewById(R.id.img);

        btn_capture = view.findViewById(R.id.btn_capture);
        btn_gallery = view.findViewById(R.id.btn_gallery);
        btn_send = view.findViewById(R.id.btn_send);
        camera_back = view.findViewById(R.id.camera_back);

        SharedPreferences spf = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        String Login_id = spf.getString("id","0");

        init();

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_open_intent();
            }

        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery_open_intent();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(getActivity());
                progress.setMessage("Uploading...");
                progress.show();

                sendImage(Login_id);

                Intent intent = new Intent(getActivity(),activity_resultcheck.class);
                getActivity().startActivity(intent);

            }

        });

        camera_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainPage_Fragment5()).commit();
            }
        });

        return view;
    }

    //이미지 플라스크로 전송
    private void sendImage(String Login_id) {

        //비트맵 이미지를 byte로 변환 -> base64형태로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        String id = Login_id;

        //base64형태로 변환된 이미지 데이터를 플라스크 서버로 전송
        String flask_url = "http://172.30.1.30:5000/sendFrame";
        StringRequest request = new StringRequest(Request.Method.POST, flask_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        if(response.equals("true")){
                            Toast.makeText(getActivity(), "Uploaded Successful", Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(getActivity(), "Some error occurred!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Some error occurred -> "+error, Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageString);
                params.put("id", id);

                return params;
            }
        };

        queue.add(request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Uri picturePhotoURI = Uri.fromFile(new File(currentPhotoPath));

            getBitmap(picturePhotoURI);
            img.setImageBitmap(bitmap);

            //갤러리에 사진 저장
            saveFile(currentPhotoPath);

        } else if (requestCode == GET_GALLERY_IMAGE && resultCode == getActivity().RESULT_OK) {
            Uri galleryURI = data.getData();
            //img.setImageURI(galleryURI);

            getBitmap(galleryURI);
            img.setImageBitmap(bitmap);
        }

    }

    //Uri에서 bitmap
    private void getBitmap(Uri picturePhotoURI) {
        try {
            //서버로 이미지를 전송하기 위한 비트맵 변환하기
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picturePhotoURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //xml에 정의한 view 초기화
    private void init() {
//        img = view.findViewById(R.id.img);
//
//        btn_capture = view.findViewById(R.id.btn_capture);
//        btn_gallery = findViewById(R.id.btn_gallery);
//        btn_send = findViewById(R.id.btn_send);

        queue = Volley.newRequestQueue(getActivity());

        requestPermission();
    }

    //카메라, 쓰기, 읽기 권한 체크/요청
    private void requestPermission() {
        //민감한 권한 사용자에게 허용요청
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { // 저장소에 데이터를 쓰는 권한을 부여받지 않았다면~

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    //갤러리 띄우기
    private void gallery_open_intent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GET_GALLERY_IMAGE);
        btn_gallery.setText("다시선택");

    }

    //갤러리 사진 저장 기능
    private void saveFile(String currentPhotoPath) {

        Bitmap bitmap = BitmapFactory.decodeFile( currentPhotoPath );

        ContentValues values = new ContentValues( );

        //실제 앨범에 저장될 이미지이름
        values.put( MediaStore.Images.Media.DISPLAY_NAME, new SimpleDateFormat( "yyyyMMdd_HHmmss", Locale.US ).format( new Date( ) ) + ".jpg" );
        values.put( MediaStore.Images.Media.MIME_TYPE, "image/*" );

        //저장될 경로 -> /내장 메모리/DCIM/ 에 'AndroidQ' 폴더로 지정
        values.put( MediaStore.Images.Media.RELATIVE_PATH, "DCIM/AndroidQ" );

        Uri u = MediaStore.Images.Media.getContentUri( MediaStore.VOLUME_EXTERNAL );
        Uri uri = getActivity().getContentResolver( ).insert( u, values ); //이미지 Uri를 MediaStore.Images에 저장

        try {
            /*
             ParcelFileDescriptor: 공유 파일 요청 객체
             ContentResolver: 어플리케이션끼리 특정한 데이터를 주고 받을 수 있게 해주는 기술(공용 데이터베이스)
                            ex) 주소록이나 음악 앨범이나 플레이리스트 같은 것에도 접근하는 것이 가능

            getContentResolver(): ContentResolver객체 반환
            */

            ParcelFileDescriptor parcelFileDescriptor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                parcelFileDescriptor = getActivity().getContentResolver( ).openFileDescriptor( uri, "w", null ); //미디어 파일 열기
            }
            if ( parcelFileDescriptor == null ) return;

            //바이트기반스트림을 이용하여 JPEG파일을 바이트단위로 쪼갠 후 저장
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );

            //비트맵 형태 이미지 크기 압축
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream );
            byte[] b = byteArrayOutputStream.toByteArray( );
            InputStream inputStream = new ByteArrayInputStream( b );

            ByteArrayOutputStream buffer = new ByteArrayOutputStream( );
            int bufferSize = 1024;
            byte[] buffers = new byte[ bufferSize ];

            int len = 0;
            while ( ( len = inputStream.read( buffers ) ) != -1 ) {
                buffer.write( buffers, 0, len );
            }

            byte[] bs = buffer.toByteArray( );
            FileOutputStream fileOutputStream = new FileOutputStream( parcelFileDescriptor.getFileDescriptor( ) );
            fileOutputStream.write( bs );
            fileOutputStream.close( );
            inputStream.close( );
            parcelFileDescriptor.close( );

            getActivity().getContentResolver( ).update( uri, values, null, null ); //MediaStore.Images 테이블에 이미지 행 추가 후 업데이트

        } catch ( Exception e ) {
            e.printStackTrace( );
        }

        values.clear( );
        values.put( MediaStore.Images.Media.IS_PENDING, 0 ); //실행하는 기기에서 앱이 IS_PENDING 값을 1로 설정하면 독점 액세스 권한 획득
        getActivity().getContentResolver( ).update( uri, values, null, null );

    }

    //카메라 호출
    private void camera_open_intent() {
        Log.d("Camera", "카메라실행!");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "에러발생!!");
            }

            if (photoFile != null) {
                Uri providerURI = FileProvider.getUriForFile(getActivity(), "com.study.materialcalendarview.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //카메라 촬영 시 임시로 사진을 저장하고 사진위치에 대한 Uri 정보를 가져오는 메소드
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        Log.d(TAG, "사진저장>> "+storageDir.toString());

        currentPhotoPath = image.getAbsolutePath();

        return image;

    }

//    public void sendid(String ID){
//        String url = "http://172.30.1.30:5000/sendid";
//
//        //JSON형식으로 데이터 통신을 진행합니다!
//        JSONObject testjson = new JSONObject();
//        try {
//            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
//            testjson.put("id", ID);
//            String jsonString = testjson.toString(); //완성된 json 포맷
//
//            //이제 전송해볼까요?
//            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {
//
//                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
//                @Override
//                public void onResponse(JSONObject response) {
//
//                }
//                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            requestQueue.add(jsonObjectRequest);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }



}