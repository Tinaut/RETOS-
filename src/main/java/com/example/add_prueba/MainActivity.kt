package com.example.add_prueba

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.add_prueba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initListeners()
        notification()
    }
    private fun initListeners(){
        val bannerIntent = Intent(this,BannerActivity::class.java)
        binding.btBanner.setOnClickListener { startActivity(bannerIntent) }
    }
    private fun notification(){
        binding.btNotificacion.setOnClickListener {

            Toast.makeText(this, "Te llegara un mensaje programado a las 19:00", Toast.LENGTH_SHORT).show()

        }
        binding.btMaps.setOnClickListener {
            val init = Intent(this,GoogleMaps::class.java)
            startActivity(init)
        }
    }

}