package com.example.abner.listviewwithsoftpanel;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private final static String TAG = "MainActivity";
    //输入框控件
    private View mFootBar;

    private TextView mSend;

    private MessageEditText mEtMessage;

    private ListView mListView;

    private int mScreenHeight;

    private List<SimpleModel> mItems;

    private SimpleAdapter mAdapter;

    private SimpleModel mSimpleModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        mScreenHeight = p.y;
        mListView = (ListView) findViewById(R.id.listview);
        mEtMessage = (MessageEditText) findViewById(R.id.et_message);
        mSend = (TextView) findViewById(R.id.tv_send);
        mFootBar = findViewById(R.id.rl_foot_bar);
        genrateData();
        mAdapter = new SimpleAdapter(mItems, this);
        mListView.setAdapter(mAdapter);
        mEtMessage.setOnFocusChangeListener(this);

        mEtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendComment();
                    return true;
                }
                return false;
            }
        });
        mEtMessage.setListener(new MessageEditText.SoftPanelBackListener() {
            @Override
            public void onClickBack() {
                closeSoftPanel();
            }
        });

        //触摸关闭键盘
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeSoftPanel();
                return false;
            }
        });
        mAdapter.setCommentListener(new SimpleAdapter.CommentListener() {
            @Override
            public void replyComment(View view, SimpleModel model) {
                mSimpleModel = model;
                commentView = view;
                mEtMessage.setHint("回复:");
                openSoftPanel();
            }
        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void sendComment() {

        if (!TextUtils.isEmpty(mEtMessage.getText().toString())) {
            mSimpleModel.getComments().add(mEtMessage.getText().toString());
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT).show();
        }
        //因为关闭后会清空edit 所以要放最后执行
        closeSoftPanel();
    }

    //生成假数据
    private void genrateData() {
        mItems = new ArrayList<>();
        for (int i = 20; i > 0; i--) {
            SimpleModel model = new SimpleModel();
            model.setText("初始化状态" + i);
            List<String> comments = new ArrayList<>();
            for(int j = 0 ; j < i ; j++){
                comments.add("初始化评论 : " + j +" 第"+i+"条状态");
            }
            model.setComments(comments);
            mItems.add(model);
        }
    }

    /**
     * *************************评论操作****************
     */

    private View commentView;
    private int mShowLastHeight;
    private boolean softPanelIsShow = false;
    private Handler handler = new Handler();
    final Runnable showSoftPanel = new Runnable() {
        @Override
        public void run() {
            int newHeight = getDecorViewHeight();
            Log.d(TAG, "newHeight  : " + newHeight);
            if (mShowLastHeight == newHeight && newHeight != mScreenHeight) {
                Log.d(TAG, "键盘高度  : " + newHeight);
                final int[] location = new int[2];
                mFootBar.getLocationOnScreen(location);
                final int[] locationComment = new int[2];
                //计算点击的view的Y坐标
                commentView.getLocationOnScreen(locationComment);
                Log.d(TAG, " 点击的view的位置: " + locationComment[1] + " 点击的view的位置高度 " + commentView.getHeight());
                int distance = locationComment[1] + commentView.getHeight() - location[1];
                Log.d(TAG, "distance:" + distance);
                mListView.smoothScrollBy(distance, 100);
                commentView.getLocationOnScreen(locationComment);
                mShowLastHeight = 0;
                handler.removeCallbacks(this);
            } else {
                Log.d(TAG, "mShowLastHeight = " + mShowLastHeight);
                mShowLastHeight = newHeight;
                if (softPanelIsShow) {
                    handler.postDelayed(this, 10);
                }
            }
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus) {
            softPanelIsShow = true;
            imm.showSoftInput(mEtMessage, InputMethodManager.SHOW_FORCED);
            handler.postDelayed(showSoftPanel, 20);
        } else {
            softPanelIsShow = false;
            mEtMessage.setFocusable(false);
            mEtMessage.setFocusableInTouchMode(false);
            imm.hideSoftInputFromWindow(mEtMessage.getWindowToken(), 0);
        }
    }

    /**
     * 关闭键盘时需要做的一些操作
     */
    private void closeSoftPanel() {
        mEtMessage.setFocusable(false);
        mEtMessage.setFocusableInTouchMode(false);
        mFootBar.setVisibility(View.GONE);
        mEtMessage.setText("");
    }

    /**
     * 弹出键盘需要做的一些操作
     */
    private void openSoftPanel() {
        mFootBar.setVisibility(View.VISIBLE);
        mEtMessage.setFocusable(true);
        mEtMessage.setFocusableInTouchMode(true);
        mEtMessage.requestFocus();
    }


    private int getDecorViewHeight() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.bottom;
    }

    /**
     * *******************************点击评论操作**************************
     */


}
