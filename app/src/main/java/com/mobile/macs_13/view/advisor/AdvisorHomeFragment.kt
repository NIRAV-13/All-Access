package com.mobile.macs_13.com.mobile.macs_13.view.advisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.mobile.macs_13.com.mobile.macs_13.model.AccomRequest
import com.mobile.macs_13.R


/**
 * A simple [Fragment] subclass.
 * Use the [AdvisorHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvisorHomeFragment : Fragment() {

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advisor_home, container, false)
    }
/*

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.advisor_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdvisorHomeAdapter(dummy)
        recyclerView.adapter = adapter
    }
*/

    
}