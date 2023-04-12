package com.mobile.macs_13.com.mobile.macs_13.view.student

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R
import com.mobile.macs_13.com.mobile.macs_13.model.CourseList
import com.mobile.macs_13.com.mobile.macs_13.model.ProgramList
import com.mobile.macs_13.com.mobile.macs_13.view.TermList
import com.mobile.macs_13.controller.accomodation.AccomodationList
import com.mobile.macs_13.controller.accomodation.AccommodationController
import com.mobile.macs_13.model.AccomodationListAdapter
import com.mobile.macs_13.model.AdvisorAccomRequestModel
// view class to handle the accomodation list with dropdown options for advisor
class AccomodationListActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var accomodationListAdapter: AccomodationListAdapter
    private val advisorController = AccommodationController()
    private lateinit var selectcoursedropdown: Spinner
    private lateinit var selectProgramDropdown: Spinner
    private lateinit var selectYearDropdown: Spinner
    private lateinit var accomCheckBtn : Button
    private var accommodationList = mutableListOf<AdvisorAccomRequestModel>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accomodation_list)
        accommodationList.clear()
        recyclerView = findViewById(R.id.accomodationRecyclerView)
        recyclerView.setHasFixedSize(true)
        accommodationList = AccomodationList.getAccomodations()
        accomodationListAdapter = AccomodationListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)

        recyclerView.adapter = accomodationListAdapter

        selectProgramDropdown=findViewById(R.id.spinnerSelectProgram);
        selectcoursedropdown=findViewById(R.id.spinnerSelectCourse);
        selectYearDropdown=findViewById(R.id.spinnerSelectYear);

        advisorController.fetchProgramsForDropDown{status ->
            if(status){

                val selectProgramDropdownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ProgramList.getPrograms())
                var selectProgramDropdown=findViewById<Spinner>(R.id.spinnerSelectProgram);

                selectProgramDropdown.adapter = selectProgramDropdownAdapter;

            }
            else{
                Toast.makeText(this,"Error! Please try again!", Toast.LENGTH_SHORT).show()
            }
        }
        advisorController.fetchCourseForDropDown{status ->
            if(status){

                val selectCourseDropdownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, CourseList.getCourses())
                var selectcoursedropdown=findViewById<Spinner>(R.id.spinnerSelectCourse);
                selectcoursedropdown.adapter = selectCourseDropdownAdapter;

            }
            else{
                Toast.makeText(this,"Error! Please try again!", Toast.LENGTH_SHORT).show()


            }
        }
        // to fetch data for the dropdown menus
        advisorController.fetchTermForDropDown{status ->
            if(status){
                accommodationList.clear()

                val selectYearDropdownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, TermList.getTerms())
                var selectYearDropdown=findViewById<Spinner>(R.id.spinnerSelectYear);
                selectYearDropdown.adapter = selectYearDropdownAdapter;


            }
            else{
                Toast.makeText(this,"Error! Please try again!", Toast.LENGTH_SHORT).show()

            }
        }
// onclick listener on the dropdown menus
        selectProgramDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                accommodationList.clear()

                val selectedCourse = selectcoursedropdown.selectedItem.toString() //this is your selected item
                val selectedYear = selectYearDropdown.selectedItem.toString() //this is your selected item
                val selectedProgram= parent.getItemAtPosition(position).toString() //this is your selected item

                val advisorController = AccommodationController()

                advisorController.fetchAccomodations(selectedCourse, selectedProgram, selectedYear){status->
                    accomodationListAdapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("data","nothing selected")

            }
        }

        selectcoursedropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                accommodationList.clear()

                val selectedCourse = parent.getItemAtPosition(position).toString() //this is your selected item
                val selectedYear = selectYearDropdown.selectedItem.toString() //this is your selected item
                val selectedProgram= selectProgramDropdown.selectedItem.toString() //this is your selected item

                val advisorController = AccommodationController()


                advisorController.fetchAccomodations(selectedCourse, selectedProgram, selectedYear){status->

                    accomodationListAdapter.notifyDataSetChanged()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

         selectYearDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val selectedCourse = selectcoursedropdown.selectedItem.toString() //this is your selected item
                val selectedYear = parent.getItemAtPosition(position).toString() //this is your selected item
                val selectedProgram= selectProgramDropdown.selectedItem.toString() //this is your selected item

                val advisorController = AccommodationController()



                advisorController.fetchAccomodations(selectedCourse, selectedProgram, selectedYear){status->
                    accomodationListAdapter.notifyDataSetChanged()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


    }
}

