package com.tony_fire.cryptogenius
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.retrofit.RetrofitInstance
import com.retrofit.UserInfo
import com.tony_fire.cryptogenius.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userField()
        videoPlayer()

    }
    private fun videoPlayer() {

        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        val mediaItem: MediaItem = MediaItem.fromUri("https://dl.dropbox.com/s/nidapzfo0ntz2cj/The%20Crypto%20Genius.mp4?dl=0")
        binding.videoView.player = player
        binding.videoView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        player.setMediaItem(mediaItem)
        player.prepare()



    }
    private fun userField() {
        binding.regButton.setOnClickListener {
            val ed1stName = binding.ed1stName.text.toString().trim()
            val ed2stName = binding.edSecondName.text.toString().trim()
            val ed_email = binding.edEmail.text.toString().trim()
            val ed_tel = binding.edPhone.text.toString().trim()
            val countrypicker = binding.codePicker.selectedCountryNameCode.toString()



            if(ed1stName.isEmpty()){
                binding.ed1stName.error = "Enter your Name"
                binding.ed1stName.requestFocus()
                return@setOnClickListener

            }

            if(ed2stName.isEmpty()){
                binding.edSecondName.error = "Enter your Last Name"
                binding.edSecondName.requestFocus()
                return@setOnClickListener

            }

            if(ed_email.isEmpty()){
                binding.edEmail.error = "Enter your Email"
                binding.edEmail.requestFocus()
                return@setOnClickListener

            }
            if(ed_tel.isEmpty()){
                binding.edPhone.error = "Enter your phone"
                binding.edPhone.requestFocus()
                return@setOnClickListener

            }

            val userinfo = UserInfo(countrypicker,ed_email,ed1stName,"6016df00e01be55aa1abf07a",ed2stName,ed_tel,"hknmvcrtp")
            val call : Call<InfoClass> = RetrofitInstance.api.signUpUser(userinfo)

            call.enqueue(object : Callback<InfoClass> {
                override fun onResponse(call: Call<InfoClass>, response: Response<InfoClass>) {
                    if(response.isSuccessful){
                        Log.d("Info", "Status: ${response.body()?.status}" )
                        Log.d("Info", "Pr: ${response.body()?.data?.pr}" )
                        Log.d("Info", "Redirect: ${response.body()?.data?.redirect}" )

                        when {
                            response.body()?.data?.pr.equals("success") -> {
                                binding.regButton.setBackgroundResource(R.drawable.bg_color_select)
                                val i = Intent(
                                    this@RegistrationActivity,
                                    ThanksActivity::class.java
                                )
                                startActivity(i)
                            }
                            response.body()?.data?.pr.equals("exist") -> {
                                Toast.makeText(this@RegistrationActivity,"You are already registered!",
                                    Toast.LENGTH_LONG).show()
                            }
                            response.body()?.data?.pr.equals("redirect") -> {
                                val i = Intent(this@RegistrationActivity,WebViewActivity::class.java).apply {
                                    putExtra("Url",response.body()?.data?.redirect.toString())
                                }
                                startActivity(i)
                            }
                        }

                    }
                    else {
                        Toast.makeText(applicationContext,"Check your internet connection", Toast.LENGTH_SHORT).show()
                        return
                    }

                }

                override fun onFailure(call: Call<InfoClass>, t: Throwable) {
                    Toast.makeText(applicationContext,"Check your internet connection", Toast.LENGTH_SHORT).show()
                }


            })



        }

    }
}