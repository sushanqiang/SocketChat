package com.yzchat.socket.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yzchat.socket.view.widget.KeyBoardFragment
import com.yzchat.socket.R
import com.yzchat.socket.view.adapter.PLVChatMessageItemType
import com.yzchat.socket.view.adapter.PLVECChatMessageAdapter
import com.yzchat.socket.base.ChatBaseFragment
import com.yzchat.socket.base.ChatBaseViewData
import com.yzchat.socket.data.PLVCustomGiftBean
import com.yzchat.socket.data.PolyvBulletinVO
import com.yzchat.socket.data.PolyvMediaPlayMode
import com.yzchat.socket.event.PLVBaseEvent
import com.yzchat.socket.event.PLVCustomGiftEvent
import com.yzchat.socket.event.PLVLoginEvent
import com.yzchat.socket.utils.ScreenUtils
import com.yzchat.socket.view.widget.*
import com.yzchat.socket.view.widget.PLVECMorePopupView.OnLiveMoreClickListener
import com.yzchat.socket.view.widget.PLVECRewardGiftAnimView.RewardGiftInfo
import com.yzchat.socket.view.widget.recyclerview.PLVMessageRecyclerView
import java.util.*

/**
 * 聊天室：主持人信息、聊天室、点赞、更多、商品、打赏
 */
class ChatGroupFragment : ChatBaseFragment(), View.OnClickListener {
    // <editor-fold defaultstate="collapsed" desc="变量">
    //观看信息布局
    private var watchInfoLy: PLVECWatchInfoView? = null

    //公告布局
    private var bulletinLy: PLVECBulletinView? = null

    //欢迎语
    private var greetLy: PLVECGreetingView? = null

    //聊天区域
    private var chatMsgRv: PLVMessageRecyclerView? = null
    private var chatMessageAdapter: PLVECChatMessageAdapter? = null
    private var sendMsgTv: TextView? = null
    private var chatImgScanPopupView: PLVECChatImgScanPopupView? = null

    //未读信息提醒view
    private var unreadMsgTv: TextView? = null

    //下拉加载历史记录控件
    private var swipeLoadView: SwipeRefreshLayout? = null

    //点赞区域
    private var likeBt: PLVECLikeIconView? = null
    private var likeCountTv: TextView? = null

    //更多
    private var moreIv: ImageView? = null
    private var morePopupView: PLVECMorePopupView? = null
    private var currentLinesPos = 0
    private var currentDefinitionPos = 0
    private var videoViewRect: Rect? = null

    //商品
    private var commodityIv: ImageView? = null

    //    private PLVECCommodityPopupView commodityPopupView;
    private val isOpenCommodityMenu = false

    //    private PLVECCommodityPushLayout commodityPushLayout;
    private val lastJumpBuyCommodityLink: String? = null

    //打赏
    private var rewardIv: ImageView? = null
    private var rewardPopupView: PLVECRewardPopupView? = null
    private var keyBoardFragment: KeyBoardFragment? = null
    private var rewardGiftAnimView: PLVECRewardGiftAnimView? = null

    private var back_iv: ImageView? = null

