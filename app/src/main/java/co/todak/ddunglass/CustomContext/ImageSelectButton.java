/*

package co.todak.ddunglass.CustomContext;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

*/
/**
 * Created by CJH_NOTE on 2017-06-18.
 *//*


public class ImageSelectButton extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Activity activity;
    ImageView imageView;
    Button button;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    private Uri mImageCaptureUri;

    public ImageSelectButton(Context context, ImageView imageView, Button button) {
        this.context = context;
        this.activity = (Activity) context;
        this.imageView = imageView;
        this.button = button;

        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Toast toast = Toast.makeText(this.context, "왜 안 나옴?", Toast.LENGTH_SHORT);
        toast.show();

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

    // 카메라에서 사진 촬영 후 이미지 가져오기
    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        ((Activity) (this.context)).startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    // 앨범에서 이미지 가져오기
    public void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        Toast toast = Toast.makeText(this.context, "doTakeAlbumAction", Toast.LENGTH_SHORT);
        toast.show();
        this.activity.startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast toast = Toast.makeText(this.context, "onActivityResult", Toast.LENGTH_SHORT);
        toast.show();

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
                imageView.setImageURI(mImageCaptureUri);

                toast = Toast.makeText(this.context, "PICK_FROM_CAMERA", Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
        }
    }
}*/
