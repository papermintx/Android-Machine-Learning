package com.dicoding.asclepius.view.result

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_PREDICTION = "extra_prediction"
        const val EXTRA_CONFIDENCE = "extra_confidence"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra(EXTRA_IMAGE)
        val prediction = intent.getStringExtra(EXTRA_PREDICTION)
        val confidence = intent.getStringExtra(EXTRA_CONFIDENCE)

        binding.resultImage.setImageURI(stringToUri(image))
        binding.resultText.text = prediction

        binding.confidenceText.text = getString(R.string.confidence_score, confidence ?: "N/A")
    }

    private fun stringToUri(string: String?): Uri {
        return Uri.parse(string)
    }
}
