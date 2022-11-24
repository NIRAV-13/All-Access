package com.mobile.macs_13

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.mobile.macs_13.model.StudentNotificationData


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.advisor_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdvisorHomeAdapter(dummy)
        recyclerView.adapter = adapter
    }

    private fun getLink(): String {

        var imageLinkFromFireStore = ""
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        val currentUserID = "1001"
        //mAuth.currentUser?.uid

//        db.collection("StudentProfileImages").document(currentUserID)
//            .addSnapshotListener(object : EventListener<DocumentSnapshot> {
//                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
//
//                    if (error != null) {
//                        Log.d(
//                            "Error",
//                            "Some Error in Connection to FireStore ${error.message.toString()}"
//                        )
//                        return
//                    }
//
//                    if (value != null) {
//                        if (value.id == currentUserID) {
//                            imageLinkFromFireStore = value.data?.get("link").toString()
//                            Log.d("Info", "LINK : $imageLinkFromFireStore")
//                        }
//                    }
//                }
//            })

        db.collection("StudentProfileImages").document(currentUserID)
            .get().addOnCompleteListener { task ->
                if (task.isComplete) {
                    imageLinkFromFireStore = task.result.data?.get("link").toString()
                }
            }

        Log.d("Info", "LINK $imageLinkFromFireStore")
        return imageLinkFromFireStore
    }
}