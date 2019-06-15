# VaingloryGo
An Android app that queries Vainglory data.

English │[中文](https://github.com/VcotyQin/VaingloryGo/blob/master/README-cn.md)

### Overview
- MVP framework
- Rxjava2
- Fast Android Networking Library is network framework
- Api specification of json-api
- Develop with [AIDE](https://www.android-ide.com/)(Android IDE on Android)


[![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/coolapk.png)](https://www.coolapk.com/apk/vcoty.vainglory.go)

### Screenshots
| | | | | | | |
|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|:----------:|
|    ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/Search_page.png)       |      ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/Main.png)     |   ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/Match.png)       |     ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/player.png)      |    ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/Player's_data page.png)      |       ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/player.png)    |      ![image](https://raw.githubusercontent.com/VcotyQin/VaingloryGo/master/Screenshots/Settings.png)|

### Code specification
- All "Activity" inherit from "BaseActivity". All "Fragment" inherit from "BaseFragment".

- Other references (the above specifications are preferred) [Alibaba code specification for Android](https://www.jianshu.com/p/f5a55dff62f0)

### Data sources
- Player personal data from [vgpro.gg](vgpro.gg).
- Match statistics from [vgpro.gg](vgpro.gg).
- Other game date from official Vainglory.
- Use [Bmob](bmob.cn) to manage the heroes' head image, outfit image and related information of historical versions.

### Thanks for open source projects
- [LitePal](https://github.com/LitePalFramework/LitePal.git) datebase framework
- [Rxjava2](https://github.com/ReactiveX/RxJava.git) Reactive Extensions for the JVM
- [RxAndroid2](https://github.com/ReactiveX/RxAndroid.git) Rxjava's expansion of Android
- [RxLifecycle](https://github.com/trello/RxLifecycle.git) Manage the Lifecycle
- [Rxbinding](https://github.com/JakeWharton/RxBinding.git) Handling UI widgets in the form of rxjava
- [Fast Android Networking Library](https://github.com/amitshekhariitbhu/Fast-Android-Networking.git) Network framework
- [Picasso](https://github.com/square/picasso.git) Picture loading
- [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper.git) Quickly create RecyclerView Adapter
- [AnimatedPieView](https://github.com/razerdp/AnimatedPieView.git) Very graceful pie chart.
- [Moshi](https://github.com/square/moshi.git) json serialization framework
- [Moshi-jsonapi](https://github.com/kamikat/moshi-jsonapi.git) Moshi's extension of json-api

### License
[Apache License 2.0](https://github.com/VcotyQin/VaingloryGo/blob/master/LICENSE)

### Change log (No English)
####v1.5.7(2019/03/06)
```
[优化] 加载更多的UI
[修复] 加载更多时的bug
[修复] 存在三丰的对局统计信息加载不出来
```
####v1.5.6(2019/03/01)<上架应用宝>(他们不收)
````
[新增] 中国红主题
[新增] 对局详情页中点击一项可查看玩家基础信息
[优化] 所有卡片的圆角稍微增大
[优化] 玩家信息页地区信息搬到了底部
[优化] 捐赠入口更具人性化
[优化] 玩家信息页的段位卡片采用日本传统色配色方案
[优化] 对话框更具MD风格
[修复] 切换语言时不彻底
[修复] 模式筛选时更新时出现的bug
[修复] 一些能够导致崩溃的bug
````

####v1.5.5(2018/10/20)
````
[修复] 部分用户内存溢出导致的崩溃
[修复] 最近的比赛无法查看统计信息
````

####v1.5.4(2018/10/03)
````
[优化] 降低了访问超时的概率(这是今后的重点，慢慢来)
[修复] 一些会崩溃的bug
````

####v1.5.3(2018/09/24)
各位中秋快乐!🌕
从1.0.6 alpha升级上来的清一下数据再用，不然会崩的！!!
    [修复] 修复了一些你们可能遇不到的bug😂
    [优化] 微调布局
    [优化] 根据部分用户需求，分段展示规则的「根据游戏模式」选项中，大乱斗将展示闪电战的分段

####v1.5.2(2018/09/21)
    [修复] 切换地区时崩溃
    [修复] 更新数据库失败后崩溃
    [修复] 更新数据库后无法查看详细数据
    [优化] 微调布局

####v1.5.1(2018/09/19)
    紧急修复一个bug

#### v1.5.0(2018/09/19)
    船新版本！船新版本！船新版本！ｸﾞｯ!(๑•̀ㅂ•́)و✧
    时隔两月，让大家久等了，VGO以全新的姿态与大家相逢在了酷安。
    新增加了许多功能，优化了性能，懒得写了，自己去探索吧。
    由于服务器在国外，国内我已经尽力优化了，用ti子会快点。(国内慢的问题依旧是以后优化的重点，不用反馈了)
    由于这个版本重构了项目的，建议卸掉原来的版本重新下载，或者清除一下数据。(崩溃了别找我(/ω＼))

#### v1.0.6 alpha 
感谢捐赠:
    @Alllie

    [修复] 很久没有玩过的玩家查看个人信息时崩溃
    [修复] 部分没玩过排位的玩家查看个人信息时崩溃

#### v1.0.5 alpha
感谢捐赠:
    @口吻

    [新增] 少女粉与知乎蓝主题色
    [优化] 更好的适配类原生Android
    [优化] 多状态提示更加人性化
    [优化] 主题更换逻辑及UI
    [优化] 优化更新逻辑
    [优化] 再次减小了安装包
    [修复] 上个版本写的bug
    [修复] 最近参赛的列表滑动过快导致无法加载更多

#### v1.0.4 alpha
我来紧急修几个hug

    [优化] 换回了原来的接口，又可以随便玩了
    [修复] 全部模式下不会加载更多的bug

由于学习原因，我今后会尽量保持周更的！如果有有延迟，请见谅。😀

#### v1.0.3 alpha
感谢捐赠:
    @我就是喜欢你呀i

    [修复] 部分设备打开底部装备栏时崩溃的bug
    [修复] 部分玩家个人页面崩溃的bug

    这个临时的接口每分钟只有10次调用次数，你们悠着点

#### v1.0.2 alpha
    [新增] 战绩列表模式筛选
    [优化] 图标换了(感谢@hk流星😀)
    [优化] 小幅度提升性能
    [修复] 捐赠时会崩溃(我也不清除治好没有，大部分没毛病，以前有问题的现在试试有没有问题)
    [修复] 几个小bug而已

#### v1.0.1 alpha
    [新增] Android 5.0 以下的版本无法使用的提示(别问我为什么不适配5.0以下的,换手机吧)
    [新增] 点击建议条目直接搜索
    [优化] 提升性能
    [优化] 小细节
    [优化] 最近对局列表的K/D/A向右移，防止分辨率较低的用户显示与模式重叠
    [优化] 英文翻译(虽然没有国外用户，但将来会有的😂)
    [修复] 清空所有条目再撤销时不添加
    [修复] 更新本地数据库之后装备显示的bug

#### v1.0.0 alpha
    第一个版本
