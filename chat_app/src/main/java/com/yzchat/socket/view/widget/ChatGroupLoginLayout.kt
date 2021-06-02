package com.yzchat.socket.view.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import com.yzchat.socket.R
import kotlinx.android.synthetic.main.layout_chat_group_login.view.*
import kotlin.random.Random

/**
 *
 */
class ChatGroupLoginLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var checkLogin: ((String, String) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_chat_group_login, this, true)
    }

    fun setLisenter() {
        btLogin?.setOnClickListener {
            checks()
        }

        val u = "${Random.nextInt(10, 555)}"
        user_id?.setText(u)
        app_roomId?.setText("100")
    }

    private fun checks() {
        if (user_id == null) return
        val uid = user_id.text.toString()
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(context, "请输入userId", Toast.LENGTH_SHORT).show()
            return
        }
        val app_roomId = app_roomId.text.toString()
        if (TextUtils.isEmpty(app_roomId)) {
            Toast.makeText(context, "请输入房间号", Toast.LENGTH_SHORT).show()
            return
        }
        checkLogin?.invoke(uid, app_roomId)

    }


}