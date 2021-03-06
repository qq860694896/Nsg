package top.yokey.nsg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import top.yokey.nsg.activity.NcApplication;
import top.yokey.nsg.R;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：单人聊天列表适配器
*
*/

public class ChatOnlyListAdapter extends RecyclerView.Adapter<ChatOnlyListAdapter.ViewHolder> {

    private String avatar;
    private NcApplication mApplication;
    private ArrayList<HashMap<String, String>> mArrayList;

    public ChatOnlyListAdapter(NcApplication application, ArrayList<HashMap<String, String>> arrayList, String avatar) {
        this.avatar = avatar;
        this.mArrayList = arrayList;
        this.mApplication = application;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HashMap<String, String> hashMap = mArrayList.get(position);

        holder.mRelativeLayout.setVisibility(View.GONE);
        holder.myRelativeLayout.setVisibility(View.GONE);

        if (hashMap.get("f_id").equals(mApplication.userHashMap.get("member_id"))) {
            holder.myRelativeLayout.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mApplication.userHashMap.get("avator"), holder.myHeadImageView);
            holder.myMsgTextView.setText(hashMap.get("t_msg"));
        } else {
            holder.mRelativeLayout.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(avatar, holder.headImageView);
            holder.msgTextView.setText(hashMap.get("t_msg"));
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_chat_only, group, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRelativeLayout;
        public ImageView headImageView;
        public TextView msgTextView;

        public RelativeLayout myRelativeLayout;
        public ImageView myHeadImageView;
        public TextView myMsgTextView;

        public ViewHolder(View view) {
            super(view);

            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.mainRelativeLayout);
            headImageView = (ImageView) view.findViewById(R.id.headImageView);
            msgTextView = (TextView) view.findViewById(R.id.msgTextView);
            myRelativeLayout = (RelativeLayout) view.findViewById(R.id.myRelativeLayout);
            myHeadImageView = (ImageView) view.findViewById(R.id.myHeadImageView);
            myMsgTextView = (TextView) view.findViewById(R.id.myMsgTextView);

        }

    }

}
