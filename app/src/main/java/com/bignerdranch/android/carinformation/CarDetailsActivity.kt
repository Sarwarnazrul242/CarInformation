package com.bignerdranch.android.carinformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CarDetailsActivity : AppCompatActivity() {
    private lateinit var carNameTextView: TextView
    private lateinit var carPriceTextView: TextView
    private lateinit var carEngineTypeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        carNameTextView = findViewById(R.id.car_name)
        carPriceTextView = findViewById(R.id.car_price)
        carEngineTypeTextView = findViewById(R.id.car_engine_type)

        val carName = intent.getStringExtra("name") ?: ""
        val carPrice = intent.getStringExtra("price") ?: ""
        val carEngineType = intent.getStringExtra("engineType") ?: ""

        carNameTextView.text = carName
        carPriceTextView.text = getString(R.string.market_price, carPrice)
        carEngineTypeTextView.text = getString(R.string.engine_type, carEngineType)
    }
}