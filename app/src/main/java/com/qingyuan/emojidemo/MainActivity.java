package com.qingyuan.emojidemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qingyuan.emojidemo.emojiutils.EmojiWidget;

public class MainActivity extends Activity implements OnClickListener {
    private final static int ON_EMOJI_CHANGE = 0xc1;
    private ListView chatListView;
    private TextView tvSend;
    private EditText etContent;
    private ImageView ivEmoji;
    private ChatAdapter chatAdapter;
    private LinearLayout llEmoji;
    private boolean b = false;
    private EmojiWidget emojiWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        chatAdapter = new ChatAdapter(this);
        chatListView = (ListView) findViewById(R.id.chat_listview);
        tvSend = (TextView) findViewById(R.id.tv_send);
        etContent = (EditText) findViewById(R.id.et_content);
        ivEmoji = (ImageView) findViewById(R.id.iv_emoji);
        llEmoji = (LinearLayout) findViewById(R.id.ll_emoji);
        tvSend.setOnClickListener(this);
        ivEmoji.setOnClickListener(this);
        etContent.setOnClickListener(this);
        chatListView.setAdapter(chatAdapter);
        this.emojiWidget = new EmojiWidget(this, this, ON_EMOJI_CHANGE,
                this.mUIHandler, this.etContent);

    }

    /**
     * 监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_emoji:
                hideKeyboardAndEmoji();
                if (llEmoji.getVisibility() == View.GONE) {
                    llEmoji.setVisibility(View.VISIBLE);
                } else {
                    llEmoji.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_send:
                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "不能为空呀", Toast.LENGTH_LONG).show();
                    return;
                }

                Person person = new Person();

                if (b == true) {
                    b = false;//左边
                } else {
                    b = true;//右边
                }


                person.content = content;
                person.direction = b;
                chatAdapter.setData(person);
                chatAdapter.notifyDataSetChanged();
                chatListView.setSelection(chatAdapter.getCount() - 1);
                etContent.setText("");

                break;
            case R.id.et_content:
                llEmoji.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 更新的Handler
     */
    private Handler mUIHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_EMOJI_CHANGE: { // 监听表情界面的变化
                    emojiWidget.refreshWidgetUI(msg);
                    break;
                }
            }
        }
    };
    //隐藏键盘
    private void hideKeyboardAndEmoji() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
