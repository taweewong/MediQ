package com.nonggun.nursemediq.controller

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nonggun.nursemediq.R
import com.nonggun.nursemediq.adapter.QueueRecyclerAdapter
import com.nonggun.nursemediq.dialogs.AddQueueDialog
import com.nonggun.nursemediq.model.Queue
import com.nonggun.nursemediq.service.WalkInQueueService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WalkInQueueService.OnGetAllQueueComplete {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queueRecyclerView.layoutManager = LinearLayoutManager(this)
        WalkInQueueService.getAllQueue(this)

        floatingActionButton.setOnClickListener({
            createAddQueueDialog()
        })
    }

    override fun onGetAllQueueSuccess(queues: ArrayList<Queue>) {
        val adapter = QueueRecyclerAdapter(this, queues)
        queueRecyclerView.adapter = adapter
    }

    override fun onGetAllQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createAddQueueDialog() {
        val dialog = AddQueueDialog(this, object : AddQueueDialog.OnClickDialogButton{
            override fun onClickConfirmDialog(builder: AlertDialog, name: String, phoneNumber: String) {
                WalkInQueueService.addWalkInQueue(name, phoneNumber)
                builder.dismiss()
            }
        })

        dialog.show()
    }
}
