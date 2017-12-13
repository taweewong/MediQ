package com.nonggun.nursemediq.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nonggun.nursemediq.R
import com.nonggun.nursemediq.adapter.QueueRecyclerAdapter
import com.nonggun.nursemediq.model.Queue
import com.nonggun.nursemediq.service.QueryQueueService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), QueryQueueService.OnGetAllQueueComplete {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queueRecyclerView.layoutManager = LinearLayoutManager(this)
        QueryQueueService.getAllQueue(this)
    }

    override fun onGetAllQueueSuccess(queues: ArrayList<Queue>) {
        val adapter = QueueRecyclerAdapter(queues)
        queueRecyclerView.adapter = adapter
    }

    override fun onGetAllQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
