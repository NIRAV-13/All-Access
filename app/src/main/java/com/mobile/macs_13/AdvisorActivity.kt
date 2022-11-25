package com.mobile.macs_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdvisorActivity : AppCompatActivity() {

    private lateinit var adapter: AdvisorHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var accomRequestList: ArrayList<AccomRequest>

    val dummy = arrayListOf<AccomRequest>(
        AccomRequest(
            "Test",
            "Data",
            "Some details",
            "jkb"
        ),
        AccomRequest("Test 2", "Data", "kjnkfn", "jfnlkvmk")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor)

        val layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView = findViewById(R.id.advisor_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdvisorHomeAdapter(dummy)
        recyclerView.adapter = adapter
    }
}