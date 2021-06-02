package com.yzchat.socket.view.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.yzchat.socket.R
import kotlinx.android.synthetic.main.layout_chat_private_login.view.*
import kotlin.random.Random


/**
 *
 */
class ChatPrivateLoginLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var checkLogin: ((String, String) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_chat_private_login, this, true)
    }

    fun setLisenter() {

        tv_private_chat?.setOnClickListener {
            checks()
        }
        val u = "${Random.nextInt(10, 555)}"
        user_id_p?.setText(u)
    }

    private fun checks() {
        if (user_id_p == null) return
        val uid = user_id_p.text.toString()
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(context, "请输入userId", Toast.LENGTH_SHORT).show()
            return
        }
        val other_uid_p = other_uid_p?.text?.toString()
        if (other_uid_p.isNullOrEmpty()) {
            Toast.makeText(context, "请输入对方用户id", Toast.LENGTH_SHORT).show()
            return
        }
        checkLogin?.invoke(uid, other_uid_p)

    }
}