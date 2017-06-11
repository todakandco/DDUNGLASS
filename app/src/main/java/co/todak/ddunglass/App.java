package co.todak.ddunglass;

import android.app.Application;
import android.content.Context;

/**
 * Created by CJH_NOTE on 2017-06-03.
 */

// 현재 어떤 Actvity를 사용하는지 가져오기 위해 사용한 클래스
// CropActvity 에서 카메라나 저장소의 권한을 주기 위해 자기 자신의 Activity 를 가져와야 하는데
// CropActvity 를 선언할 때 implements View.OnClickListener 를 사용하여 this 를 사용하면
// OnClickListener 객체가 불러와져 아래와 같이 따로 클래스를 만들어서 Context 객체를 넣어주었다.
public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context Context) {
        mContext = Context;
    }
}
