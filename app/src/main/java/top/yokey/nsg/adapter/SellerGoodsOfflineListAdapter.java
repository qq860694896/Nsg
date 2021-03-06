package top.yokey.nsg.adapter;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import top.yokey.nsg.activity.NcApplication;
import top.yokey.nsg.R;
import top.yokey.nsg.system.SellerAjaxParams;
import top.yokey.nsg.utility.DialogUtil;
import top.yokey.nsg.utility.ToastUtil;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：卖家商品列表已下架适配器
*
*/

public class SellerGoodsOfflineListAdapter extends RecyclerView.Adapter<SellerGoodsOfflineListAdapter.ViewHolder> {

    private Activity mActivity;
    private NcApplication mApplication;
    private ArrayList<HashMap<String, String>> mArrayList;

    public SellerGoodsOfflineListAdapter(NcApplication application, Activity activity, ArrayList<HashMap<String, String>> arrayList) {
        this.mActivity = activity;
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

        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        ImageLoader.getInstance().displayImage(hashMap.get("goods_image"), holder.mImageView);
        holder.nameTextView.setText(hashMap.get("goods_name"));
        String temp = "￥ " + hashMap.get("goods_price");
        holder.pricePromotionTextView.setText(temp);
        temp = "库存 " + hashMap.get("goods_storage_sum") + " 件";
        holder.storageTextView.setText(temp);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show(mActivity, "开发中");
            }
        });

        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.query(mActivity, "确认您的选择", "上架这个商品", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtil.cancel();
                        DialogUtil.progress(mActivity);
                        SellerAjaxParams ajaxParams = new SellerAjaxParams(mApplication);
                        ajaxParams.putAct("seller_goods");
                        ajaxParams.putOp("goods_show");
                        ajaxParams.put("commonids", hashMap.get("goods_commonid"));
                        mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
                            @Override
                            public void onSuccess(Object o) {
                                super.onSuccess(o);
                                DialogUtil.cancel();
                                if (mApplication.getJsonSuccess(o.toString())) {
                                    mArrayList.remove(holder.getAdapterPosition());
                                    ToastUtil.showSuccess(mActivity);
                                    notifyDataSetChanged();
                                } else {
                                    ToastUtil.showFailure(mActivity);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                super.onFailure(t, errorNo, strMsg);
                                ToastUtil.showFailure(mActivity);
                                DialogUtil.cancel();
                            }
                        });
                    }
                });
            }
        });

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.query(mActivity, "确认您的选择", "删除这个商品", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtil.cancel();
                        DialogUtil.progress(mActivity);
                        SellerAjaxParams ajaxParams = new SellerAjaxParams(mApplication);
                        ajaxParams.putAct("seller_goods");
                        ajaxParams.putOp("goods_drop");
                        ajaxParams.put("commonids", hashMap.get("goods_commonid"));
                        mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
                            @Override
                            public void onSuccess(Object o) {
                                super.onSuccess(o);
                                DialogUtil.cancel();
                                if (mApplication.getJsonSuccess(o.toString())) {
                                    mArrayList.remove(holder.getAdapterPosition());
                                    ToastUtil.showSuccess(mActivity);
                                    notifyDataSetChanged();
                                } else {
                                    ToastUtil.showFailure(mActivity);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                super.onFailure(t, errorNo, strMsg);
                                ToastUtil.showFailure(mActivity);
                                DialogUtil.cancel();
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_seller_goods_offline, group, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRelativeLayout;
        public ImageView mImageView;
        public TextView nameTextView;
        public TextView pricePromotionTextView;
        public TextView storageTextView;
        public FloatingActionButton editButton;
        public FloatingActionButton upButton;
        public FloatingActionButton delButton;

        public ViewHolder(View view) {
            super(view);

            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.mainRelativeLayout);
            mImageView = (ImageView) view.findViewById(R.id.mainImageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            pricePromotionTextView = (TextView) view.findViewById(R.id.pricePromotionTextView);
            storageTextView = (TextView) view.findViewById(R.id.storageTextView);
            editButton = (FloatingActionButton) view.findViewById(R.id.editButton);
            upButton = (FloatingActionButton) view.findViewById(R.id.upButton);
            delButton = (FloatingActionButton) view.findViewById(R.id.delButton);

        }

    }

}
