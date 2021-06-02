package com.yzchat.socket.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yzchat.socket.R
import com.yzchat.socket.data.Message
import com.yzchat.socket.data.User
import com.yzchat.socket.utils.ToastUtils
import com.yzchat.socket.view.widget.messages.MessageHolders
import com.yzchat.socket.view.widget.messages.MessageInput
import com.yzchat.socket.view.widget.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.layout_messages.*
import java.util.*

/**
 * 私聊
 */
class ChatPrivateFragment : Fragment(), MessageInput.InputListener,
        MessageInput.AttachmentsListener {
    var messagesAdapter: MessagesListAdapter<Message>? = null

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_messages, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setTextInfo(name: String?, otherUserId: String?, userId: String?, nums: Int) {
        otherUserIds = otherUserId
        userIds = userId
        tv_info?.text = "对方信息:$otherUserIds  我的id:$userId"
        messagesAdapter?.senderId = userIds


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        input?.setInputListener(this)
//        fakeStatusBar?.layoutParams?.height = StatusBarUtil.getStatusBarHeight(context)
//        fakeStatusBar?.requestLayout()
//        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT && Build.VERSION_CODES.M > Build.VERSION.SDK_INT) {
//            fakeStatusBar.setBackgroundColor(Color.BLACK)
//        }
        back_iv?.setOnClickListener {
            activity?.finish()
        }

        initAdapter()

        input?.setInputListener(this)
        input?.setAttachmentsListener(this)
    }

    private fun initAdapter() {


        val holdersConfig: MessageHolders = MessageHolders()
                .setIncomingTextLayout(R.layout.item_custom_incoming_text_message)
                .setOutcomingTextLayout(R.layout.item_custom_outcoming_text_message)
                .setIncomingImageLayout(R.layout.item_custom_incoming_image_message)
                .setOutcomingImageLayout(R.layout.item_custom_outcoming_image_message)

        messagesAdapter = MessagesListAdapter(userIds, holdersConfig, null)
        messagesList?.setAdapter(messagesAdapter)

    }


    var sendMsg: ((CharSequence?) -> Unit)? = null

    var otherUserIds: String? = ""
    var userIds: String? = ""

    companion object {
        private const val TAG = "MsgFragment"
        fun newInstance(name: String?, otherUserIds: String?, userId: String?, nums: Int): ChatPrivateFragment {
            val args = Bundle()
            args.putString("name", name)
            args.putString("userId", userId)
            args.putString("otherUserIds", otherUserIds)

            args.putInt("nums", nums)
            val fragment = ChatPrivateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onSubmit(input: CharSequence?): Boolean {
        if (input.isNullOrEmpty()) {
            ToastUtils.showShort("请输入内容")
            return false
        }
        sendMsg?.invoke(input)
        Log.e(TAG, "otherUserIds=$otherUserIds---------userIds=$userIds")
        messagesAdapter?.addToStart(getMessages(userIds ?: "", input.toString()), true)
        return true
    }

    fun addToStart(uid: String?, msg: String, boolean: Boolean) {

        if (Looper.getMainLooper() != Looper.myLooper()) {
            activity?.runOnUiThread {
                messagesAdapter?.addToStart(getMessages(uid ?: "", msg), boolean)
            }
        } else {
            messagesAdapter?.addToStart(getMessages(uid ?: "", msg), boolean)

        }
    }

    fun getMessages(uid: String, text: String?): Message? {
        val message = Message(uid, getUser(uid), text)
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        message.createdAt = calendar.time

        return message
    }

    private fun getUser(uid:String): User? {
        return User(
                uid,
                "用户",
                "",
                true)
    }

    override fun onAddAttachments() {
    }
}