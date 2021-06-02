package com.yzchat.socket.view.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yzchat.socket.R
import com.yzchat.socket.SingleSocket
import com.yzchat.socket.base.ChatBaseActivity
import com.yzchat.socket.base.ChatBaseViewData
import com.yzchat.socket.data.ChatToken
import com.yzchat.socket.data.ChatToken.ChatBody
import com.yzchat.socket.data.ChatToken.ChatHeader
import com.yzchat.socket.data.PLVChatMessageItemType
import com.yzchat.socket.data.PLVSocketUserBean
import com.yzchat.socket.data.PolyvLocalMessage
import com.yzchat.socket.event.PLVLoginEvent
import com.yzchat.socket.event.SocketEvent
import com.yzchat.socket.view.fragment.ChatGroupFragment
import com.yzchat.socket.view.fragment.LoginFragment
import com.yzchat.socket.view.fragment.ChatPrivateFragment
import com.yzchat.socket.utils.*
import com.yzchat.socket.view.widget.SoftInputUtils
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import java.util.*
import kotlin.random.Random

class MainActivity : ChatBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun showFloat() {
        val list = mutableListOf("s", "af", "fss", "sss")
        val size = list.size
        val v = Vector<Int>()
        var boolean = true
        while (boolean) {
            val r = Random.nextInt(size)
            if (!v.contains(r)) {
                v.add(r)
            }
            if (size == v.size) {
                boolean = false
            }

        }
        for (i: Int in 0 until size) {
            Log.e("showFloat", "i:$i getOrNull=" + list.getOrNull(v[i]))
        }


    }

    private var disposable: CompositeDisposable? = null

    var mLoginFragment: Fragment? = null
    var privateFragment: ChatPrivateFragment? = null
    var groupFragment: ChatGroupFragment? = null
    var connetType: String? = null
    var otherUserIds: String? = null
    var roomIds: String? = null
    var currentUid: String? = null
    var currentName: String? = null
    private var progressDialog: ProgressDialog? = null

    private fun init() {
        SoftInputUtils.closeKeybord(this)
        Utils.init(this.applicationContext)
        mLoginFragment = LoginFragment.newInstance().apply {
            login = { userId, name, type, roomId, otherUserId ->
                loginSocket(userId, name, roomId, type, otherUserId)
            }
        }
        privateFragment = ChatPrivateFragment.newInstance("$currentName", "$otherUserIds", "$currentUid", 1).apply {
            sendMsg = {
                //发送
                LogUtils.d("MainActivity", "发送 privateEvent   $it")
                val map = HashMap<String, Any>()
                map["targetId"] = "$otherUserIds"
                map["userId"] = "$currentUid"
                map["msgType"] = "1"
                map["phoneModel"] = "android"
                map["msg"] = "$it"

                map["imCode"] = ("YZPRCODE")

                val jsonObject: JSONObject? = JSONObject(map as Map<*, *>)

                SingleSocket.getInstance().senMsg("privateEvent", jsonObject)
            }
        }
        groupFragment = ChatGroupFragment().apply {
            sendMsg = {
                if (!roomIds.isNullOrEmpty()) {
                    //发送
                    LogUtils.d("MainActivity", "发送 groupEvent   $it")
                    val map = HashMap<String, Any>()
//                   map["targetId"] = "msg"
                    map["userId"] = "$currentUid"
                    map["msgType"] = "2"
                    map["phoneModel"] = "android"
                    map["msg"] = it
                    map["roomId"] = roomIds!!

                    map["imCode"] = ("YZGRCODE")

                    val jsonObject: JSONObject? = JSONObject(map as Map<*, *>)
                    SingleSocket.getInstance().senMsg("groupEvent", jsonObject)
                }

            }
        }
        addFragmentToActivity(R.id.content, mLoginFragment!!, "mLoginFragment")
        addFragmentToActivity(R.id.content, privateFragment!!, "privateChat")
        addFragmentToActivity(R.id.content, groupFragment!!, "groupFragment")
        showAndHideFragments(mLoginFragment, listOf(privateFragment, groupFragment)) {}
        disposable = CompositeDisposable()
        disposable?.add(RxBus.register(SocketEvent::class.java)
                .subscribe {
                    socketEvent(it)
                })

    }

    private fun socketEvent(socketEvent: SocketEvent?) {
        if (socketEvent == null) return
        ToastUtils.showShort(socketEvent.msg)
        when (socketEvent.status) {
            1 -> {
                when (connetType) {
                    "groupEvent" -> {
                        switchChatRoom()
                        val loginEvent = PLVLoginEvent().apply {
                            onlineUserNumber = 1
                            timeStamp = System.currentTimeMillis()
                            user = PLVSocketUserBean().apply {
                                channelId = roomIds
                                nick = currentName
                                roomId = roomIds
                                uid = currentUid
                                userId = currentUid
                            }
                        }
                        groupFragment?.acceptLoginMessage(loginEvent)

                    }
                    "privateEvent" -> {
                        switchPrivateRoom()
                    }
                }
            }
            8 -> {//收到消息
                val localMessage = PLVGsonUtil.fromJson<PolyvLocalMessage>(PolyvLocalMessage::class.java, socketEvent.data.toString())

                when (socketEvent.type) {
                    2 -> {
                        //添加信息至列表
                        val dataList: MutableList<ChatBaseViewData<PolyvLocalMessage>> = ArrayList()
                        dataList.add(ChatBaseViewData(localMessage, PLVChatMessageItemType.ITEMTYPE_SEND_SPEAK))
                        groupFragment?.addChatMessageToList(dataList, false)
                    }
                    1 -> {

                        privateFragment?.addToStart(localMessage.userId, localMessage.message, true)
                    }
                }

            }
            else -> {
                dismissProgressDialog()
            }
        }
    }

    private fun loginSocket(
            userId: String,
            name: String,
            roomId: String?,
            type: String?,
            otherUserId: String?
    ) {
        connetType = type
        otherUserIds = otherUserId
        roomIds = roomId
        currentUid = userId
        currentName = name
        val mChatToken = ChatToken()
        val header = ChatHeader()
        header.setAppIds("yz123")
        header.setUserIds(userId)
        header.setAppTypes("3")
        mChatToken.setHeaders(header)


        val body = ChatBody()
        val timeStamp = System.currentTimeMillis()

//        time_stamp	String	是	13位毫秒级时间戳
//        app_secret	String	是	应用密钥 目前先用：MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT+cf8PMhEW52pPrbwYPTO8ANOL0JAqxmw6iFt/lXSnDP3hWxFb64sWtOYAeIOGBfqAY7V2DHcJO6O8nmFhxWGj9xP9Xi5Bmo3kt2Qi5gKfz61y3v8Rt54DT5zzGpGJ/pdJOj8uuFSSonldFKwowmMfJYdAMZj+WHI3h4zOd+/nQIDAQAB
//        app_id	String	String	应用id 目前先用：yz123
//        merchant_id	String	String	商户号id 目前先用：yz123
        val treeMap = TreeMap<String, String>()
        treeMap["app_id"] = "yz123"
        treeMap["app_secret"] = EncryptUtils.privateKey
        treeMap["merchant_id"] = "yz123"
        treeMap["time_stamp"] = "$timeStamp"
        val singStr = StringBuilder()
        singStr.append("{")

        treeMap?.onEach {
            singStr.append("\"").append(it.key).append("\"").append(":").append("\"").append(it.value).append("\"").append(",")
        }
        singStr?.replace(singStr.length - 1, singStr.length, "")
        singStr.append("}")
        if (!roomId.isNullOrEmpty()) {
            body.roomId = (roomId)
            header.setImCodes("YZGRCODE")

        } else {
            header.setImCodes("YZPRCODE")

        }
        val jsonS = singStr.toString()
        LogUtils.d("MainActivity", "  jsonS   $jsonS")

        val sign = EncryptUtils.md5(jsonS).toUpperCase()
        body.setSigns(sign)
        body.setTimeStamps("$timeStamp")
        LogUtils.d("MainActivity", "  sign   $sign")

        mChatToken.body = body
        val urr = StringBuilder(SingleSocket.IO_SERVER_URL)
        urr.append("?reqStr=").append(PLVGsonUtil.toJson(mChatToken))
        SingleSocket.getInstance().connectSocket(urr.toString())
        SoftInputUtils.closeKeybord(this)
        showProgressDialog("正在登录")

    }

    //切换到聊天室
    fun switchChatRoom() {
        showAndHideFragments(groupFragment, listOf(privateFragment, mLoginFragment)) {

        }
        dismissProgressDialog()


    }

    //切换到私聊
    fun switchPrivateRoom() {
        showAndHideFragments(privateFragment, listOf(mLoginFragment, groupFragment)) {
            privateFragment?.setTextInfo(currentName, otherUserIds, currentUid, 1)
        }
        dismissProgressDialog()

    }

    override fun onDestroy() {
        super.onDestroy()
        SingleSocket.getInstance().disConnect()
        disposable?.dispose()
        disposable?.clear()
    }

    fun AppCompatActivity.addFragmentToActivity(@IdRes id: Int, fragment: Fragment, tag: String) {
        try {
            if (!fragment.isAdded) {
                supportFragmentManager.beginTransaction().apply {
                    add(id, fragment, tag)
                }.commitNowAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showAndHideFragments(
            showFragment: Fragment?,
            hideFragments: List<Fragment?>?,
            action: () -> Unit
    ) {
        try {
            val b = supportFragmentManager.beginTransaction()
            hideFragments?.forEach {
                it?.let {
                    b.hide(it)
                }
            }
            showFragment?.let {
                b.show(it)
            }
            b.apply {
                action()
            }.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showProgressDialog(msg: String?) {
        if (true == progressDialog?.isShowing) {
            progressDialog?.dismiss()
        }
        if (this.isDestroyed || this.isFinishing) {
            return
        }
        progressDialog = ProgressDialog.show(this, null, msg, false, true, null)
    }

    fun dismissProgressDialog() {
        if (true == progressDialog?.isShowing) {
            progressDialog?.dismiss()
        }

    }

}
