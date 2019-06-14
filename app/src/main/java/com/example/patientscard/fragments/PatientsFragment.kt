package com.example.patientscard.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.patientscard.models.Patient

import com.example.patientscard.R
import com.example.patientscard.activities.MainActivity
import com.example.patientscard.adapters.PatientsRVAdapter
import com.example.patientscard.asynctasks.PatientsAsyncTask
import kotlinx.android.synthetic.main.fragment_patients.*


class PatientsFragment : Fragment() {

    var adapter :PatientsRVAdapter? = null
    private var listener: OnFragmentInteractionListener? = null


    fun setPatientsWithNotify(patients:ArrayList<Patient>) {
        adapter!!.patients = patients
        adapter!!.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patients, container, false)
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PatientsRVAdapter(ArrayList(), activity!! as MainActivity, patientsSwipeRefreshLayout)
        searchPatient("a")
        patientsRecyclerView.layoutManager = LinearLayoutManager(context)
        patientsRecyclerView.adapter = adapter

        patientsSwipeRefreshLayout.setOnRefreshListener {
            searchPatient("a")
        }

    }

    fun searchPatient(pattern: String) {
        patientsSwipeRefreshLayout.isRefreshing = true
        val patientsAsyncTask = PatientsAsyncTask(pattern, adapter!!)
        patientsAsyncTask.execute()
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

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
    companion object {
        @JvmStatic
        fun newInstance():PatientsFragment {
            return PatientsFragment()
        }
    }
}
