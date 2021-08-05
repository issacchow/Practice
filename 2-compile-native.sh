#! /bin/bash

jdk_home=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64

# for searching <jni.h>
jdk_include=$jdk_home/include
# for searching "jni_md.h"
jdk_include_linux=$jdk_include/linux

filename=./native/com_isc_NativeMethod.c

gcc -shared -fPIC -o ./native/libnativeMethod.so -I$jdk_include -I$jdk_include_linux -L. $filename
#gcc -o nativeMethod.a -I$jdk_include -I$jdk_include_linux -L. $filename
