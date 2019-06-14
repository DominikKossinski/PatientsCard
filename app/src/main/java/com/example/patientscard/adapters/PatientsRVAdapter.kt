package com.example.patientscard.adapters

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.content.res.AppCompatResources.getDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.patientscard.activities.MainActivity
import com.example.patientscard.models.Patient
import com.example.patientscard.R
import com.example.patientscard.enums.Gender

class PatientsRVAdapter(var patients: ArrayList<Patient>, val activity: MainActivity, val swipeRefreshLayout: SwipeRefreshLayout) :
    RecyclerView.Adapter<PatientsRVAdapter.PatientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.patient_row, parent, false)
        return PatientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    override fun onBindViewHolder(viewHolder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        if(patient.gender.contentEquals(Gender.FEMALE.gender)) {
            viewHolder.rowImageView.setImageDrawable(getDrawable(activity, R.drawable.ic_female))
        } else {
            viewHolder.rowImageView.setImageDrawable(getDrawable(activity, R.drawable.ic_male))
        }
        viewHolder.lastNameTextView.text = patient.lastName
        viewHolder.firstNameTextView.text = patient.firstName
        val text = "Birth date: ${MainActivity.simpleDateFormat.format(patient.birthDate)}"
        viewHolder.birthDateTextView.text = text
        viewHolder.itemView.setOnClickListener {
            if(!swipeRefreshLayout.isRefreshing) {
                activity.openPatientActivity(patient)
            } else {
                Toast.makeText(activity.applicationContext, activity.getString(R.string.wait_a_moment), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setNewPatients(patients: ArrayList<Patient>) {
        this.patients = patients
        swipeRefreshLayout.isRefreshing = false
        this.notifyDataSetChanged()
    }


    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val firstNameTextView =  itemView.findViewById(R.id.firstNameTextView) as TextView
        val lastNameTextView = itemView.findViewById(R.id.lastNameTextView) as TextView
        val rowImageView = itemView.findViewById(R.id.patientRowImageView) as ImageView
        val birthDateTextView = itemView.findViewById(R.id.birthDateTextView) as TextView
    }
}