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
    
2025/7/12
模板制作工具
    问题1：更改元信息数据模型配置，并进行分组后，之前编写的ftl模板无法正确生成内容。
    (动态模板和元信息配置有很强的绑定关系)
    问题2：每个文件需要自己挖坑，容易出现遗漏，成本高。
    (人工提前准备动态模板，项目文件越多，使用成本越高)
    解决办法：唐制作工具自动给项目挖坑，并生成相互对应的动态模板文件和元信息配置。
制作工具的作用只是提高效率，无法覆盖所有的定制需求！
我们之前如何制作ftl和配置文件的：
    1、先指定一个原始的、待“挖坑”的输入文件
    2、明确文件中需要被动态替换的内容和模型参数(前两步需要用户自主完成)
    3、自己编写 FreeMarker FTL 模板文件
    4、自己编写生成器的元信息配置，包括基本信息、文件配置、模型参数配置(3-4可用制作工具完成)

一、基础功能实现
(1)基本流程实现
    1、提供输入参数: 生成器基本信息、原始项目目录、原始文件、模型参数
    2、基于字符串替换算法，使用模型参数的字段名称来替换原始文件的指定内容，并使用替换后的内容来创建 FTL 动态模板文件
    3、使用输入信息来创建 meta.json 元信息文件
【问题】为什么先设置对象，然后再修改属性反映到那个引用。
    Meta.FileConfig fileConfig = new Meta.FileConfig();
    meta.setFileConfig(fileConfig);
    fileConfig.setSourceRootPath(sourceRootPath);
【解答】Java对象是引用传递，fileConfig 变量指向堆内存中的同一个对象。即使先设置了 fileConfig 到 meta 中，后续修改 fileConfig 的属性仍然会反映到 meta 中的那个引用

(2)工作空间隔离
    【问题】上述代码会在原始项目内生成模板和元信息配置，对原项目污染。
    【解决】每次制作模板，不直接修改原始项目文件。而是先复制原项目到一个临时的目录，在该目录下完成文件的生成和处理。
    新增.temp文件
    在TemplateMaker原有基础上新增复制目录逻辑：
    1、需要用户传入 originProjectPath 变量代表原始项目路径
    2、每次制作分配一个唯一 id（使用雪花算法），作为工作空间的名称，从而实现隔离
    3、通过 FileUtil.copy 复制目录
    4、修改变量 sourceRootPath 的值为复制后的工作空间内的项目根目录
    【雪花算法】

(3)分布制作能力
    在制作模板时，要一步步替换参数、制作模板。
    所以，该制作工具要有分布制作、追加配置的能力，具体有以下3点：
    1、不可重复输入;
    2、后续制作不用再次复制原始项目，而是在原有文件上多次追加，覆盖新文件；
    3、在原有文件上多次追加，覆盖新文件。
    要想实现这个能力首先让制作工具【有状态】
【有状态和无状态】
    有状态：是指程序或请‌求多次执行时，下一次执行保留对上一次执行的‍记忆。
    无状态‌：是指每次程序或请求执行，都像是第一次执行一样，没有任何历史‍信息。很多 Restful ؜API 会采用无状态的设计，能够节省服务器的资源占用。

    有状态实现
        2个要素：唯一标识和存储
    多次制作实现
        根据id判断出并非首次制作，该做调整有：
        1、非首次制作，不需要复制原始项目文件
        2、非首次制作，可以在已有模板的基础上再次挖坑。
            由于模板文件为ftl,则用ftl作为判断条件。有，则不为首次制作，在该模板文件基础上去替换内容
        3、非首次制作，不需要重复输入已有元信息，而是在此基础上覆盖和追加元信息配置
            可以通过是否存在meta.json文件来判断应该新增还是修改配置
        在追加完配置后需要去重，否则可能出现多个一模一样的模型参数或文件信息。
        【StreamAPI和Lambda表达式简化代码】
2025/07/16
二、更多功能实现
(1)单次制作多个模板文件
    实现多个模板文件同时制作：1、支持输入目录，同时处理该目录下的所有文件；2、支持输入多个文件路径，同时处理这些文件。
    1、支持输入文件目录
    抽取出制作单个文件模板的方法makeFileTemplate。(接受单个文件，模板信息，替换文本，sourcRootPath等参数。返回Fileinfo文件信息)
【注意】sourcRootPath也要记得进行转义
    输入文件路径是目录，使用Hultool的loopFiles方法递归遍历并获取目录下的所有文件列表。
    分别处理每个文件。
    优化：莫格文件内容没有呗参数替换，则不生成模板，而是以静态生成的方式记录到元信息配置中。
    2、支持输入多个文件路径
    将传入makeFileTemplate方法中的inputFilePath改为inputFilePathList。
2025/07/16
(2)文件过滤功能
    目的：找到分散在不同目录中的，包含某个名称的文件。
    需求场景：用户输入一个开关参数控制帖子功能相关文件是否生成。这些文件分散在不同目录中，无法通过直接指定一个目录完成制作。
    改动：不直接使用Meta创建文件信息配置，而是
    文件过滤归纳了2类配置：
        1、过滤范围：根据文件名称、或文件内同过滤；
        2、过滤规则：包含contains、前缀匹配startsWith、后缀匹配endsWith、正则regex、相等equals。
2025/07/17
    【@Builder的作用】
    主要作用：简化对象创建过程;实现了建造者模式(Builder Pattern)
    1、链式调用。可以通过 .属性名(值) 的方式逐步设置属性，代码更易读。如 builder().xxx(value).build()
    2、避免构造方法参数过多。
    3、支持部分属性初始化。可以只设置部分属性，未设置的属性会使用默认值。
    4、建造者模式通常用于创建不可变对象。因为build()方法会返回一个新对象，而不是修改已有的对象。
    5、比Setter更安全。setter可以单独调用，如 obj.setXxx(value)
    【.filter(file -> doSingleFileFilter(fileFilterConfigList, file))解释】
    1、file -> doSingleFileFilter(fileFilterConfigList, file) 是一个Lambda表达式
        等价于
        .filter(new Predicate<File>() {
            @Override
            public boolean test(File file) {
                return doSingleFileFilter(fileFilterConfigList, file);
            }
        })
    2、函数逻辑
        对Stream中的每个File对象，调用doSingleFileFilter方法。
        返回 true（保留文件）或 false（过滤掉文件）。
    【Predicate<T>接口】
    (1) 包含一个boolean test(T t),用于条件判断场景。
    (2) 默认方法：and,or,negate(非),
    【Lambda表达式】
    本质上，是一个匿名或未命名的方法。
    (1) 单个表达式主体
        () -> System.out.println("Lambdas are great");
    (2) 由代码块组成的主体
        () -> {
            double pi = 3.1415;
            return pi;
        };
    (3) 带参数的Lambda表达式
        (n) -> (n%2)==0 // 其中 n 是传递给lambda表达式的参数
    (4) Lambda表达式和Stream API
2025/07/18
(3)文件分组
    1、分组策略：一个完整的文件配置(TemplateMakerFileConfig)对应一次分组。即配置files列表中的所有问价都属于同组。
    (跨目录下、同功能的文件设置为一个组)
    fileConfig = fileGroupConfig + fileInfo
    原fileConfig = fileInfo
    2、追加配置能力。
    支持多次制作时追加配置的能力，可以增加新的分组也可以在同分组下新增文件。
    【小Bug】第一次制作的模板文件在文件配置信息中为dynamic，第二次在追加时，变为了static。
    



    