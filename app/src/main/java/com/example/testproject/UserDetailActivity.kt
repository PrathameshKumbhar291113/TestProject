package com.example.testproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.testproject.data.Data
import com.example.testproject.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.getParcelableExtra<Data>("userDetails")

        if (args!= null){
            var userImage = args.avatar.toString()
            binding.userDetailImage.load(userImage)
            binding.userDetailFirstName.text = args.first_name.toString()
            binding.userDetailLastName.text = args.last_name.toString()
            binding.userDetailEmail.text = args.email.toString()
        }
    }
}