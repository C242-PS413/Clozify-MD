package com.c242ps413.clozify.ui.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c242ps413.clozify.R
import com.c242ps413.clozify.data.api.response.MoodResponse
import com.c242ps413.clozify.data.api.retrofit.ApiConfig
import com.c242ps413.clozify.data.retrofit.ApiService
import com.c242ps413.clozify.databinding.ActivityUploadBinding
import com.c242ps413.clozify.ui.UserDataHolder
import com.c242ps413.clozify.ui.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString)
            binding.previewImageView.setImageURI(imageUri)

            // Trigger automatic mood analysis
            imageUri?.let { uri ->
                Toast.makeText(this, "Analyzing mood...", Toast.LENGTH_SHORT).show()
                uploadImageToServer(uri)
            }
        } else {
            Toast.makeText(this, "No image to display", Toast.LENGTH_SHORT).show()
        }

        binding.uploadButton.setOnClickListener {
            getRecommendations()
        }
    }

    private fun uploadImageToServer(uri: Uri) {
        val file = uriToFile(uri)
        if (file != null) {
            // Disable button and show progress indicator
            binding.uploadButton.isEnabled = false
            binding.progressIndicator.visibility = android.view.View.VISIBLE

            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val apiService: ApiService = ApiConfig.getApiServiceMood()
                    val response: MoodResponse = apiService.uploadImage(body)

                    withContext(Dispatchers.Main) {
                        binding.progressIndicator.visibility = android.view.View.GONE
                        binding.textViewDeskripsi.text = "Your Mood Today: ${response.predicted_mood ?: "Unknown mood"}"
                        UserDataHolder.userData.emotion_category = response.predicted_mood
                        Toast.makeText(this@UploadActivity, "Mood analysis successful: ${response.predicted_mood}", Toast.LENGTH_SHORT).show()
                        binding.uploadButton.isEnabled = true
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        binding.progressIndicator.visibility = android.view.View.GONE
                        Toast.makeText(this@UploadActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("UploadActivity", "Error uploading image", e)
                    }
                }
            }
        } else {
            Toast.makeText(this, "Failed to process image file!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRecommendations() {
        binding.uploadButton.isEnabled = false
        binding.progressIndicator.visibility = android.view.View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService: ApiService = ApiConfig.getApiServiceMood()
                val response = apiService.getRecommendations(UserDataHolder.userData).execute()

                withContext(Dispatchers.Main) {
                    binding.progressIndicator.visibility = android.view.View.GONE
                    if (response.isSuccessful) {
                        val recomData = response.body()
                        if (recomData != null) {
                            Toast.makeText(this@UploadActivity, "Recommendations fetched successfully!", Toast.LENGTH_SHORT).show()
                            // Navigate to HomeFragment after getting recommendations
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, HomeFragment())  // Replace with your fragment container ID
                                .addToBackStack(null)  // Optional, to add fragment to back stack
                                .commit()
                        } else {
                            Toast.makeText(this@UploadActivity, "No recommendations available.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@UploadActivity, "Failed to fetch recommendations. Error: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                    }

                    binding.uploadButton.isEnabled = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressIndicator.visibility = android.view.View.GONE
                    Log.e("UploadActivity", "Error fetching recommendations", e)
                    Toast.makeText(this@UploadActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()

                    binding.uploadButton.isEnabled = true
                }
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        val contentResolver = contentResolver
        val tempFile = File.createTempFile("temp_", ".jpg", cacheDir)

        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("UploadActivity", "Failed to convert URI to File", e)
            null
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
