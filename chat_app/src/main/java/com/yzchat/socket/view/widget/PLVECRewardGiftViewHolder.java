package com.yzchat.socket.view.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzchat.socket.R;
import com.yzchat.socket.view.adapter.PLVBaseViewHolder;
import com.yzchat.socket.base.ChatBaseViewData;
import com.yzchat.socket.data.PLVCustomGiftBean;
import com.yzchat.socket.utils.ScreenUtils;


/**
 * 打赏礼物viewHolder
 */
public class PLVECRewardGiftViewHolder extends PLVBaseViewHolder<ChatBaseViewData, PLVECRewardGiftAdapter> {
    private PLVCustomGiftBean giftBean;
    private ImageView giftIv;
    private TextView giftTv;
    private TextView rewardTv;

    public PLVECRewardGiftViewHolder(final View itemView, final PLVECRewardGiftAdapter adapter) {
        super(itemView, adapter);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getLastSelectView() != null && adapter.getLastSelectView() != v) {
                    adapter.getLastSelectView().setSelected(false);
                    View rewardView = adapter.getLastSelectView().findViewById(R.id.reward_tv);
                    View giftView = adapter.getLastSelectView().findViewById(R.id.gift_tv);
                    if (rewardView != null) {
                        rewardView.setVisibility(View.GONE);
                    }
                    if (giftView != null) {
                        giftView.setTranslationY(ScreenUtils.dip2px(itemView.getContext(),6));
                    }
                }
                if (v.isSelected()) {
                    adapter.callOnRewardClick(v, giftBean);
                }
                itemView.setSelected(true);
                adapter.setLastSelectView(v);
                rewardTv.setVisibility(View.VISIBLE);
                giftTv.setTranslationY(-ScreenUtils.dip2px(itemView.getContext(),2));
            }
        });
        giftIv = findViewById(R.id.gift_iv);
        giftTv = findViewById(R.id.gift_tv);
        rewardTv = findViewById(R.id.reward_tv);
    }

    @Override
    public void processData(ChatBaseViewData data, int position) {
        super.processData(data, position);
        giftBean = (PLVCustomGiftBean) data.getData();
        int drawableId = itemView.getContext().getResources().getIdentifier("plvec_gift_" + giftBean.getGiftType(), "drawable", itemView.getContext().getPackageName());
        giftIv.setImageResource(drawableId);
        giftTv.setText(giftBean.getGiftName());
        if (itemView.isSelected()) {
            rewardTv.setVisibility(View.VISIBLE);
            giftTv.setTranslationY(-ScreenUtils.dip2px(itemView.getContext(),2));
        } else {
            rewardTv.setVisibility(View.GONE);
            giftTv.setTranslationY(ScreenUtils.dip2px(itemView.getContext(),6));
        }
    }
}
