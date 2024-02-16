package io.github.matirosen.pdschallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity
import io.github.matirosen.pdschallenge.holder.LifecycleEventHolder

class LifecycleEventAdapter : RecyclerView.Adapter<LifecycleEventHolder>() {

    private var data: List<LifecycleEventEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifecycleEventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_event, parent, false)
        return LifecycleEventHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LifecycleEventHolder, position: Int) {
        holder.bind(data[position])
    }

    fun submitList(data: List<LifecycleEventEntity>?) {
        this.data = data ?: emptyList()
        notifyDataSetChanged()
    }
}