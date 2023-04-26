package com.bignerdranch.android.carinformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.carinformation.adapter.CarsAdapter
import com.bignerdranch.android.carinformation.model.CarData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mDataBase: DatabaseReference
    private lateinit var carList: ArrayList<CarData>
    private lateinit var filteredCarList: ArrayList<CarData>
    private lateinit var mAdapter: CarsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carList = ArrayList()
        filteredCarList = ArrayList()
        mAdapter = CarsAdapter(this, filteredCarList)
        recyclerCars.layoutManager = LinearLayoutManager(this)
        recyclerCars.setHasFixedSize(true)
        recyclerCars.adapter = mAdapter

        getCarsData()
        fetchCarData()

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText)
                return true
            }
        })
    }

    private fun performSearch(query: String?) {
        filteredCarList.clear()

        if (query.isNullOrEmpty()) {
            filteredCarList.addAll(carList)
        } else {
            carList.forEach { car ->
                if (car.name?.lowercase()?.contains(query.lowercase()) == true) {
                    filteredCarList.add(car)
                }
            }
        }

        mAdapter.notifyDataSetChanged()
    }

    private fun getCarsData() {
        mDataBase = FirebaseDatabase.getInstance().getReference("Cars")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    carList.clear()
                    for (carSnapshot in snapshot.children) {
                        val car = carSnapshot.getValue(CarData::class.java)
                        carList.add(car!!)
                    }
                    performSearch(search_bar.query.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,
                    error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun fetchCarData() {
        val database = FirebaseDatabase.getInstance()
        val carDataRef = database.getReference("cars")

        carDataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                carList.clear()
                for (carSnapshot in snapshot.children) {
                    val car = carSnapshot.getValue(CarData::class.java)
                    car?.let { carList.add(it) }
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Error fetching car data: ${error.message}")
            }
        })
    }
}