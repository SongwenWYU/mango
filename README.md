# mango 
<br>
	是一个较为完善的搜索引擎.使用heritrix爬虫爬去网页信息,这里针对人民网进行了相关的爬去策略定制,其他网站的爬去,可自行根据相关网站的具体情况进行定制爬去策略.在搜索索引的建立是使用Lucene进行的.前端使用spring框架和dwr的异步刷新.由于是毕业设计的作品,较为粗糙,在这里放出来,仅供参考,有很多不足的地方,勿喷.

##mango_Heritrix 爬虫端
<br>
	爬虫端,基于heritrix(具体相关heritrix文档,可百度!).导入项目后,在项目中可能会在Heritrix.java文件中"FileURLConnection"位置出现错误,这个错误是引用了sun包的原因,只需要右击项目选择设置(Properties)-java compoler-error/warrings 勾选enable project specific settings,拉动滚动条展开deprecated and restricted api选项,在 forbidde reference**** 选项中将error改为warring或者另外个忽略就行了.
<br>
	运行时,run as java application 然后选择main函数,输入Heritrix搜索就会出来了.<br>
	还有一种运行在tomcat环境下的,具体的可以百度一下.

##mango_index 索引建立端
<br>
	索引建立的模块,其中的jar包存放于mango_Heritrix项目中的lib文件夹下.
<br>
	这里的策略是现将爬去下来的网页进行一个处理,出去冗余的信息,然后保存为一个个的文件,再进行索引的建立.很显然这不是一个好的方案.
<br>
	在项目下的app.properties和config.xml文件是相关的配置.详细的后续补上.
<br>
	先运行mango.extractor.htmlparser.HtmlparserMain下的main函数,然后再运行mango.extractor.htmlparser.HtmlparserMain的main函数.

##mango_search 网页前端
<br>
	搜索引擎的前端网页.导入eclipse中,便可run起来了.同样,项目里含有配置文件.

<br><br><br><br><br>
这个项目是一个不怎么完整的项目,也是个不怎么好的项目,只是放上来作为初学者的参考.在性能还有其他方面多有欠缺,希望见谅.

