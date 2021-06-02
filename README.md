
* 温馨提示： 文档按照远智项目（Android版，最近一次提交于2019/02/12； 当前版本versionCode=1 ，versionName=1.0）情况如实填写的、请务必仔细阅读，
            如有遗漏、错误等，请按照自己实际编程环境以及具体版本开发。



 项    目： 远智教育

 介    绍： 1、原生开发（基于android studio 3.2 正式版 ,gradle-4.6-all.zip,com.android.tools.build:gradle:3.2.1,java1.8）



 主要结构：
    1、kotlin+java
    2、rxjava+retrofit+mvp
    3、app+module 分模块形式


 主要功能：


          一：核心业务
            学员报名缴费、在线学习
            智米商城
            消息中心

          二：第三方
           保利威视直播、支付宝、微信、极光推送

          三：js+android 交互
            注：1、目前很多页面延用h5界面。具体查看业务
                2、需要将个人登陆信息传入webView

          四：工具类
           base基本控件和封装类
           图片选择，日期选择等库
           glide
        maven {
            url "https://maven.aliyun.com/repository/public"
        }
        maven {
            url "https://maven.aliyun.com/repository/google"
        }
        maven {
            url "https://maven.aliyun.com/repository/gradle-plugin"
        }
         maven { url "https://maven.aliyun.com/repository/jcenter" }

          五：调试工具

                1、网络调试
                     postman.exe
                     facebook的stetho（用法，打开谷歌浏览器、 打开地址 chrome://inspect/#devices ，选择设备即可 注意：需要翻墙）
                2、图片压缩（可选）
                     地址：https://tinypng.com/
                3、其他
                   androidprofiler检测

          六：应用升级


                1、注意：每次版本发布要在根目录build.gradle中给versionCode至少加1（versionCode 必须修改） ，versionName 可按照要求修改，但必须大于上一版本。


          七：多渠道打包
             引入第三方时，也应该把相应的混淆信息加入proguard-rules.pro
             暂无

其他命令：
         adb devices
         adb install "apk的绝对路径"
         adb uninstall xxx.apk

       ./gradlew -v 版本号
       ./gradlew clean 清除app目录下的build文件夹
       ./gradlew build 检查依赖并编译打包
       ./gradlew tasks 列出所有task
         gradlew apkJiagu  加固后多渠道打包
       ./gradlew -q app:dependencies    查看第三方依赖
       * 解决 greadle Permission Denied 问题：  输入 chmod +x gradlew
         gradlew :app:dependencies --configuration compile  查看依赖 或者          gradlew :app:dependencies --configuration implementation
         gradlew processDebugManifest --stacktrace
         gradlew build --stacktrace //错误信息
         //根据环境打包
         gradlew assembleYzwill -PSERVER_URL="\"https://app.yzou.cn\"" -PENVIRONMENT="\"生产环境"" -PWEB_URL="\"https://app.yzou.cn\""
         //根据输入参数进行打包，域名：yz_host （89  ,87   ,192 ,188 .yzwill），具体查看 yzApplication defaultTestUrl
         //注意，输入的是string，要加双引号,  YZ_LOG 为更新日志，可以不用传，只支持英文
         gradlew assembleYzwill -PYZ_HOST="\"89\"" -PYZ_LOG="\"xxxxxx\""  upload2Fir
         gradlew assembleYzwill -PYZ_HOST="\"89\""  upload2Fir
         //jenkins
         assembleYzwill -PSERVER_URL="\"$SERVER_URL\"" -PENVIRONMENT="\"$ENVIRONMENT\"" -PWEB_URL="\"$WEB_URL\""  upload2Fir
         //直接（默认取最后一个）上传apk 到 fir
         gradlew upload2Fir

         //启动命令
         adb命令 : adb shell am start -S -W 包名/启动类的全限定名 ， -S 表示重启当前应用
         //dump进程内存，来查看内存情况
         adb shell dumpsys meminfo [pacakgename]
         //查看线程总数据(总进程)
         adb shell  cat /proc/sys/kernel/threads-max

         //查看某一进程线程汇总数据
         adb shell cat /proc/3424/status



         //在上面的基础上查看
          ps -T -p 3424 | grep RxCachenThread | wc -l
          //watch -n 1 -d ' ps -T -p 3424 | grep XXX | wc -l'
         //启动时长
         adb shell am start -S -W cn.yzou.yzxt/cn.yzwill.android.main.view.SplashActivity
         //包名
         adb shell dumpsys window | findstr mCurrentFocus
         adb -s GPG4C19117002756 shell dumpsys window | findstr mCurrentFocus

         adb shell dumpsys activity com.shoplex.shoplex（这里是你要查看的应用的包名）

         //kill 进程
         1、 按Home按键退出你的程序；
         2、 找到该APP的进程ID
          adb shell ps

          # 找到你APP的报名
          adb shell ps | grep cn.yzou.yzxt

          # 按照上述命令操作后，看起来是这样子的:
          # USER      PID   PPID  VSIZE  RSS     WCHAN    PC         NAME
          # u0_a198   24607 160   827940 22064 ffffffff 00000000 S your.app.package

          # 通过PID将你的APP杀掉  需要root
          adb shell kill  24607
          或者 暴力方式 adb shell am force-stop  cn.yzou.yzxt

