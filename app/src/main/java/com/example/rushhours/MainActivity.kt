package com.example.rushhours

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var playBtn : Button
    private lateinit var levelsBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        playBtn = findViewById(R.id.play_btn)
        levelsBtn = findViewById(R.id.levels_btn)

        playBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    PlayActivity::class.java
                )
            )
        }
    }
}