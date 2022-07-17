#include "com_example_mengzhi_myapplication2_JniTestingActivity"

// NDK工具链里面的 log 库 引入过来
#include <android/log.h>

#define TAG "Derry"
// ... 我都不知道传入什么  借助JNI里面的宏来自动帮我填充
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)


// extern "C"： 必须采用C的编译方式，为什么，请看JNIEnv内部源码
// // 无论是C还是C++ 最终是调用到 C的JNINativeInterface，所以必须采用C的方式 extern "C"
// 函数的实现
extern "C"

JNIEXPORT  // 标记该方法可以被外部调用（VS上不加入 运行会报错， AS上不加入运行没有问题）
// Linux运行不加入，不报错,  Win 你必须加入 否则运行报错,   MacOS 还不知道

jstring // Java <---> native 转换用的

JNICALL // 代表是 JNI标记，可以少

// Java_包名_类名_方法名  ，注意：我们的包名 _     native _1

// JNIEnv * env  JNI：的桥梁环境    300多个函数，所以的JNI操作，必须靠他

// jobject jobj  谁调用，就是谁的实例  MainActivity this
// jclass clazz 谁调用，就是谁的class MainActivity.class

Java_com_derry_as_1jni_1project_MainActivity_getStringPwd
        (JNIEnv * env, jobject jobj) {


}

// 静态函数
//extern "C"
//JNIEXPORT jstring JNICALL
//Java_com_example_mengzhi_myapplication2_JniTestingActivity_getStringPwd2(JNIEnv *env, jclass clazz) {
//    // TODO: implement getStringPwd2()
//}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_mengzhi_myapplication2_JniTestingActivity_changeName(JNIEnv *env, jobject obj) {
   // 获取class
   jclass j_cls = env->GetObjectClass(obj);

   // 获取属性  L对象类型 都需要L
   // jfieldID GetFieldID(MainActivity.class, 属性名, 属性的签名)
   jfieldID j_fid = env->GetFieldID(j_cls, "name", "Ljava/lang/String;");

   // 转换工作
   jstring j_str = static_cast<jstring>(env->GetObjectField(obj , j_fid));

   // 打印字符串  目标
    char * c_str = const_cast<char *>(env->GetStringUTFChars(j_str, NULL));
    LOGD("native : %s\n", c_str);
    LOGE("native : %s\n", c_str);
    LOGI("native : %s\n", c_str);

    // 修改成 Beyond
    jstring jName = env->NewStringUTF("Beyond");
    env->SetObjectField(obj, j_fid, jName);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_mengzhi_myapplication2_JniTestingActivity_callAddMethod(JNIEnv *env, jobject thiz) {
    // 自己得到 MainActivity.class
    jclass  mainActivityClass = env->GetObjectClass(thiz);

    // GetMethodID(MainActivity.class, 方法名, 方法的签名)
   jmethodID j_mid = env->GetMethodID(mainActivityClass, "add", "(II)I");

   // 调用 Java的方法
   jint sum = env->CallIntMethod(thiz, j_mid, 3, 3);
   LOGE("sum result:%d", sum);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_mengzhi_myapplication2_JniTestingActivity_changeAge(JNIEnv *env, jclass mainActivityCls) {
    jfieldID ageFid = env->GetStaticFieldID(mainActivityCls, "age", "I");
    int age = env->GetStaticIntField(mainActivityCls, ageFid);
    env->SetStaticIntField(mainActivityCls, ageFid, age+1);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_mengzhi_myapplication2_JniTestingActivity_changeNumber(JNIEnv *env, jobject thiz) {
    jclass activityClass = env->GetObjectClass(thiz);
    jfieldID numberFid = env->GetFieldID(activityClass, "number", "D");
    double number = env->GetDoubleField(thiz, numberFid);
    env->SetDoubleField(thiz, numberFid, number + 7);
}