package com.example.patientscard.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.patientscard.R
import com.example.patientscard.activities.PatientActivity
import com.example.patientscard.asynctasks.ObservationsAsyncTask
import com.example.patientscard.models.Observation
import com.example.patientscard.models.Patient
import kotlinx.android.synthetic.main.fragment_select.*

class SelectFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    var patient: Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientSwipeRefreshLayout.setOnRefreshListener {
            patientSwipeRefreshLayout.isRefreshing = false
        }
        bodyWeightButton.setOnClickListener {
            if(!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).startDateDialogFragment("Body Weight")
            }
        }

        bodyMassIndexButton.setOnClickListener {
            if (!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).startDateDialogFragment("Body Mass Index")
            }
        }

        bodyHeightButton.setOnClickListener {
            if(!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).startDateDialogFragment("Body Height")
            }
        }

        systolicBloodPressureButton.setOnClickListener {
            if(!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).startDateDialogFragment("Systolic Blood")
            }
        }

        diastolicBloodPressureButton.setOnClickListener {
            if(!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).startDateDialogFragment("Diastolic Blood")
            }
        }

        allObservationsButton.setOnClickListener {
            if(!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).setAllObservationsFragment()
            }
        }

        medicationRequestsButton.setOnClickListener {
            if(!patientSwipeRefreshLayout.isRefreshing) {
                (activity as PatientActivity).setMedicationsRequestFragment()
            }
        }

        if(patient != null) {
            updatePatient(patient)
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun updatePatient(patient: Patient?) {
        this.patient = patient

        val address = "Address: ${patient!!.address.address}"
        addressTextView.text = address

        val city = "City: ${patient.address.city}"
        cityTextView.text = city

        val postalCode = "Postal Code: ${patient.address.postalCode}"
        postalCodeTextView.text = postalCode

        val state = "State: ${patient.address.state}"
        stateTextView.text = state

        val country = "Country: ${patient.address.country}"
        countryTextView.text = country

        val phone = "Tel: ${patient.telephone}"
        phoneTextView.text = phone
        //TODO update data
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(patient: Patient?): SelectFragment {
            val fragment = SelectFragment()
            fragment.patient = patient
            return fragment
        }

    }
}
