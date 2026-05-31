#include <jni.h>
#include <cstdint>
#include <chrono>

// Хранит счётчик кликов на стороне C++
static int64_t click_count = 0;


// Для фпс
static int64_t frame_count = 0;
static double last_fps = 0.0;
static auto fps_start = std::chrono::steady_clock::now();

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

JNIEXPORT jdouble JNICALL
Java_com_clicker_MainActivity_nativeGetFps(JNIEnv* env, jobject)
{
	++frame_count;
	auto now = std::chrono::steady_clock::now();
	double elapsed = std::chrono::duration<double>(now - fps_start).count();
	if(elapsed >= 0.1) // Обновляем каждую 100мс
	{
		last_fps = frame_count / elapsed;
		frame_count = 0;
		fps_start = now;
	}
	return last_fps;
}

} // extern "C"
