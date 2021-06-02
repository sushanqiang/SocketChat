package com.yzchat.socket;

import android.text.TextUtils;
import android.util.Log;

import com.yzchat.socket.event.SocketEvent;
import com.yzchat.socket.utils.LogUtils;
import com.yzchat.socket.utils.RxBus;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;


/**
 * Created by ssq on 2021/3/14.
 * 测试Socket连接，通信
 */

public class SingleSocket {
    private static volatile SingleSocket mSingleSocket;
    private Socket mSocket;
    private String TAG = "SingleSocket";
    public static final String IO_SERVER_URL =  "http://24-chat.yzwill.cn";////"http://10.0.2.24:8083"//http://24-chat.yzwill.cn?userId=12&roomId=1";//https://socket-io-chat.now.sh/";
    //"https://socketio-chat-h9jt.herokuapp.com/"
    private Emitter.Listener privateEvent, groupEvent, onConnectTimeout, onDisconnect, onConnect, onConnectError, onConnecting, onNewMessage, onError, onReconnectAttempt, onReconnectFailed, onReconnectError, onReconnect, onReconnecting;
    private OkHttpClient okHttpClient;

    public static SingleSocket getInstance() {
        if (mSingleSocket == null) {
            synchronized (SingleSocket.class) {
                if (mSingleSocket == null) {
                    mSingleSocket = new SingleSocket();
                }
            }
        }
        return mSingleSocket;
    }

    private SingleSocket() {

        onError = new Emitter.Listener() {
            public void call(Object... var1) {

                Log.d(TAG, "onError: " + Arrays.toString(var1));
            }
        };
        onReconnectAttempt = new Emitter.Listener() {
            public void call(Object... var1) {
                Log.d(TAG, "onReconnectAttempt: " + Arrays.toString(var1));
            }
        };
        onReconnectFailed = new Emitter.Listener() {
            public void call(Object... var1) {
                RxBus.post(new SocketEvent().setStatus(-2).setMsg("重连接失败"));

                Log.d(TAG, "onReconnectFailed: " + Arrays.toString(var1));
            }
        };
        onReconnectError = new Emitter.Listener() {
            public void call(Object... var1) {
                RxBus.post(new SocketEvent().setStatus(-2).setMsg("重连接失败"));

                Log.d(TAG, "onReconnectError: " + Arrays.toString(var1));
            }
        };
        onReconnect = new Emitter.Listener() {
            public void call(Object... var1) {
                Log.d(TAG, "onReconnect: " + Arrays.toString(var1));
                RxBus.post(new SocketEvent().setStatus(1).setMsg("重连接"));

            }
        };
        onReconnecting = new Emitter.Listener() {
            public void call(Object... var1) {
                RxBus.post(new SocketEvent().setStatus(2).setMsg("重连接中"));

                Log.d(TAG, "onReconnecting: " + Arrays.toString(var1));
            }
        };

        onConnectTimeout = new Emitter.Listener() {
            public void call(Object... var1) {
                RxBus.post(new SocketEvent().setStatus(-3).setMsg("连接超时"));

                Log.d(TAG, "onConnectTimeout: " + Arrays.toString(var1));
            }
        };
        onConnect = new Emitter.Listener() {
            public void call(Object... var1) {
                RxBus.post(new SocketEvent().setStatus(1).setMsg("连接成功"));
                Log.d(TAG, "连接成功 onConnected mSocket.id=" + mSocket.id());
                if (var1 != null && var1.length > 0) {
                    Log.d(TAG, "onConnected Object=" + var1[0].toString());
                }
            }
        };
        onDisconnect = new Emitter.Listener() {
            public void call(Object... var1) {
                Log.e(TAG, "onDisconnect " + var1);


            }
        };
        onConnectError = new Emitter.Listener() {
            public void call(Object... var1) {
                Log.e(TAG, "onConnectError " + var1[0].toString());
                RxBus.post(new SocketEvent().setStatus(-1).setMsg("连接失败"));

            }
        };

        onConnecting = new Emitter.Listener() {
            public void call(Object... var1) {
                Log.d(TAG, "onConnecting " + IO_SERVER_URL);
            }
        };
        onNewMessage = new Emitter.Listener() {
            public void call(Object... var1) {
                Log.e(TAG, "onNewMessage " + var1[0].toString());

            }
        };
        privateEvent = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "收到消息：privateEvent " + args);

