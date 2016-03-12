package com.qingyuan.emojidemo;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyuan.emojidemo.emojiutils.EmojiConversionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyuan on 16/3/11.
 * 适配器
 */
public class ChatAdapter extends BaseAdapter {
    private List<Person> persons = new ArrayList<>();
    private Context mContext;

    public ChatAdapter(Context context){
        this.mContext = context;

    }
    public void setData(Person person) {
        persons.add(person);
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return persons.get(position).direction ? 0 : 1;
    }
    public int getViewTypeCount() {
        return 2;
    }

    ViewHolder viewHolder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int view_type = getItemViewType(position);
        Person person = persons.get(position);
        if(convertView == null){
            switch (view_type){
                case 0:
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_right,
                            parent, false);
                    viewHolder.content = (TextView) convertView
                            .findViewById(R.id.right_content);

                    convertView.setTag(viewHolder);
                    break;
                case 1:
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_left,
                            parent, false);
                    viewHolder.content = (TextView) convertView
                            .findViewById(R.id.left_content);
                    convertView.setTag(viewHolder);
                    break;
            }
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 解析表情
         */
        SpannableString spannableString = EmojiConversionUtils.INSTANCE.getExpressionString(mContext,person.content);
        viewHolder.content.setText(spannableString);

        return convertView;
    }


    public class  ViewHolder{
        TextView content;

    }
}
