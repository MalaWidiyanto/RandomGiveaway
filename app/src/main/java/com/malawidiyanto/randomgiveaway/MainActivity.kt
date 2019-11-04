package com.malawidiyanto.randomgiveaway

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri


class MainActivity : AppCompatActivity() {

    var txvRandom: TextView? = null
    var handler: Handler? = null
    var runnable: Runnable? = null

    val min = 0
    var max = 0
    val duration: Long = 50

    lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = Paper.book().read("userList", ArrayList<User>())

        for (i in 0..userList.size - 1) {
            println("x : ($i) : " + userList.get(i))
        }
        max = userList.size-1


        txvRandom = findViewById(R.id.txv_random)
        val btnStart = findViewById<MaterialButton>(R.id.btn_start)
        val btnStop = findViewById<MaterialButton>(R.id.btn_stop)
        val btnAdd = findViewById<MaterialButton>(R.id.btn_add)
        val icLogo = findViewById<ImageView>(R.id.ic_logo)

        icLogo.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/erlin_shop14"))
            startActivity(browserIntent)
        }

        btnStart.setOnClickListener {
            if (userList.size > 0)
                start()
            else
                Toast.makeText(this, "KANDIDAT MASIH KOSONG", Toast.LENGTH_SHORT).show()
        }
        btnStop.setOnClickListener {
            stop()
        }
        btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
            finish()
        }

        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                val random = Random().nextInt(max - min + 1) + min
                handler?.postDelayed(this, duration)
                txvRandom?.text = userList.get(random).name
            }
        }
    }

    fun start() {
        handler?.post(runnable)
    }

    fun stop() {
        handler?.removeCallbacks(runnable)

        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(1250L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(10))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
