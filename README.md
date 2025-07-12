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
【java反射机制】
【BUG】
① MainTemplate.java.ftl报错变量名为空。
    在GenerateCommand中的变量名要和MainGenerator中的变量名要对应起来。
② 关于jar打包：
    在code_generator_basic中对项目打包，该jar包在code_generator_basic中的target目录下，运行该jar包会报找不到文件路径。
    解决办法：只有将jar包拖到code_generator_basic目录下才能正确运行。(笨办法)

2025/06/15
开发代码生成器 制作工具。
明确需求和业务
    目标：实现本地的代码生成器的制作工具。(能够将一个项目制作为可以动态定制部分内容的代码生成器)
    实现的效果：开发人员可直接使用该制作工具生成模板文件(FTL)、数据模型(配置文件)、代码生成器jar包(包含动静Generator、制作jar包)。
    实现思路
        1、工具要实现什么功能？ 并如何提高效率？
        2、如何自动生成模板文件以及相应的配置文件？ 如何在源文件中提取参数？【这一步是最复杂的，所以在实现之前，均是假设已有一套现成的模板文件，也已经知道哪些参数需要填充】
        3、如何自动生成命令行？
        4、如何动态生成jar包？
    需求拆解
        1、开发基础代码生成器制作工具；移除第一阶段中的硬编码，通过读取预设的配置文件自动制作代码生成器的可执行文件(jar文件)。

        2、配置文件增强。以快速制作Springboot初始化模板项目生成器为目标，增加更多参数。

        3、模板制作工具。自动生成配置文件和FTL动态模板文件。

2025/06/16
2025/06/17
开发基础代码生成器制作工具，移除硬编码。

1、重新构建新项目：code_generator_maker，进行代码和目录结构优化。
2、构建元信息json文件与相对应的类。
    使用GsonFormatPlus插件将JSON文件转为Java类代码。在Meta.class中按快捷键【Alte + Shift + Insert 打开配置菜单】
    Files => FileInfo、Models => ModelInfo、ModelInfo.defaultValue 类型改为Obejct.
将Json内容填充到实体对象。
    步骤：先读取元信息文件，使用Hutool的JSONUtil.toBean方法，将JSON字符串转为对象。
    为了每次获取meta对象时避免重复创建对象，使用一种【单例模式】，保证项目运行期间只有一个meta对象被创建，并且能轻松获取。
    【关于volatile关键字】
        java中内存模型：是基于CPU的缓存模型来建立的。分为两个概念，一个是线程的工作内存(相当于CPU的高速缓存)，一个是主内存。还定义了一些操作，read load use assign store write
        线程0读取主内存中普通变量，是只读一次还是多次？
            对于普通变量，线程在第一次读取时从主内存中获取值，之后会缓存在工作内存中，后续可直接从工作内存中获取，不会自动刷新主内存的最新值。
        线程0读取有volatile的变量是如何读的？
            对于volatile变量，每次读取都直接会从主内存获取最新值，不会缓存到工作内存。
        volatile保证线程有序性。
        【详细讲解volatile关键字以及并发编程】：https://juejin.cn/post/7070091066044579876

3、动态生成数据模型文件(即自定义命令类DataModel)。
    先定义ftl文件，
    modelInfo.defaultValue?c将任何类型的变量(String或boolean)都转为字符串。
4、动态生成Picocli命令类。
5、动态生成代码生成文件
6、程序构建jar包
2025/06/21
    下载完成maven包后，运行构建jar包main，出现错误((in directory "D:\Study\programmes\code_generator\code_generator_maker\generated\acm-template-pro-generator\pom.xml"): CreateProcess error=2, 系统找不到指定的文件。)
    ，但转天重新打开电脑后又可运行。
7、程序封装脚本
2025/07/05
    类似错误：[ERROR] /D:/Study/programmes/code_generator/code_generator_maker/generated/acm-template-pro-generator/src/main/java/code/generator/model/DataModel.java:[14,25] ��Ҫ';'
    原因：DataModel.java.ftl文件中，变量赋值写成 == 
【注意】
    写ftl文件时，要注意变量最后的分号不要遗漏；
    注意包的路径要与新生成的包中路径为准；
    注意变量名书写，如：outputPath写为ouputPath；
    注意@option中属性名要写全，如漏写description = ；
8、测试验证
【注意】
Exception in thread "main" cn.hutool.core.io.IORuntimeException: File not exist: D:\Study\programmes\code_generator\code_generator_demo_projects\acm_template_pro\.gitignore
对应文件夹中是_gitignore文件而不是.gitignore

2025/07/07
制作工具优化
1、可移植性优化:增加.source模板文件夹，取消硬编码，
2、功能性优化:增加项目解释文件.md；制作精简代码生成器(复制了代码生成器一些必要文件)
3、健壮性优化: 1、规则梳理；2、自定义异常类；3、编写校验类；4、圈复杂度优化(两个操作，反转if(Alt + Enter)；提取成新方法(Refactor->Extract Method))
4、可扩展性：
    1、【枚举值定义】：什么是枚举值？为什么这么定义？代码是什么意思?(在meta.enumss)
    枚举是一种编程语言特性，它允许开发者定义一组命名的 常量值 ，使代码更易读和维护。
    2、【模板方法模式】：模板(父类)、实现模板类(子类)
    其中【protected关键字】？一个类中某方法使用这个关键字代表：1、同包中可以调用，子类也可以调用；2、背后意思：普通用户不用继承这个类，则使用不到专业功能；
专业用户可以继承这个类，获得对专业功能的使用权。3、实践表明，使用private修饰变量才是更安全的。应为可以设置getter和setter，在这两个方法中使用不同关键字达到不同效果。

2025/07/10
配置增强
实现原则：
    配置文件中，fileConfig应专注与文件生成相关的逻辑。
    配置文件中，modelConfig应专注于数据模型的定义。只是定义有某个参数，但具体作用是什么不放在modelConfig中来控制。
1、参数控制文件生成(needGit控制.gitignore文件生成)
    modelConfig.models下新增needGit(boolean类型)，控制.gitignore是否生成。
    fileConfig.files下的_gitignore加入condition，在ftl文件中作为判断条件。
    (发现GenerateCommand.java.ftl中option里names一个判断条件也写作addr，造成后续出错，随记录)
2、同参数控制多个文件生成
    先判断有无groupKey(fileConfig.files)
        判断有无condition(groupKey组中的condition)
    判断有无conditiong(fileConfig.files)
    注意：拼写错误</#if>不是<#/if>
【FreeMarker的宏定义】
3、同参数控制代码和文件生成(2中已经实现)
4、定义一组相关的参数
    目的：为避免配置之间可能会有名称冲突。对参数进行分组，各组下参数互相隔离，避免命名冲突。
5、定义可选开启参数组
    目的：用户输入的某个开关参数，来控制是否要让用户输入其他的参数组。
    








    