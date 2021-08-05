#include "com_isc_NativeMethod.c"

JNIEXPORT void JNICALL
Java_com_isc_NativeMethod_say
  (JNIEnv *, jobject, jstring){

   printf("Hello World");

}

/*
 * Class:     com_isc_NativeMethod
 * Method:    getSomeContent
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL
Java_com_isc_NativeMethod_getSomeContent
  (JNIEnv *, jobject){



}