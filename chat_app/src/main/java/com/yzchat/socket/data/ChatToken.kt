package com.yzchat.socket.data

class ChatToken {
    var header: ChatHeader? = null
    var body: ChatBody? = null

    fun setHeaders(header: ChatHeader?): ChatToken {
        this.header = header
        return this
    }

    fun setBodys(body: ChatBody?): ChatToken {
        this.body = body
        return this
    }

    class ChatHeader {
        var appType //:'3',
                : String? = null
        var userId //:'1',
                : String? = null
        var appId: String? = null
        var imCode //:'YZPRCODE'
                : String? = null

        fun setAppTypes(appType: String?): ChatHeader {
            this.appType = appType
            return this
        }

        fun setUserIds(userId: String?): ChatHeader {
            this.userId = userId
            return this
        }

        fun setAppIds(appId: String?): ChatHeader {
            this.appId = appId
            return this
        }

        fun setImCodes(imCode: String?): ChatHeader {
            this.imCode = imCode
            return this
        }
    }

    class ChatBody {
        var roomId //”:”1”,
                : String? = null
        var sign //”:””,”timeStamp”:””
                : String? = null
        var timeStamp: String? = null

        fun setRoomIds(roomId: String?): ChatBody {
            this.roomId = roomId
            return this
        }

        fun setSigns(sign: String?): ChatBody {
            this.sign = sign
            return this
        }

        fun setTimeStamps(timeStamp: String?): ChatBody {
            this.timeStamp = timeStamp
            return this
        }
    }
}