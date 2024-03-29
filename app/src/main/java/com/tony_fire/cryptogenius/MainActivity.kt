package com.tony_fire.cryptogenius

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.messaging.FirebaseMessaging
import com.tony_fire.cryptogenius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoPlayer()
        binding.button.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token2 = task.result
                Log.d("Token", "Token: + $token2" )
            }
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun videoPlayer() {

        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        val mediaItem: MediaItem = MediaItem.fromUri("https://dl.dropbox.com/s/nidapzfo0ntz2cj/The%20Crypto%20Genius.mp4?dl=0")
        binding.videoView.player = player
        binding.videoView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        player.setMediaItem(mediaItem)
        player.prepare()



    }
}