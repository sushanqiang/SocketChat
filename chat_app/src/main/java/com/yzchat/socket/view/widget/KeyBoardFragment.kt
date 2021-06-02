package com.yzchat.socket.view.widget

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.yzchat.socket.R
import com.yzchat.socket.utils.ScreenUtils
import kotlinx.android.synthetic.main.plvec_chat_input_layout.*

class KeyBoardFragment : DialogFragment() {
    var inputText:((String)->Unit)?=null
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullSreenDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val params: WindowManager.LayoutParams
        if (window != null) {
            params = window.attributes
            params.gravity = Gravity.BOTTOM
            params.width =  ScreenUtils.getScreenWidth(context)
            window.attributes = params
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        view.requestFocus()
        initView()
    }

    private fun initView() {
        key_bar_root?.setOnClickListener {
            SoftInputUtils.closeSoftInput(context, chat_input_et)
            dismissAllowingStateLoss()
        }
        cl_ed_top?.setOnClickListener {  }
        sendBtn?.setOnClickListener {
            SoftInputUtils.closeSoftInput(context, chat_input_et)
            dismissAllowingStateLoss()
            val s=chat_input_et?.text?.toString()
            if (!s.isNullOrEmpty())
            inputText?.invoke(s)
        }
        val key = KeyBarLisenter {
            val isEnabled = chat_input_et?.text.toString().isNullOrEmpty()
            sendBtn?.isEnabled = !isEnabled
        }
        chat_input_et?.addTextChangedListener(key)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.plvec_chat_input_layout, container, false)
    }


    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.isDestroyed) {
            return
        }
        try {
            val transaction = manager.beginTransaction()
            if (this.isAdded) {
                transaction.remove(this).commitAllowingStateLoss()
            }
            transaction.add(this, tag)
            transaction.commitAllowingStateLoss()
            manager.executePendingTransactions()
        } catch (e: Exception) {
        }

    }

    override fun dismiss() {
        super.dismiss()

    }
    fun clrearText(){
        chat_input_et?.setText("")

    }

    class KeyBarLisenter(var status: (() -> Unit)? = null) : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            status?.invoke()

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }
}