# JNI示例
> 本Demo实现的是在  Linux下  通过JNI调用C/C++ 实现的native 方法，native方法是指在java代码中带有native关键字的方法

主要步骤:
* 步骤1: 生成带有native方法的类所对应的c头文件
* 步骤2: 增加该头文件的一个实现.c文件，并编写HelloWorld逻辑
* 步骤3: 生成.so库文件(需要通过-I参数指定jni.h所在目录,即{jdk目录}/include)
* 步骤4: 生成Java主程序
* 步骤5: 通过加入环境变量java.library.path指定加载库文件，来运行java程序