svn 清理失败    问题查看 ：https://note.youdao.com/share/?id=1c8b84a39e6533b6f12c449ca1386a7b&type=note#/
  4：打开cmd执行 sqlite3 .svn/wc.db "select * from work_queue"  命令，可以看到是那些文件有问题
  5：sqlite3 .svn/wc.db "delete from work_queue"出现下图的情况说明清理成功


开发账号：


测试账号：
密码：888888

13802351293
15500000000
13635963937
15500007744
15500006836
18966663333

线上帐号：




//保利威视信息
userId（账号ID）： 52554be7b4
AppID（应用ID）：  eynogj82jc
AppSecret（应用密匙）： d3b81fd951f14fe783df5085bce76328
//加密秘钥和加密向量，在后台->设置->API接口中获取，用于解密SDK加密串
//值修改请参考 http://dev.polyv.net/2018/liveproduct/l-api/rule/sign/
SDKEncryptString : C641964EB5944BD31C064FB8DC559D96

//友盟appkey
5aa1e5b2a40fa35edb00006d

//MobSDK
appKey "29ed460685b2a"
appSecret "4a28807f4d4e580ed92a7e9673e7b2b3"
ShareSDK {
        //平台配置信息
        devInfo {
            Wechat {
                appId "3c221ac1a34c78ca"
                appSecret "64020361b8ec4c99936c0e3999a9f249"
            }
            WechatMoments {
                appId "3c221ac1a34c78ca"
                appSecret "64020361b8ec4c99936c0e3999a9f249"
            }
        }
    }

//jpush
JPUSH_APPKEY =c4ce820ba819794db99088ee

//百度地图生生sha1
keytool -importkeystore -srckeystore D:\myproject\appproject\YuanZhi\key\yzxt.jks -destkeystore D:\myproject\appproject\YuanZhi\key\yzxt.jks -deststoretype pkcs12
keytool -list -alias yzxt -keystore "D:\myproject\appproject\YuanZhi\key\yzxt.jks" -storepass yZ@8uhb!@ -keypass yZ@8uhb!@
//阿里银行卡检查
get请求地址："https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo=6210910002001951239&cardBinCheck=true"

//网络环境问题，设置代理时会出现以下问题
 ----------------- 鉴权错误信息 ------------
    sha1;package:0C:F3:38:BD:70:E9:4A:63:72:E7:04:0C:74:31:7E:C6:B5:D1:B2:C6;cn.yzou.yzxt
    key:rbx50PuUzwkupZSzulnAvs3IpOavRflW
    errorcode: -11 uid: -1 appid -1 msg: httpsPost failed,IOException:java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
    请仔细核查 SHA1、package与key申请信息是否对应，key是否删除，平台是否匹配
    errorcode为230时，请参考论坛链接：

Android 透明度
透明度	16进制表示
100  %	00
99   %	03
98   %	05
97   %	07
96   %	0A
95   %	0D
94   %	0F
93   %	12
92   %	14
91   %	17
90   %	1A
89   %	1C
88   %	1E
87   %	21
86   %	24
85   %	26
84   %	29
83   %	2B
82   %	2E
81   %	30
80   %	33
79   %	36
78   %	38
77   %	3B
76   %	3D
75   %	40
74   %	42
73   %	45
72   %	47
71   %	4A
70   %	4D
69   %	4F
68   %	52
67   %	54
66   %	57
65   %	59
64   %	5C
63   %	5E
62   %	61
61   %	63
60   %	66
59   %	69
58   %	6B
57   %	6E
56   %	70
55   %	73
54   %	75
53   %	78
52   %	7A
51   %	7D
50   %	80
49   %	82
48   %	85
47   %	87
46   %	8A
45   %	8C
44   %	8F
43   %	91
42   %	94
41   %	96
40   %	99
39   %	9C
38   %	9E
37   %	A1
36   %	A3
35   %	A6
34   %	A8
33   %	AB
32   %	AD
31   %	B0
30   %	B3
29   %	B5
28   %	B8
27   %	BA
26   %	BD
25   %	BF
24   %	C2
23   %	C4
22   %	C7
21   %	C9
20   %	CC
19   %	CF
18   %	D1
17   %	D4
16   %	D6
15   %	D9
14   %	DB
13   %	DE
12   %	E0
11   %	E3
10   %	E6
9   %	E8
8   %	EB
7   %	ED
6   %	F0
5   %	F2
4   %	F5
3   %	F7
2   %	FA
1   %	FC
0   %	FF