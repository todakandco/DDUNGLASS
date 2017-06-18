/*
package co.todak.ddunglass.NotUsed;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Window;

*//**
 * Created by CJH_NOTE on 2017-06-18.
 *//*

public class MyUtil {

    // 상태 바 크기 구하는 메소드
    // 참고 : http://arabiannight.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9CAndroid-statusbar-titlebar-%EB%86%92%EC%9D%B4-%EA%B5%AC%ED%95%98%EA%B8%B0
    public static int getStatusBarSize(Context mContext) {
        Rect rectgle = new Rect();

        //WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Window window = ((Activity) mContext).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);

        int StatusBarHeight = rectgle.top;

        return StatusBarHeight;
    }
}
*/
