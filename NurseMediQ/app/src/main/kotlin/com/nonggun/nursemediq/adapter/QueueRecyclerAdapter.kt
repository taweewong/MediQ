package com.nonggun.nursemediq.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.nonggun.nursemediq.model.Queue
import com.nonggun.nursemediq.viewholder.QueueViewHolder
import android.view.LayoutInflater
import com.nonggun.nursemediq.R
import com.nonggun.nursemediq.service.QueryQueueService

class QueueRecyclerAdapter(private val queues: List<Queue>): RecyclerView.Adapter<QueueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.queue_view_holder, parent, false)

        return QueueViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
        holder.queueText.text = queues[position].queueNumber.toString()
        holder.clientNameText.text = queues[position].name
        holder.clientPhoneNumberText.text = queues[position].phoneNumber
        holder.deleteButton.setOnClickListener {
            QueryQueueService.deleteQueue(queues[position])
        }
    }

    override fun getItemCount(): Int {
        return queues.size
    }

}