    //监听器
    private val onViewActionListener: OnViewActionListener? =
            null

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="生命周期">
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.plvec_live_page_home_fragment, null)
        initView()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        calculateLiveVideoViewRect()
        startLikeAnimationTask(5000)
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="初始化view">
    private fun initView() {
        watchInfoLy = findViewById(R.id.watch_info_ly)
        bulletinLy = findViewById(R.id.bulletin_ly)
        greetLy = findViewById(R.id.greet_ly)
        chatMsgRv = findViewById(R.id.chat_msg_rv)
        PLVMessageRecyclerView.setLayoutManager(chatMsgRv).stackFromEnd = true
        chatMsgRv?.addItemDecoration(
                PLVMessageRecyclerView.SpacesItemDecoration(
                        ScreenUtils.dip2px(
                                activity,
                                4f
                        )
                )
        )
        chatMessageAdapter = PLVECChatMessageAdapter()
        chatMsgRv?.setAdapter(chatMessageAdapter)
        chatMessageAdapter?.setOnViewActionListener(onChatMsgViewActionListener)
        //未读信息view
        unreadMsgTv =
                findViewById(R.id.unread_msg_tv)
        chatMsgRv?.addUnreadView(unreadMsgTv)
        //下拉控件
        swipeLoadView =
                findViewById(R.id.swipe_load_view)
        swipeLoadView?.isEnabled=false
        swipeLoadView?.setColorSchemeResources(
                android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light
        )
        swipeLoadView?.setOnRefreshListener {
            swipeLoadView?.isRefreshing=false
            //                chatroomPresenter.requestChatHistory(0);
        }
        sendMsgTv = findViewById(R.id.send_msg_tv)
        sendMsgTv?.setOnClickListener(this)
        likeBt = findViewById(R.id.like_bt)
        likeBt?.setOnButtonClickListener(this)
        likeCountTv =
                findViewById(R.id.like_count_tv)
        moreIv = findViewById(R.id.more_iv)
        moreIv?.setOnClickListener(this)
        commodityIv =
                findViewById(R.id.commodity_iv)
        commodityIv?.setOnClickListener(this)
        //        commodityPushLayout = findViewById(R.id.commodity_push_ly);
        rewardIv = findViewById(R.id.reward_iv)
        rewardIv?.setOnClickListener(this)
        rewardGiftAnimView =
                findViewById(R.id.reward_ly)
        morePopupView = PLVECMorePopupView()
        //        commodityPopupView = new PLVECCommodityPopupView();
        rewardPopupView = PLVECRewardPopupView()
        chatImgScanPopupView = PLVECChatImgScanPopupView()
        back_iv = findViewById(R.id.back_iv)
        back_iv?.setOnClickListener(this)
    }

    // </editor-fold>
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="聊天室 - 公告控制">
    private fun acceptBulletinMessage(bulletinVO: PolyvBulletinVO) {
        bulletinLy?.acceptBulletinMessage(bulletinVO)
    }

    private fun removeBulletin() {
        bulletinLy?.removeBulletin()
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="聊天室 - 欢迎语控制">
    fun acceptLoginMessage(loginEvent: PLVLoginEvent) {
        //显示欢迎语
        greetLy?.acceptGreetingMessage(loginEvent)
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="聊天室 - 添加信息至列表">
    fun addChatMessageToList(
            chatMessageDataList: List<ChatBaseViewData<*>>,
            isScrollEnd: Boolean
    ) {
        handler.post {
            if (chatMessageAdapter == null) return@post
            chatMessageAdapter?.addDataListChanged(chatMessageDataList)
            if (isScrollEnd) {
                chatMsgRv?.scrollToPosition(chatMessageAdapter?.itemCount ?: 0 - 1)
            } else {
                chatMsgRv?.scrollToBottomOrShowMore(chatMessageDataList.size)
            }
        }
    }

    private fun addChatHistoryToList(
            chatMessageDataList: List<ChatBaseViewData<PLVBaseEvent>>,
            isScrollEnd: Boolean
    ) {
        handler.post {
            if (chatMessageAdapter == null) return@post

            chatMessageAdapter?.addDataListChangedAtFirst(chatMessageDataList)
            if (isScrollEnd) {
                chatMsgRv?.scrollToPosition(chatMessageAdapter?.itemCount ?: 0 - 1)
            } else {
                chatMsgRv?.scrollToPosition(0)
            }
        }
    }

    private fun removeChatMessageToList(
            id: String,
            isRemoveAll: Boolean
    ) {
        handler.post {
            if (isRemoveAll) {
                chatMessageAdapter?.removeAllDataChanged()
            } else {
                chatMessageAdapter?.removeDataChanged(id)
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="聊天室 - 图片信息点击事件处理">
    var onChatMsgViewActionListener =
            PLVECChatMessageAdapter.OnViewActionListener { view, imgUrl ->
                chatImgScanPopupView?.showImgScanLayout(
                        view,
                        imgUrl
                )
            }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="聊天室 - 输入窗口显示，信息发送">
    private fun showInputWindow() {
        if (keyBoardFragment == null) {
            keyBoardFragment = KeyBoardFragment()
            keyBoardFragment?.inputText = { s: String ->
                sendMsg(s)
                null
            }
        }
        keyBoardFragment?.show(childFragmentManager, "keyBoardFragment")
    }

    private fun sendMsg(message: String) {
//        val localMessage = PolyvLocalMessage(message)
        sendMsg?.invoke(message)
        keyBoardFragment?.clrearText()

    }

    var sendMsg: ((String) -> Unit)? = null

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="聊天室 - 点赞数据处理，定时显示飘心任务">
    private fun acceptLikesMessage(likesCount: Int) {
        handler.post { startAddLoveIconTask(200, Math.min(5, likesCount)) }
    }

    private fun startLikeAnimationTask(ts: Long) {
        handler.postDelayed({
            val randomLikeCount = Random().nextInt(5) + 1
            startAddLoveIconTask(200, randomLikeCount)
            startLikeAnimationTask((Random().nextInt(6) + 5) * 1000L)
        }, ts)
    }

    private fun startAddLoveIconTask(ts: Long, count: Int) {
        if (count >= 1) {
            handler.postDelayed({
                likeBt?.addLoveIcon(1)
                startAddLoveIconTask(ts, count - 1)
            }, ts)
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="商品 - 布局显示、事件处理、推送显示、商品链接跳转等">
    private fun showCommodityLayout(v: View) {
        //清空旧数据
//        commodityPopupView.setCommodityVO(null);
        //每次弹出都调用一次接口获取商品信息
//        liveRoomDataManager.requestProductList();
//        commodityPopupView.showCommodityLayout(v, new PLVECCommodityAdapter.OnViewActionListener() {
//            @Override
//            public void onBuyCommodityClick(View view, PLVProductContentBean contentsBean) {
//                acceptBuyCommodityClick(contentsBean);
//            }
//
//            @Override
//            public void onLoadMoreData(int rank) {
//                liveRoomDataManager.requestProductList(rank);
//            }
//        });
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="打赏 - 布局显示，动画view显示">
    private fun showRewardLayout(v: View) {
        rewardPopupView?.showRewardLayout(v) { view, giftBean ->
            rewardPopupView?.hide()
            val nickName = "用户："
            showRewardGiftAnimView("$nickName(我)", giftBean)
            addCustomGiftToChatList(
                    "$nickName(我)",
                    giftBean.giftName,
                    giftBean.giftType,
                    true
            )
            //通过自定义信息事件发送礼物信息至聊天室
//                chatroomPresenter.sendCustomGiftMessage(giftBean, nickName + " 赠送了" + giftBean.getGiftName());
        }
    }

    private fun showRewardGiftAnimView(
            userName: String,
            giftBean: PLVCustomGiftBean
    ) {
        val giftDrawableId = resources.getIdentifier(
                "plvec_gift_" + giftBean.giftType,
                "drawable",
                context?.packageName
        )
        rewardGiftAnimView?.acceptRewardGiftMessage(
                RewardGiftInfo(userName, giftBean.giftName, giftDrawableId)
        )
    }

    private fun addCustomGiftToChatList(
            userName: String,
            giftName: String,
            giftType: String,
            isScrollEnd: Boolean
    ) {
        val customGiftEvent =
                generateCustomGiftEvent(userName, giftName, giftType)
        val dataList: MutableList<ChatBaseViewData<*>> =
                ArrayList()
        dataList.add(
                ChatBaseViewData(
                        customGiftEvent,
                        PLVChatMessageItemType.ITEMTYPE_CUSTOM_GIFT
                )
        )
        addChatMessageToList(dataList, isScrollEnd)
    }

    private fun generateCustomGiftEvent(
            userName: String,
            giftName: String,
            giftType: String
    ): PLVCustomGiftEvent {
        val span =
                SpannableStringBuilder("$userName 赠送了 $giftName p")
        val giftDrawableId = resources.getIdentifier(
                "plvec_gift_$giftType",
                "drawable",
                context?.packageName
        )
        val drawable =
                resources.getDrawable(giftDrawableId)
        val imageSpan: ImageSpan =
                PLVRelativeImageSpan(drawable, PLVRelativeImageSpan.ALIGN_CENTER)
        val textSize = ScreenUtils.dip2px(activity, 12f)
        drawable.setBounds(0, 0, (textSize * 1.5).toInt(), (textSize * 1.5).toInt())
        span.setSpan(
                imageSpan,
                span.length - 1,
                span.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return PLVCustomGiftEvent(span)
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="更多弹窗 - 布局显示及交互处理">
    private fun showMorePopupWindow(v: View) {
        val isCurrentVideoMode =
                onViewActionListener == null || onViewActionListener.onGetMediaPlayModeAction() == PolyvMediaPlayMode.MODE_VIDEO
        morePopupView?.showLiveMoreLayout(v, isCurrentVideoMode, object : OnLiveMoreClickListener {
            override fun onPlayModeClick(view: View): Boolean {
                if (onViewActionListener != null) {
                    onViewActionListener.onChangeMediaPlayModeClick(
                            view,
                            if (view.isSelected) PolyvMediaPlayMode.MODE_VIDEO else PolyvMediaPlayMode.MODE_AUDIO
                    )
                    return true
                }
                return false
            }

            override fun onShowLinesClick(view: View): IntArray {
                return intArrayOf(
                        onViewActionListener?.onGetLinesCountAction() ?: 1,
                        currentLinesPos
                )
            }

            override fun onLinesChangeClick(
                    view: View,
                    linesPos: Int
            ) {
                if (currentLinesPos != linesPos) {
                    currentLinesPos = linesPos
                    onViewActionListener?.onChangeLinesClick(view, linesPos)
                }
            }

            //            @Override
            //            public Pair<List<PolyvDefinitionVO>, Integer> onShowDefinitionClick(View view) {
            //                return onViewActionListener == null ? new Pair<List<PolyvDefinitionVO>, Integer>(null, 0) : onViewActionListener.onShowDefinitionClick(view);
            //            }
            override fun onDefinitionChangeClick(
                    view: View,
                    definitionPos: Int
            ) {
                if (currentDefinitionPos != definitionPos) {
                    currentDefinitionPos = definitionPos
                    onViewActionListener?.onDefinitionChangeClick(view, definitionPos)
                }
            }
        })
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="播放器 - 计算直播播放器横屏视频、音频模式的播放器区域位置">
    private fun calculateLiveVideoViewRect() {
        watchInfoLy?.post {
            if (watchInfoLy == null) return@post
            acceptVideoViewRectParams(watchInfoLy!!.bottom, 0)
        }
        greetLy?.post {
            if (greetLy == null) return@post
            acceptVideoViewRectParams(0, greetLy!!.top)
        }
    }

    private fun acceptVideoViewRectParams(top: Int, bottom: Int) {

        if (videoViewRect == null) {
            videoViewRect = Rect(0, top, 0, bottom)
        } else {
            videoViewRect = Rect(
                    0,
                    Math.max(videoViewRect!!.top, top),
                    0,
                    Math.max(videoViewRect!!.bottom, bottom)
            )
            onViewActionListener?.onSetVideoViewRectAction(videoViewRect)
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="点击事件">
    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_iv -> {
                activity?.finish()
            }
            R.id.send_msg_tv -> {
                showInputWindow()
            }
            R.id.more_iv -> {
                showMorePopupWindow(v)
            }
            R.id.like_bt -> {
//            chatroomPresenter.sendLikeMessage();
                acceptLikesMessage(1)
            }
            R.id.commodity_iv -> {
                showCommodityLayout(v)
            }
            R.id.reward_iv -> {
                showRewardLayout(v)
            }
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="内部类 - view交互事件监听器">
    interface OnViewActionListener {
        //切换播放模式
        fun onChangeMediaPlayModeClick(
                view: View?,
                mediaPlayMode: Int
        )

        //切换线路
        fun onChangeLinesClick(view: View?, linesPos: Int)

        //[清晰度信息，清晰度索引]
        //        Pair<List<PolyvDefinitionVO>, Integer> onShowDefinitionClick(View view);
        //切换清晰度
        fun onDefinitionChangeClick(view: View?, definitionPos: Int)

        //获取播放模式
        fun onGetMediaPlayModeAction(): Int

        //获取线路数
        fun onGetLinesCountAction(): Int

        //获取线路索引
        fun onGetLinesPosAction(): Int

        //获取清晰度索引
        fun onGetDefinitionAction(): Int

        //设置播放器的位置
        fun onSetVideoViewRectAction(videoViewRect: Rect?)
    } // </editor-fold>

    companion object {
        private const val TAG = "PLVECLiveHomeFragment"
    }
}