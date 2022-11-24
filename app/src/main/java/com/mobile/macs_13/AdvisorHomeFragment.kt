package com.mobile.macs_13

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.model.StudentNotificationData


/**
 * A simple [Fragment] subclass.
 * Use the [AdvisorHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvisorHomeFragment : Fragment() {

    private lateinit var adapter: AdvisorHomeAdapter
    private lateinit var recyclerView: RecyclerView

    val dummy = arrayListOf<AppointmentRequest>(
        AppointmentRequest("Test", "Data", "Some details", "jkbjk"),
        AppointmentRequest("Test 2", "Data", "kjnkfn", "jfnlkvmk")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advisor_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.advisor_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdvisorHomeAdapter(dummy)
        recyclerView.adapter = adapter
    }

}