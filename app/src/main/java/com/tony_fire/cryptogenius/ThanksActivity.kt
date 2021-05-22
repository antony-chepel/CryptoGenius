package com.tony_fire.cryptogenius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tony_fire.cryptogenius.databinding.ActivityThanksBinding

class ThanksActivity : AppCompatActivity() {
    private lateinit var binding : ActivityThanksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}