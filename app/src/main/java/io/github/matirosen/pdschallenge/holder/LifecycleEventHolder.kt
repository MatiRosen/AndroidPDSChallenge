package io.github.matirosen.pdschallenge.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity

class LifecycleEventHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var view: View

    init {
        this.view = view
    }

    fun bind(lifecycleEventEntity: LifecycleEventEntity) {
        val txtEventName = this.view.findViewById<TextView>(R.id.txtCardItemEventName)
        val txtEventTimestamp = this.view.findViewById<TextView>(R.id.txtCardItemEventTimestamp)
        val imgEvent = this.view.findViewById<ImageView>(R.id.imgCardItemEvent)

        txtEventName.text = view.context.getString(R.string.event_title, lifecycleEventEntity.eventName)
        txtEventTimestamp.text = view.context.getString(R.string.event_timestamp, lifecycleEventEntity.eventTimestamp.toString())

        val eventImageMap = mapOf(
            "onCreate" to R.drawable.img_lifecycle_create,
            "onStart" to R.drawable.img_lifecycle_start,
            "onResume" to R.drawable.img_lifecycle_resume,
            "onPause" to R.drawable.img_lifecycle_pause,
            "onStop" to R.drawable.img_lifecycle_stop,
            "onDestroy" to R.drawable.img_lifecycle_destroy
        )

        imgEvent.setImageResource(eventImageMap[lifecycleEventEntity.eventName] ?: R.drawable.img_lifecycle_default)
    }

}