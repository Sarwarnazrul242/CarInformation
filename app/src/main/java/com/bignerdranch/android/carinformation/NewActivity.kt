package com.bignerdranch.android.carinformation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.carinformation.R
import com.bignerdranch.android.carinformation.uitel.getProgressDrawable
import com.bignerdranch.android.carinformation.uitel.loadImage
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

//get data
        val carIntent = intent
        val carName = carIntent.getStringExtra("name")
        val carInfo = carIntent.getStringExtra("info")
        val carImg = carIntent.getStringExtra("img")

        val carPrice = carIntent.getStringExtra("price")
        val carEngineType = carIntent.getStringExtra("engineType")

        name.text = carName
        info.text = carInfo
        img.loadImage(carImg, getProgressDrawable(this))

        name.setOnClickListener {
            val detailsIntent = Intent(this, CarDetailsActivity::class.java)
            detailsIntent.putExtra("name", carName)
            detailsIntent.putExtra("price", carPrice)
            detailsIntent.putExtra("engineType", carEngineType)
            startActivity(detailsIntent)
        }

    }
}