                if (args.length > 0) {
                    Object object = args[0];

                    RxBus.post(new SocketEvent().setStatus(8).setType(1).setObject(object));

                    Log.d(TAG, "收到消息：privateEvent " +object.toString());
                    if (args.length > 1) {
                        Log.d(TAG, "收到消息：privateEvent " + args[1].toString());
                    }
                }

            }
        };
        groupEvent = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "收到消息：groupEvent " + args);

                if (args.length > 0) {
                    Object object = args[0];
                    RxBus.post(new SocketEvent().setStatus(8).setType(2).setObject(object));
                    Log.d(TAG, "收到消息：groupEvent " + object.toString());
                    if (args.length > 1) {
                        Log.d(TAG, "收到消息：groupEvent " + args[1].toString());
                    }
                }

            }
        };
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(5000);
        dispatcher.setMaxRequestsPerHost(5000);
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.connectionPool(new ConnectionPool(5000, 5000, TimeUnit.MINUTES));
        mBuilder.dispatcher(dispatcher);
        mBuilder.retryOnConnectionFailure(true);
        okHttpClient = mBuilder.build();
    }

    public void connectSocket(String urls) {
        Log.d(TAG, "connectSocket " + urls);

        if (mSocket == null) {
            try {
                IO.setDefaultOkHttpCallFactory(okHttpClient);
                IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
                IO.Options options = new IO.Options();
                options.query = "EIO=3&transport=websocket";
                options.transports = new String[]{WebSocket.NAME};
                URL url = new URL(urls);
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null);
                Log.e(TAG, "uri=" + uri);
                mSocket = IO.socket(uri, options);

            } catch (URISyntaxException | MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            disConnect();
        }
        connect();
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void connect() {
        if (mSocket != null) {
            mSocket.on("transport", args -> {
                Transport transport = (Transport) args[0];
                transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.v(TAG, "Caught EVENT_REQUEST_HEADERS after EVENT_TRANSPORT, adding headers");
                        Map<String, List<String>> mHeaders = (Map<String, List<String>>) args[0];
                        mHeaders.put("Authorization", Arrays.asList("Basic bXl1c2VyOm15cGFzczEyMw=="));
                    }
                });
            });
            mSocket.on("ping", args -> Log.d(TAG, "EVENT_PING " + args));
            mSocket.on("pong", args -> Log.d(TAG, "EVENT_PONG " + args));
            mSocket.on("privateEvent", this.privateEvent);
            mSocket.on("groupEvent", this.groupEvent);
            mSocket.on("message", this.onNewMessage);
            mSocket.on("connect", this.onConnect);
            mSocket.on("disconnect", this.onDisconnect);
            mSocket.on("connecting", this.onConnecting);
            mSocket.on("connect_error", this.onConnectError);
            mSocket.on("connect_timeout", this.onConnectTimeout);
            mSocket.on("reconnect", this.onReconnect);
            mSocket.on("reconnecting", this.onReconnecting);
            mSocket.on("reconnect_error", this.onReconnectError);
            mSocket.on("reconnect_failed", this.onReconnectFailed);
            mSocket.on("reconnect_attempt", this.onReconnectAttempt);
            mSocket.on("error", this.onError);
            mSocket.connect();
            LogUtils.d(TAG, "connect  init ");
        }
    }

    public void senMsg(String event, Object msg) {
        LogUtils.d(TAG, "senMsg：  " + event + "  msg=" + msg);
        LogUtils.d(TAG, "privateEvent: " + privateEvent);
        LogUtils.d(TAG, "groupEvent: " + groupEvent);

        if (mSocket != null && !TextUtils.isEmpty(event) && null != (msg)) {
            LogUtils.d(TAG, "senMsg： mSocket ");
            mSocket.emit(event, msg, (Ack) args -> LogUtils.d(TAG, "senMsg：回调  " + event));
        }
    }

    public void disConnect() {
        if (mSocket != null) {
            mSocket.off();
            mSocket.disconnect();
            mSocket = null;
        }
    }
}
