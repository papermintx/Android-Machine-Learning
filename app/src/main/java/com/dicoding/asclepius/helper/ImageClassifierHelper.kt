package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import javax.inject.Inject


class ImageClassifierHelper @Inject constructor(
    var threshold: Float = 0.1f,
    var maxResults: Int = 1,
    val modelName: String = "cancer_classification.tflite",
    val context: Context,
) {
    private var imageClassifier: ImageClassifier? = null
    var classifierListener: ClassifierListener? = null

    init {
        setupImageClassifier()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            inferenceTime: Long
        )
    }

    private fun setupImageClassifier() {
        val optionBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(maxResults)
            .setScoreThreshold(threshold)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)

        optionBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionBuilder.build()
            )
        } catch (e: Exception) {
            classifierListener?.onError(e.message.toString())
            Log.e(TAG, "Error setting up image classifier.", e)
        }
    }

    fun classifyImage(imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()

        val bitmapImage = convertUriToBitmap(imageUri)
        val processedTensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmapImage))

        val startTime = SystemClock.uptimeMillis()
        val classificationResults = imageClassifier?.classify(processedTensorImage)
        val elapsedTime = SystemClock.uptimeMillis() - startTime

        classifierListener?.onResults(
            classificationResults,
            elapsedTime
        )
    }

    @Suppress("DEPRECATION")
    private fun convertUriToBitmap(uri: Uri): Bitmap {
        val bitmapResult: Bitmap?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            bitmapResult = ImageDecoder.decodeBitmap(source)
        } else {
            bitmapResult = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        return bitmapResult!!.copy(Bitmap.Config.ARGB_8888, true)
    }



    companion object{
        private const val TAG = "ImageClassifierHelper"
    }

}