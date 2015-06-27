/**
 *
 */
package com.example.abner.listviewwithsoftpanel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;


/**
 * 聊天界面，消息输入框(实现多行的EditText，并且键盘显示send)
 */
public class MessageEditText extends EditText {
    private final String TAG = "MessageEditText";

    public MessageEditText(Context context) {
        super(context);
    }

    public MessageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        int imeActions = outAttrs.imeOptions & EditorInfo.IME_MASK_ACTION;
        if ((imeActions & EditorInfo.IME_ACTION_SEND) != 0) {
            // clear the existing action
            outAttrs.imeOptions ^= imeActions;
            // set the send action
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_SEND;
        }
        if ((outAttrs.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
        return connection;
    }

    @Override
    public boolean dispatchKeyEventPreIme(@NonNull KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (listener != null) {
                listener.onClickBack();
            }
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    //键盘弹出时点击返回键事件
    public interface SoftPanelBackListener {
        void onClickBack();
    }

    private SoftPanelBackListener listener;

    public void setListener(SoftPanelBackListener listener) {
        this.listener = listener;
    }
}
