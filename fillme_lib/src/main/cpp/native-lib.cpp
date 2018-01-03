#include <jni.h>
#include <queue>


extern "C" {
JNIEXPORT jbooleanArray JNICALL
Java_com_patryk1007_fillme_calculations_FloodFillLinesCalculationAsync_invokeNativeFunction(
        JNIEnv *env, jobject instance,
        jintArray inJNIArray, jint width, jint height) {

    jint *pixels = env->GetIntArrayElements(inJNIArray, false);
    jsize pointsSize = env->GetArrayLength(inJNIArray);
    jbooleanArray filledPointsObject = env->NewBooleanArray(pointsSize);
    jboolean *filledPoints = env->GetBooleanArrayElements(filledPointsObject, false);

    int currentItem = 0, lastFilledField = 0;
    int *checkPointsArray = new int[pointsSize];

    checkPointsArray[lastFilledField] = 0;
    int newPixIndex;
    jint currentPixIndex;

    do {
        currentPixIndex = checkPointsArray[currentItem];
        currentItem++;
        newPixIndex = currentPixIndex + 1;

        if (newPixIndex < pointsSize) {
            if (!filledPoints[newPixIndex]) {
                filledPoints[newPixIndex] = 1;
                if (/*pixels[newPixIndex] >> 24 <= alphaLevel*/
                        pixels[newPixIndex] == 0 || currentPixIndex < width) {
                    lastFilledField++;
                    checkPointsArray[lastFilledField] = newPixIndex;
                }
            }

            newPixIndex = currentPixIndex + width;
            if (newPixIndex < pointsSize) {
                if (!filledPoints[newPixIndex]) {
                    filledPoints[newPixIndex] = 1;
                    if (/*pixels[newPixIndex] >> 24 <= alphaLevel*/
                            pixels[newPixIndex] == 0 || currentPixIndex % width == 0) {
                        lastFilledField++;
                        checkPointsArray[lastFilledField] = newPixIndex;
                    }
                }
            }
        }

        newPixIndex = currentPixIndex - 1;
        if (newPixIndex > 0) {
            if (!filledPoints[newPixIndex]) {
                filledPoints[newPixIndex] = 1;
                if (/*pixels[newPixIndex] >> 24 <= alphaLevel*/ pixels[newPixIndex] == 0) {
                    lastFilledField++;
                    checkPointsArray[lastFilledField] = newPixIndex;
                }
            }

            newPixIndex = currentPixIndex - width;
            if (newPixIndex > 0) {
                if (!filledPoints[newPixIndex]) {
                    filledPoints[newPixIndex] = 1;
                    if (/*pixels[newPixIndex] >> 24 <= alphaLevel*/ pixels[newPixIndex] == 0) {
                        lastFilledField++;
                        checkPointsArray[lastFilledField] = newPixIndex;
                    }
                }
            }
        }
    } while (lastFilledField >= currentItem);

    return filledPointsObject;
}
}