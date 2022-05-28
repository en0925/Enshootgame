package com.example.enshootgame

import android.content.pm.ActivityInfo
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.enshootgame.databinding.ActivityMainBinding
import kotlinx.coroutines.*

@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {

    lateinit var img: ImageView
    lateinit var mysv:MySurfaceView
    lateinit var binding: ActivityMainBinding
    var flag:Boolean = false
    lateinit var job: Job
    lateinit var en : ImageView
    lateinit var fly1:Fly

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        GlideApp.with(this)
            .load(R.drawable.myphoto)
            .circleCrop()
            .override(800, 600)
            .into(binding.en)


        binding.img.setOnClickListener({
            if (flag){
                flag = false
                binding.img.setImageResource(R.drawable.start)
                job.cancel()
            }
            else{
                flag = true
                binding.img.setImageResource(R.drawable.stop)
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(flag) {
                        delay(10)
                        binding.mysv.fly1.update()

                        var canvas: Canvas = binding.mysv.surfaceHolder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        })
    }
}