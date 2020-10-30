package com.ipifanisoft.geoblocksexample.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ipifanisoft.geoblocksexample.GeoBlockListItem
import com.ipifanisoft.geoblocksexample.R
import kotlinx.android.synthetic.main.item_geo_point.view.*

class GeoBlocksAdapter (private var items : ArrayList<GeoBlockListItem>, private val context: Context, private val listener: GeoBlockActionListener) : RecyclerView.Adapter<GeoBlocksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_geo_point, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : GeoBlockListItem = items[position]
        holder.blockTitle.text = "Block: ${item.key}"
        holder.blockCount.text = "${item.count} Records"
        holder.itemView.btn_delete_block.setOnClickListener {
            listener.removeGeoBlock(item.key)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val blockTitle: TextView = view.tv_block_title
        val blockCount: TextView = view.tv_block_count
    }
}

public interface GeoBlockActionListener {
    fun removeGeoBlock(id: String)
}