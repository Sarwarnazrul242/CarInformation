package com.bignerdranch.android.carinformation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.carinformation.R
import com.bignerdranch.android.carinformation.databinding.ItemListBinding
import com.bignerdranch.android.carinformation.model.CarData
import com.bignerdranch.android.carinformation.NewActivity
import android.widget.Filter
import android.widget.Filterable

class CarsAdapter (
    var c:Context, var carList:ArrayList<CarData>
):RecyclerView.Adapter<CarsAdapter.CarViewHolder>(), Filterable {

    private val originalCarList = ArrayList<CarData>().apply {
        addAll(carList)
    }

    inner class CarViewHolder(var v: ItemListBinding) : RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflater, R.layout.item_list,parent,
            false)
        return CarViewHolder(v)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val newList = carList[position]
        holder.v.isCars = carList[position]
        holder.v.root.setOnClickListener{
            val img = newList.img
            val name = newList.name
            val info = newList.info
            val price = newList.price
            val engineType = newList.engineType
//set data
            val mIntent = Intent(c, NewActivity::class.java)
            mIntent.putExtra("img", img)
            mIntent.putExtra("name", name)
            mIntent.putExtra("info", info)
            mIntent.putExtra("price", price)
            mIntent.putExtra("engineType", engineType)


            c.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<CarData>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(originalCarList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in originalCarList) {
                        if (item.name?.toLowerCase()?.contains(filterPattern) == true) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    carList.clear()
                    carList.addAll(results.values as ArrayList<CarData>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}