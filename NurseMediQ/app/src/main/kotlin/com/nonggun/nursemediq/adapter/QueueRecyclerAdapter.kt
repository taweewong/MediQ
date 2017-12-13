package com.nonggun.nursemediq.adapter

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nonggun.nursemediq.R
import com.nonggun.nursemediq.dialogs.ConfirmDeleteDialog
import com.nonggun.nursemediq.model.Queue
import com.nonggun.nursemediq.service.WalkInQueueService
import com.nonggun.nursemediq.viewholder.QueueViewHolder

class QueueRecyclerAdapter(private val activity: Activity, private val queues: List<Queue>): RecyclerView.Adapter<QueueViewHolder>() {

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

            createDeleteDialog(queues[position])
        }
    }

    override fun getItemCount(): Int {
        return queues.size
    }

    private fun createDeleteDialog(queue: Queue) {
        val dialog = ConfirmDeleteDialog(activity, object : ConfirmDeleteDialog.OnClickDialogButton {
            override fun onClickConfirmDialog(builder: AlertDialog) {
                WalkInQueueService.deleteQueue(queue)
                builder.dismiss()
            }
        })

        dialog.show()
    }

}
