package co.todak.ddunglass;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by CJH_NOTE on 2017-06-03.
 */

public class CropActivity extends AppCompatActivity implements View.OnClickListener {

    // requestCode를 통해 switch문으로 분기합니다.
    /*
        PICK_FROM_CAMERA 는 사진을 촬영하고 찍힌 이미지를 처리하는 부분
        PICK_FROM_ALBUM 은 앨범에서 사진을 고르고 이미지를 처리하는 부분
        CROP_FROM_IMAGE 는 이미지를 크롭하는 부분
    */
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    int MY_REQUEST_CODE;

    int btn_id;


    private Uri mImageCaptureUri;

    // 사람 이미지가 들어갈 ImageView
    private ImageView iv_UserPhoto;

    // 안경 이미지가 들어갈 ImageView
    private ImageView iv_GlassPhoto;

    // Crop 된 파일의 절대 경로
    private String absoultePath;

    // 태그
    private static final String HUMANVIEW_TAG = "사람 이미지";
    private static final String GLASSVIEW_TAG = "안경 이미지";

    // 기존 x 좌표, y 좌표
    float oldXvalue;
    float oldYvalue;

    // 상태 바(시계가 보이는 부분)의 Y 축 길이를 저장하기 위한 변수
    float statusYvalue;

    // 사진, 이미지 등록
    // 참고 : http://jeongchul.tistory.com/287

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        // 현재 화면상에 불러온 Context 객체를 저장
        App.setContext(this);

        // 현재 layout 에서
        LinearLayout layout = new LinearLayout(this);

        // 상태 바(시계가 보이는 부분)의 Y 축 길이를 저장
        layout.post(new Runnable() {
            @Override
            public void run() {
                statusYvalue = getStatusBarSize();
            }
        });

        // 사람(배경) 이미지
        iv_UserPhoto = (ImageView) this.findViewById(R.id.user_image);
        iv_UserPhoto.setTag(HUMANVIEW_TAG);

        // 안경 이미지
        iv_GlassPhoto = (ImageView) this.findViewById(R.id.glass_image);
        iv_GlassPhoto.setTag(GLASSVIEW_TAG);

        // 안경 이미지에 터치 리스너를 연결하여
        // 터치하면 이미지를 옮길 수 있게 하고 손을 떼면 이미지가 고정되도록 함
        // 참고 : http://blog.naver.com/PostView.nhn?blogId=tkddlf4209&logNo=220734131855
        iv_GlassPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
                int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

                // 이미지에 손을 대면
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    oldXvalue = event.getX();
                    oldYvalue = event.getY();
                }

                // 이미지를 손을 대고 움직이면
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setX(event.getRawX() - oldXvalue);
                    // statusYvalue(36)은 상태 바 크기
                    v.setY(event.getRawY() - oldYvalue - statusYvalue);
                }

                // 이미지에서 손을 떼면
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (v.getX() > width && v.getY() > height) {
                        v.setX(width);
                        v.setY(height);
                    } else if (v.getX() < 0 && v.getY() > height) {
                        v.setX(0);
                        v.setY(height);
                    } else if (v.getX() > width && v.getY() < 0) {
                        v.setX(width);
                        v.setY(0);
                    } else if (v.getX() < 0 && v.getY() < 0) {
                        v.setX(0);
                        v.setY(0);
                    } else if (v.getX() < 0 || v.getX() > width) {
                        if (v.getX() < 0) {
                            v.setX(0);
                            v.setY(event.getRawY() - oldYvalue - statusYvalue);
                        } else {
                            v.setX(width);
                            v.setY(event.getRawY() - oldYvalue - statusYvalue);
                        }
                    } else if (v.getY() < 0 || v.getY() > height) {
                        if (v.getY() < 0) {
                            v.setX(event.getRawX() - oldXvalue);
                            v.setY(0);
                        } else {
                            v.setX(event.getRawX() - oldXvalue);
                            v.setY(height);
                        }
                    }
                }
                return true;
            }
        });

        // 사람(배경) 이미지 넣는 클릭 리스너 연결
        Button btn_agreeJoin = (Button) this.findViewById(R.id.btn_UploadPicture);
        btn_agreeJoin.setOnClickListener(this);

        // 안경 이미지 넣는 클릭 리스너 연결
        Button btn_glassImage = (Button) this.findViewById(R.id.btn_GlassesPicture);
        btn_glassImage.setOnClickListener(this);
    }

    // 상태 바 크기 구하는 메소드
    // 참고 : http://arabiannight.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9CAndroid-statusbar-titlebar-%EB%86%92%EC%9D%B4-%EA%B5%AC%ED%95%98%EA%B8%B0
    private int getStatusBarSize() {
        Rect rectgle = new Rect();

        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);

        int StatusBarHeight = rectgle.top;

        return StatusBarHeight;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_UploadPicture || v.getId() == R.id.btn_GlassesPicture) {
            btn_id = v.getId();

            // 카메라로 이미지 가져오기
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // 권한을 주는데 현재 Activity의 정보가 필요하여 가져오기 위해 선언
                            Context c = App.getContext();

                            // 카메라 권한 체크
                            int permissionCheckStorage = ActivityCompat.checkSelfPermission(c, Manifest.permission.CAMERA);

                            // 카메라 권한이 없으면 카메라, 저장소의 권한을 요청함
                            if (permissionCheckStorage == PackageManager.PERMISSION_DENIED) {
                                ActivityCompat.requestPermissions((Activity) c, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
                            }

                    doTakePhotoAction();
                }
            };

            // 앨범에서 이미지 가져오기 버튼
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            // 취소 버튼
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            // 알림 Dialog 생성
            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    // 카메라에서 사진 촬영 후 이미지 가져오기
    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    // 앨범에서 이미지 가져오기
    public void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                switch (btn_id) {
                    case R.id.btn_UploadPicture:
                        iv_UserPhoto.setImageURI(mImageCaptureUri);
                        break;

                    case R.id.btn_GlassesPicture:
                        iv_GlassPhoto.setImageURI(mImageCaptureUri);
                        break;

                    default:
                        break;
                }

                break;
            }
        }
    }
}
