package com.example.mobiledevelopment.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.data.response.ListDestinationItem
import com.example.mobiledevelopment.databinding.ActivityDetailBinding
import com.example.mobiledevelopment.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {


    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailStory = intent.getParcelableExtra<ListDestinationItem>(KEY) as ListDestinationItem
        binding.backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        setupUi(detailStory)
        setupView()
    }

    private fun setupUi(data : ListDestinationItem){
        showLoading(true)
        Glide.with(this@DetailActivity)
            .load(data.photoUrl)
            .fitCenter()
            .into(binding.image)

        data.apply {
            binding.title.text = name
            binding.description.text = description

            binding.hour.apply {
                binding.hour.text = context.getString(R.string.hour, estimatedTime.toString())
            }
            binding.distance.apply {
                binding.distance.text = context.getString(R.string.distance,distance.toString())
            }
            binding.rating.apply {
                binding.rating.text = context.getString(R.string.rating, rating.toString())
            }
        }
        showLoading(false)
    }

    private fun setupView(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



    companion object{
        const val KEY = "key_story"
    }
}