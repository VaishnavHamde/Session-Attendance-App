package com.example.sessionattendance

import android.R.attr.country
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){

    // Creating required variables for views in the main layout.
    lateinit var name: EditText
    lateinit var number: EditText
    lateinit var course: Spinner
    lateinit var firstYear: RadioButton
    lateinit var secondYear: RadioButton
    lateinit var thirdYear: RadioButton
    lateinit var fourthYear: RadioButton
    lateinit var address: EditText
    lateinit var add: Button
    lateinit var recyclerView: RecyclerView

    // Creating object of PesronsAdapter class.
    lateinit var adapter: PersonsAdapter

    // Creating lists to store the persons' information.
    var personNames = ArrayList<String>()
    var personNumber = ArrayList<Long>()
    var personCourse = ArrayList<String>()
    var personAddress = ArrayList<String>()

    // Creating a list to store information of all persons.
    var personList = ArrayList<ArrayList<String>>()

    // Creating objects of FileHelper classes to store all the information in the system's memory.
    var fileNameHelper = FileNameHelper()
    var fileNumberHelper = FileNumberHelper()
    var fileCourseHelper = FileCourseeHelper()
    var fileAddressHelper = FileAddressHelper()

    // Initiallizing HashSet to avoid duplication of persons.
    companion object{
        var numberSet = HashSet<Long>()

        fun deleted(){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Giving personList a size of four.
        personList.add(ArrayList<String>())
        personList.add(ArrayList<String>())
        personList.add(ArrayList<String>())
        personList.add(ArrayList<String>())

        // Creating the layout.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assigning views to variables.
        assignViewsToVariables()

        // Creatubg an object of ArrayAdapter class for setting layout for spinner and
        // adding items to the spinner.
        var arrayAdapter = ArrayAdapter.createFromResource(
            this@MainActivity, R.array.course_spinner, android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        course.adapter = arrayAdapter

        // Getting stored information from the System's memory.
        personList[0] = fileNameHelper.readData(this@MainActivity)
        personList[1] = fileNumberHelper.readData(this@MainActivity)
        personList[2] = fileCourseHelper.readData(this@MainActivity)
        personList[3] = fileAddressHelper.readData(this@MainActivity)

        // Setting the recycler view as linear layout in application.
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        // Initiallizing the adapter to the custom adapter PersonsAdapter.
        adapter = PersonsAdapter(personList[0], personList[1], personList[2], personList[3]
            , this@MainActivity)

        // Giving the recyclerView the values which are going to be displayed.
        recyclerView.adapter = adapter

        // Adding item to the recycler view.
        add.setOnClickListener {
            addItem()
        }
    }

    // Function to assign views to variables.
    fun assignViewsToVariables() {

        name = findViewById(R.id.edit_name)
        number = findViewById(R.id.edit_phone_number)
        course = findViewById(R.id.spinner_course)
        firstYear = findViewById(R.id.radio_1st_year)
        secondYear = findViewById(R.id.radio_2nd_year)
        thirdYear = findViewById(R.id.radio_3rd_year)
        fourthYear = findViewById(R.id.radio_4th_year)
        address = findViewById(R.id.edit_address)
        add = findViewById(R.id.add_button)
        recyclerView = findViewById(R.id.recycler_list)

    }

    // Function to add item to the recycler view.
    fun addItem() {

        // Giving the values to varianles which are to be stored and displayed.
        var personName: String = name.text.toString()
        var personNumber: String = number.text.toString()
        var personCourse: String = course.getSelectedItem().toString()
        if(personCourse.equals("Select branch"))
                personCourse = ""
        var personAddress: String = address.text.toString()
        var first: Boolean = firstYear.isChecked
        var second: Boolean = secondYear.isChecked
        var third: Boolean = thirdYear.isChecked
        var fourth: Boolean = fourthYear.isChecked
        if (first)
            personCourse = personCourse + " 1st Year"
        else if (second)
            personCourse = personCourse + " 2nd Year"
        else if (third)
            personCourse = personCourse + " 3rd Year"
        else if (fourth)
            personCourse = personCourse + " 4th Year"

        // Avoiding duplication of persons.
        if(MainActivity.numberSet.contains(personNumber.toLong())){
            Toast.makeText(this@MainActivity, "The person already exists."
                , Toast.LENGTH_SHORT).show()
            return
        }
        MainActivity.numberSet.add(personNumber.toLong())

        // Setting the values to the views to null values.
        name.setText("")
        number.setText("")
        course.setSelection(0)
        address.setText("")

        // Adding the info to the personList ArrayList.
        personList[0].add(personName)
        personList[1].add(personNumber)
        personList[2].add(personCourse)
        personList[3].add(personAddress)

        // Storing the info in system's memory.
        fileNameHelper.writeData(personList[0], this@MainActivity)
        fileNumberHelper.writeData(personList[1], this@MainActivity)
        fileCourseHelper.writeData(personList[2], this@MainActivity)
        fileAddressHelper.writeData(personList[3], this@MainActivity)

        // Notifying the adapter that the person is added.
        adapter.notifyDataSetChanged()

    }

}