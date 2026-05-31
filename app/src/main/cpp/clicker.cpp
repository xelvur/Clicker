#include <jni.h>
#include <string>
#include <cstdint>

// Хранит счётчик кликов на стороне C++
static int64_t click_count = 0;

extern "C" {

JNIEXPORT jlong JNICALL
Java_com_clicker_MainActivity_nativeClick(JNIEnv* env, jobject /* this */) {
    ++click_count;
    return static_cast<jlong>(click_count);
}

JNIEXPORT void JNICALL
Java_com_clicker_MainActivity_nativeReset(JNIEnv* env, jobject /* this */) {
    click_count = 0;
}

} // extern "C"
