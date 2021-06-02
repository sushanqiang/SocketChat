package com.yzchat.socket.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yzchat.socket.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.login_layout.*

/**
 *登录
 */
class LoginFragment : Fragment() {
    private var disposable: CompositeDisposable? = null
    var login: ((String, String, String?, String?, String?) -> Unit)? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposable = CompositeDisposable()

        tv_change?.setOnClickListener {
            if (true == group_chat?.isShown) {
                group_chat?.visibility = View.GONE
                private_chat?.visibility = View.VISIBLE
                tv_change?.text = "去聊天室"

            } else {
                group_chat?.visibility = View.VISIBLE
                private_chat?.visibility = View.GONE
                tv_change?.text = "去私聊"
            }
        }
        private_chat?.setLisenter()
        private_chat?.checkLogin = { uid, other_uid ->
            checkUserEd(uid, "用户$uid", "privateEvent", null, other_uid)

        }
        group_chat?.setLisenter()
        group_chat?.checkLogin = { uid, roomId ->
            checkUserEd(uid, "用户$uid", "groupEvent", roomId, null)

        }

    }


    private fun checkUserEd(
            uid: String,
            name: String,
            type: String?,
            roomId: String?,
            mUser: String?
    ) {

        login?.invoke(uid, name, type, roomId, mUser)

    }

    companion object {
        private val TAG = "LoginFragment"
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }
}