package com.nonggun.nursemediq.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nonggun.nursemediq.R

class QueueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var queueText: TextView = itemView.findViewById(R.id.queueText)
    var clientNameText: TextView = itemView.findViewById(R.id.clientNameText)
    var clientPhoneNumberText: TextView = itemView.findViewById(R.id.clientPhoneNumberText)
    var deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
}

