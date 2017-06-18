package co.todak.ddunglass.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import co.todak.ddunglass.CustomContext.GImageView;
import co.todak.ddunglass.R;

/**
 * Created by CJH_NOTE on 2017-06-03.
 */

public class DDunglassActvity extends AppCompatActivity {

    // requestCode를 통해 switch문으로 분기합니다.
    /*
        PICK_FROM_CAMERA 는 사진을 촬영하고 찍힌 이미지를 처리하는 부분
        PICK_FROM_ALBUM 은 앨범에서 사진을 고르고 이미지를 처리하는 부분
        CROP_FROM_IMAGE 는 이미지를 크롭하는 부분
    */
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    int btn_id;

    private Uri mImageCaptureUri;

    // 사람 이미지가 들어갈 ImageView
    private ImageView iv_UserPhoto;

    // 태그
    private static final String HUMANVIEW_TAG = "사람 이미지";
    private static final String GLASSVIEW_TAG = "안경 이미지";

    GImageView glassImage;
/*
    ImageSelectButton btn_custom;
    ImageSelectButton btn_custom2;
*/

    // 사진, 이미지 등록
    // 참고 : http://jeongchul.tistory.com/287

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddunglass);

        // 사람(배경) 이미지
        iv_UserPhoto = (ImageView) this.findViewById(R.id.user_image);
        iv_UserPhoto.setTag(HUMANVIEW_TAG);

        // 안경 이미지
        glassImage = (GImageView) findViewById(R.id.glass_image);

        // 사람(배경) 이미지 넣는 클릭 리스너 연결
        Button btn_agreeJoin = (Button) this.findViewById(R.id.btn_UploadPicture);
        //      btn_custom = new ImageSelectButton(this, iv_UserPhoto, btn_agreeJoin);

        btn_agreeJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_UploadPicture || v.getId() == R.id.btn_GlassesPicture) {
                    btn_id = v.getId();

                    // 카메라로 이미지 가져오기
                    DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("업로드할 이미지 선택")
                            .setPositiveButton("사진촬영", cameraListener)
                            .setNeutralButton("앨범선택", albumListener)
                            .setNegativeButton("취소", cancelListener)
                            .show();
                }
            }
        });

        // 안경 이미지 넣는 클릭 리스너 연결
        Button btn_glassImage = (Button) this.findViewById(R.id.btn_GlassesPicture);
        // btn_custom2 = new ImageSelectButton(this, (ImageView) glassImage, btn_glassImage);

        btn_glassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_UploadPicture || v.getId() == R.id.btn_GlassesPicture) {
                    btn_id = v.getId();

                    // 카메라로 이미지 가져오기
                    DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("업로드할 이미지 선택")
                            .setPositiveButton("사진촬영", cameraListener)
                            .setNeutralButton("앨범선택", albumListener)
                            .setNegativeButton("취소", cancelListener)
                            .show();
                }
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_UploadPicture || v.getId() == R.id.btn_GlassesPicture) {
            btn_id = v.getId();

            // 카메라로 이미지 가져오기
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
                        glassImage.setImageURI(mImageCaptureUri);
                        glassImage.setVisibility(View.VISIBLE);
                        break;

                    default:
                }

                break;
            }
        }
    }
}
