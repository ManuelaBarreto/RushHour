package com.example.rushhours
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Chronometer
import android.os.SystemClock
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class PlayActivity : AppCompatActivity() {
    private lateinit var backBtn: AppCompatImageView
    private lateinit var chronometer: Chronometer
    private var running = false
    private var dX = 0f
    private var dY = 0f
    private var boardSize = 390.0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play)

        // Set up the back button
        backBtn = findViewById(R.id.logo_menu_btn)
        backBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@PlayActivity,
                    MainActivity::class.java
                )
            )
        }

        // Set up the chronometer
        chronometer = findViewById(R.id.chronometer)

        val finishLevelButton: Button = findViewById(R.id.finishLevelButton)

        startChronometer()

        finishLevelButton.setOnClickListener {
            stopChronometer()
        }

        // Set up the car movements

        val boardContainer = findViewById<View>(R.id.boardContainer)

        boardContainer.post {
            boardSize = 390f
        }

        // Configurar movimentação para cada carro
        val car1 = findViewById<ImageView>(R.id.car1)
        val car2 = findViewById<ImageView>(R.id.car2)
        val car3 = findViewById<ImageView>(R.id.car3)
        val car4 = findViewById<ImageView>(R.id.car4)
        setupMovableCar(car1, isHorizontal = true)
        setupMovableCar(car2, isHorizontal = false)
        setupMovableCar(car3, isHorizontal = false)
        setupMovableCar(car4, isHorizontal = true)
    }

    private fun startChronometer() {
        if (!running) {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            running = true
        }
    }

    private fun stopChronometer() {
        if (running) {
            chronometer.stop()
            running = false
        }
    }

    private fun setupMovableCar(car: ImageView, isHorizontal: Boolean) {
        car.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Salva o deslocamento inicial
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    true // Consumir o evento
                }

                MotionEvent.ACTION_MOVE -> {
                    // Calcula a nova posição
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY

                    // Restrinja o movimento com base no eixo
                    if (isHorizontal) {
                        val clampedX = newX.coerceIn(0f, (boardSize - view.width).toFloat())
                        view.x = clampedX
                    } else {
                        val clampedY = newY.coerceIn(0f, (boardSize - view.height).toFloat())
                        view.y = clampedY
                    }
                    true // Consumir o evento
                }

                MotionEvent.ACTION_UP -> {
                    // Finalizando o movimento, considera que foi um "clique"
                    view.performClick()
                    true
                }

                else -> false
            }
        }
    }

}