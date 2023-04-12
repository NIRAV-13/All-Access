package com.example.accomodationfeature

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.macs_13.R


class AdvisorAccomodationHome : AppCompatActivity() {


    private var advDB = Firebase.firestore
    private var programList : MutableList<String> = mutableListOf()
    private var courseList : MutableList<String> = mutableListOf()
    private var termList : MutableList<String> = mutableListOf()
    private lateinit var programSpinner: Spinner
    private lateinit var termSpinner: Spinner
    private lateinit var courseSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor_accomodation_home)

        //to remove the action bar
        supportActionBar?.hide()

//        val programRef = advDB.collection("Program")
//        programRef.get().addOnSuccessListener { documents ->
//
//            for (document in documents) {
//                var program = document["program"] as String
//                programList.add(program)
//                Log.d("Program value", document["program"].toString())
//
//            }
//
//        }
//        val termRef = advDB.collection("Term")
//        termRef.get().addOnSuccessListener { documents ->
//
//            for (document in documents) {
//                var term = document["term"] as String
//                termList.add(term)
//                Log.d("Term value", document["program"].toString())
//
//            }
//        }
//
//        val courseRef = advDB.collection("Course")
//        courseRef.get().addOnSuccessListener { documents ->
//
//            for (document in documents) {
//                var course = document["course"] as String
//                courseList.add(course)
//                Log.d("Course value", document["course"].toString())
//
//            }
//        }

        getDropDownData(programList, termList, courseList)

        programSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val selectedItem =
                    parent.getItemAtPosition(position).toString() //this is your selected item
                programSpinner.setSelection(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("data","nothing selected")

            }
        }

//        termSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//
//                val selectedItem =
//                    parent.getItemAtPosition(position).toString() //this is your selected item
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Log.d("data","nothing selected")
//
//            }
//        }
//
//        courseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//
//                val selectedItem =
//                    parent.getItemAtPosition(position).toString() //this is your selected item
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Log.d("data","nothing selected")
//
//            }
//        }


    }

     private fun getDropDownData(programList:MutableList<String>, termList:MutableList<String>, courseList:MutableList<String>){

        val programSpinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,programList)
        programSpinner = findViewById(R.id.spinner)
        programSpinner.adapter= programSpinnerAdapter




    }


}