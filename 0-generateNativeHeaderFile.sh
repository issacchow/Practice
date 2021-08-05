#!/usr/bin/env bash

# 本脚本主要是生成native方法的Header文件，并编译成共享库

# java编译，生成class文件
echo "compile java class"
cd ..
mvn clean compile

# 切换到 包的根目录位置,因为后面执行javah命令时，起始位置在包的根
echo ""
echo ""
echo "change directory"
cd target/classes
echo `pwd`

# 生成native 头文件
echo ""
echo ""
echo "javah -classpath . com.isc.NativeMethod"
javah -classpath . com.isc.NativeMethod

# 输出文件

echo ""
echo ""
echo "file state"
state ./com_isc_NativeMethod.h

### 开始编写com_isc_NativeMethod.h 的实现c文件
echo "please start to write Implement for com_isc_NativeMethod.h"