# 2025/06/02
创建新项目，并使用.ignore插件创建.gitignore文件来忽视不必要传入github的文件；
创建ACM模板，后续会基于这些模板来定制生成。


2025/06/03
创建code_generator_basic项目（一定要在新窗口打开！！！鱼皮，你的提醒为什么不放在前面QAQ），导入依赖(依赖报错的话，先检查groupId、artifactId的内容是否拼写错误)；
生成静态文件，并用main方式实现。(对File包不熟悉~)(嗯？鱼给出的StaticGenerator中，没有检查输入输出是否存在，这个感觉还是蛮重要的)
动态文件生成实现。包括：静态文件生成器类.
【用到的工具/包：File、Hutool】
    疑问1：因为看到生成静态文件的方式是从一个目录中复制到另一个目录（尤其是看到需要输入ACM模板目录本地地址后）有疑问：用户登录在线代码生成网站
    获得某一代码自定义模板时，获取静态文件的功能中的静态文件的本地地址需要如何获取？是直接写死的吗？还是说要有一个功能，检测用户想获取哪一个模板
    再根据该模板id or 名字获取改模板下全部静态文件的地址？(更倾向于后者)
    如何判断该模板文件夹下哪些文件是静态呢？是定义好这些文件是静态，还是规定某些文件后缀就是静态文件的后缀？

2025/06/04
生成动态文件。引入FreeMarker依赖。FreeMarker中宏、内建函数。【至少掌握一种模板引擎用法】
FreeMarker的使用可分三步：1、引入版本号+要操作的文件地址+引入字符集；2、创建模板（创建FreeMarker中模板对象）；3、创建数据模型（就是有组织的放数据）.
具体请看：code_generator_basic/src/test/java/FreeMarkerTest.java
【Map\HarshMap\List\ArrayList】
动态文件生成实现。包括：1、模板配置类；2、动态文件的FTL模板文件；3、动态文件生成器类.
【小技巧：编写FTL文件时，可以先将后缀名不改为ftl而是直接使用原后缀名，这样可以自动整理文件的缩进。然后在改变后缀名为ftl】
这里注意：在动态文件生成器类中，引入了import lombok.Data包，这样不用写set方法。
Lombok注解代码生成工具：通过添加注解的方式，不需要为类编写getter或eques方法，同时可以自动化日志变量。@Data 注解在类，生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
经过不太完全的实验：FreeMarker获取参数是通过getter来实现的，并且对于boolean类型的值，isPool()或getPool()两种方法名均能识别。

2025/06/09
命令行开发。使用picocli包。
命令模式：将操作进行封装，每个操作都是一个独立的命令类，所以新增命令操作时不需要改动现有代码。【将操作抽象，很关键，减少代码改动】
抽象命令：命令(父类/接口),具体命令(子类，具体实现类，负责执行具体操作)
学习命令模式开发：https://github.com/liyupi/yuindex
【BUG】
① MainTemplate.java.ftl报错变量名为空。
    在GenerateCommand中的变量名要和MainGenerator中的变量名要对应起来。
② 关于jar打包：
    在code_generator_basic中对项目打包，该jar包在code_generator_basic中的target目录下，运行该jar包会报找不到文件路径。
    解决办法：只有将jar包拖到code_generator_basic目录下才能正确运行。(笨办法)
    