package com.example.testproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testproject.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.activities.start

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2000)
            start<InboxActivity>()
            finish()
        }

    }
}