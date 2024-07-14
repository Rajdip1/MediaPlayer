package com.example.day30mediaplayers

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var medialplayer : MediaPlayer
    var totalTime : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        medialplayer = MediaPlayer.create(this,R.raw.music1)
        medialplayer.setVolume(1f,1f)
        totalTime = medialplayer.duration

        supportActionBar?.hide()
        val btnplay = findViewById<ImageView>(R.id.play)
        val btnpause = findViewById<ImageView>(R.id.pause)
        val btnstop = findViewById<ImageView>(R.id.stop)
        val btnseekBar = findViewById<SeekBar>(R.id.seekBar)

        btnplay.setOnClickListener {
            medialplayer.start()
        }
        btnpause.setOnClickListener {
            medialplayer.pause()
        }
        btnstop.setOnClickListener {
            medialplayer?.stop()
            medialplayer.reset()
            medialplayer.release()
        }

        //when user changes the time stamp of music, reflect that change
        btnseekBar.max = totalTime
        btnseekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    try {
                        medialplayer.seekTo(progress)
                    }catch (e:Exception){
                        Log.d("SeekBarChangeError","Error seeking to $progress",e)
                    }

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                btnseekBar?.let {
                    medialplayer.seekTo(it.progress)
                }
                Log.d("SeekBar", "User stopped tracking SeekBar")
            }

        })

        //change the seekBar position based on the music
        val handler = Handler()
        handler.postDelayed(object:Runnable{
            override fun run() {
                try {
                    btnseekBar.progress=medialplayer.currentPosition
                    handler.postDelayed(this,1000)
                }catch (exception:Exception){
                    btnseekBar.progress=0
                }
            }
        },0)
    }
}

