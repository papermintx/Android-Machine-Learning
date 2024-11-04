package com.dicoding.asclepius.view.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.view.result.ResultActivity
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            cropImage(uri)
            mainViewModel.setCurrentImageUri(uri)
        } else {
            showToast("Image selection failed")
        }
    }

    private val cropImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(result.data!!)
            if (resultUri != null) {
                mainViewModel.setCurrentImageUri(resultUri)
            } else {
                showToast("Crop result is null")
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            showToast("Crop error: ${cropError?.message}")
        }
    }


    private fun observeCurrentImageUri() {
        mainViewModel.currentImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                showImage(uri)
            } else {
//                binding.previewImageView.setImageDrawable(null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }

        observeViewModel()
        observeCurrentImageUri()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage(uri: Uri?) {
        if (uri != null) {
            binding.previewImageView.setImageURI(uri)
        }
        else{
            binding.previewImageView.setImageDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.ic_place_holder))
        }
    }

    private fun analyzeImage() {
        binding.progressIndicator.visibility = View.VISIBLE
        mainViewModel.currentImageUri.value?.let { imageUri ->
            mainViewModel.analyzeImage(imageUri)
        } ?: showToast("Image is null")

        binding.progressIndicator.visibility = View.GONE
    }

    private fun cropImage(uri: Uri) {
        val options = UCrop.Options().apply {
            setCompressionQuality(90)
            setToolbarColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
            setActiveControlsWidgetColor(ContextCompat.getColor(requireContext(), R.color.teal_500))
            setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.teal_800))
            setToolbarWidgetColor(Color.WHITE)
        }

        val uniqueFilename = "cropped_image_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, uniqueFilename))

        val uCrop = UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .withOptions(options)

        cropImageLauncher.launch(uCrop.getIntent(requireContext()))
    }


    private fun observeViewModel() {
        mainViewModel.classificationResult.observe(viewLifecycleOwner) { result ->
            result?.let { (prediction, confidenceScore) ->
                mainViewModel.currentImageUri.value?.let { uri ->
                    saveAnalyzeHistory(uri, prediction, confidenceScore)
                }
                moveToResult(prediction, confidenceScore)
                binding.progressIndicator.visibility = View.GONE
                mainViewModel.setCurrentImageUri(null)
                showImage(mainViewModel.currentImageUri.value)
            }

        }

        mainViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showToast(it)
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }

    private fun saveAnalyzeHistory(uri: Uri, result: String, score: String) {
        mainViewModel.saveAnalyzeHistory(uri, result, score)
    }

    private fun moveToResult(prediction: String, confidenceScore: String) {
        mainViewModel.currentImageUri.value?.let {
            val intent = Intent(requireActivity(), ResultActivity::class.java).apply {
                putExtra(ResultActivity.EXTRA_IMAGE, it.toString())
                putExtra(ResultActivity.EXTRA_PREDICTION, prediction)
                putExtra(ResultActivity.EXTRA_CONFIDENCE, confidenceScore)
            }
            mainViewModel.setResultsNull()
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

