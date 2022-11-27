package com.mobile.macs_13.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.accomodation.AccomodationList
import com.mobile.macs_13.controller.accomodation.AdvisorAccomodation
import com.mobile.macs_13.controller.accomodation.AdvisorController
import com.mobile.macs_13.model.AccomodationListAdapter
import com.mobile.macs_13.model.AdvisorAccomRequestModel

class AccomodationListActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var accomodationListAdapter: AccomodationListAdapter
    private val advisorController = AdvisorController()
    private var accomodationList = mutableListOf<AdvisorAccomRequestModel>()
//    val formatted = getString(R.string.my_xml_string, yourString)
    private lateinit var selectcoursedropdown: Spinner
    private lateinit var selectProgramDropdown: Spinner
    private lateinit var selectYearDropdown: Spinner
    private lateinit var accomCheckBtn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accomodation_list)

        recyclerView = findViewById(R.id.accomodationRecyclerView)
        recyclerView.setHasFixedSize(true)
        accomodationListAdapter = AccomodationListAdapter(AccomodationList.getAccomodations())
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)

        recyclerView.adapter = accomodationListAdapter

        selectProgramDropdown=findViewById(R.id.spinnerSelectProgram);
        var adapterProgram = ArrayAdapter.createFromResource(this, R.array.selectProgram,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adapterProgram.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        selectProgramDropdown.setAdapter(adapterProgram);

        selectcoursedropdown=findViewById(R.id.spinnerSelectCourse);
        var adaptercourse = ArrayAdapter.createFromResource(this, R.array.selectCourse,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adaptercourse.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        selectcoursedropdown.setAdapter(adaptercourse);

        selectYearDropdown=findViewById(R.id.spinnerSelectYear);
        var adapterYear = ArrayAdapter.createFromResource(this, R.array.selectYear,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adapterYear.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        selectYearDropdown.setAdapter(adapterYear);

        accomCheckBtn = findViewById(R.id.accom_req_check)
        accomCheckBtn.setOnClickListener{

        }
        advisorController.fetchAccomodations(){status ->
            accomodationListAdapter.notifyDataSetChanged()
        }

        accomodationListAdapter.onItemClick ={
            val accomIntent = Intent(this, AdvisorAccomodation::class.java)
//            accomIntent.putExtra("accomodationRequest", it)
            startActivity(accomIntent)
        }

    }
}