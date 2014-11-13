SMSInterception
===============

短信拦截，并且中文分词然后分析。是否是祝福语等、并且按照时间拦截短信，还可以选择多种回复方式。


代码说明：
UI：
	MainActivity 是展示主界面，接受到的垃圾短信List。 其中删除、回复的按钮事件在listadapter 的MainListAdapter中实现。
主界面电话号码转换成人名也是依靠MainListAdapter提供的getContactNameByPhoneNumber()方法，是那电话号码去手机通讯录中进行匹配。
	SettingActivity是第一个设置界面。设置拦截的时间的界面
	SettingRecActivity是第二个设置界面设置回复内容等。
	
数据存储：用的Android ContentProvider实现。代码在provider包内。
	provider储存的是数据库，是用来储存垃圾短信的。
	SettingShare是用来储存设置信息的。是一个XML文件。key-value的形式储存。
短信拦截：
	系统受到短信发出系统广播。所以只需要比系统自带的短信系统先拦截到短信信息进行处理。
	短信拦截在包receriver 的SMSReceiver内	。	
	