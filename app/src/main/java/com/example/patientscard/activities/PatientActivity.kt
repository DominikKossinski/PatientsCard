package com.example.patientscard.activities

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.patientscard.DatePickerDialogFragment
import com.example.patientscard.R
import com.example.patientscard.asynctasks.MedicationRequestAsyncTask
import com.example.patientscard.asynctasks.ObservationsAsyncTask
import com.example.patientscard.asynctasks.PatientAsyncTask
import com.example.patientscard.enums.Gender
import com.example.patientscard.fragments.*
import com.example.patientscard.models.MedicationRequest
import com.example.patientscard.models.Observation
import com.example.patientscard.models.Patient
import kotlinx.android.synthetic.main.activity_patient.*
import kotlinx.android.synthetic.main.fragment_select.*
import java.util.*
import kotlin.collections.ArrayList

class PatientActivity : AppCompatActivity(),
 SelectFragment.OnFragmentInteractionListener,
    ObservationsWithChartFragment.OnFragmentInteractionListener,
DataFragment.OnFragmentInteractionListener,
ChartFragment.OnFragmentInteractionListener,
MedicationsRequestsFragment.OnFragmentInteractionListener{

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var patient: Patient? = null
    var observations = ArrayList<Observation>()
    var medicationRequests = ArrayList<MedicationRequest>()
    var lastFragment : Fragment = SelectFragment.newInstance(patient)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            val id = bundle.getString("id")!!
            PatientAsyncTask(id, this).execute()
            val firstName = bundle.getString("firstName")
            val lastName = bundle.getString("lastName")
            val newTitle = "$firstName $lastName"
            title = newTitle
        }


        setUpFirstFragment()
    }

    private fun setUpFirstFragment() {
        supportFragmentManager.beginTransaction().add(R.id.patientFragmentContainer, lastFragment).commit()
    }

    fun setUpPatient(patient: Patient) {
        this.patient = patient
        setUpPatientData()
    }

    private fun setUpPatientData() {
        if(lastFragment is SelectFragment) {
            (lastFragment as SelectFragment).updatePatient(patient)
        }
        val birth = "Birth date: ${MainActivity.simpleDateFormat.format(patient!!.birthDate)}"
        birthDateToolbarTextView.text = birth
        if(patient!!.gender.contentEquals(Gender.FEMALE.gender)) {
            patientToolbarImageView.setImageDrawable(getDrawable(R.drawable.ic_female))
        } else {
            patientToolbarImageView.setImageDrawable(getDrawable(R.drawable.ic_male))
        }
        if(lastFragment is SelectFragment) {
            patientSwipeRefreshLayout.isRefreshing = true
            val observationsAsyncTask = ObservationsAsyncTask(patient!!.id, observations, patientSwipeRefreshLayout)
            observationsAsyncTask.execute()
            val medicationRequestAsyncTask = MedicationRequestAsyncTask(patient!!.id, medicationRequests, patientSwipeRefreshLayout)
            medicationRequestAsyncTask.execute()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                if(lastFragment is SelectFragment) {
                finish()
                } else {
                    openSelectFragment()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSelectFragment() {
        lastFragment = SelectFragment.newInstance(patient)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    private fun setBodyMassIndexFragment(startDate:Date, endDate:Date) {
        val bodyWeights = Observation.filter(observations, "Body Mass Index", startDate, endDate)
        lastFragment = ObservationsWithChartFragment.newInstance(bodyWeights, 0f, 50f)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()

    }

    private fun setBodyWeightFragment(startDate:Date, endDate:Date) {
        val bodyWeights = Observation.filter(observations, "Body Weight", startDate, endDate)
        lastFragment = ObservationsWithChartFragment.newInstance(bodyWeights,0f, 100f)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    private fun setBodyHeightFragment(startDate:Date, endDate:Date) {
        val bodyHeights = Observation.filter(observations, "Body Height", startDate, endDate)
        lastFragment = ObservationsWithChartFragment.newInstance(bodyHeights,0f, 200f)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    private fun setSystolicBloodPressureFragment(startDate:Date, endDate:Date) {
        val systolicBloodPressure = Observation.filter(observations, "Systolic Blood Pressure", startDate, endDate)
        lastFragment = ObservationsWithChartFragment.newInstance(systolicBloodPressure,0f, 200f)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    private fun setDiastolicBloodPressureFragment(startDate:Date, endDate:Date) {
        val diastolicBloodPressure = Observation.filter(observations, "Diastolic Blood Pressure", startDate, endDate)
        lastFragment = ObservationsWithChartFragment.newInstance(diastolicBloodPressure,0f, 200f)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    fun setAllObservationsFragment() {
        lastFragment = DataFragment.newInstance(observations)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    fun setMedicationsRequestFragment() {
        lastFragment = MedicationsRequestsFragment.newInstance(medicationRequests)
        supportFragmentManager.beginTransaction().replace(R.id.patientFragmentContainer, lastFragment).commit()
    }

    fun openFragment(fragmentName: String, startDateString: String, endDateString: String) {

        when {
            fragmentName.contentEquals("Body Weight") -> setBodyWeightFragment(MainActivity.simpleDateFormat.parse(startDateString), MainActivity.simpleDateFormat.parse(endDateString))
            fragmentName.contentEquals("Body Height") -> setBodyHeightFragment(MainActivity.simpleDateFormat.parse(startDateString), MainActivity.simpleDateFormat.parse(endDateString))
            fragmentName.contentEquals("Body Mass Index") -> setBodyMassIndexFragment(MainActivity.simpleDateFormat.parse(startDateString), MainActivity.simpleDateFormat.parse(endDateString))
            fragmentName.contentEquals("Systolic Blood") -> setSystolicBloodPressureFragment(MainActivity.simpleDateFormat.parse(startDateString), MainActivity.simpleDateFormat.parse(endDateString))
            fragmentName.contentEquals("Diastolic Blood") -> setDiastolicBloodPressureFragment(MainActivity.simpleDateFormat.parse(startDateString), MainActivity.simpleDateFormat.parse(endDateString))
        }
    }

    fun startDateDialogFragment(fragmentName: String) {
        val dialog = DatePickerDialogFragment.newInstance(fragmentName)
        dialog.show(supportFragmentManager, "DateFragment")
    }
}
