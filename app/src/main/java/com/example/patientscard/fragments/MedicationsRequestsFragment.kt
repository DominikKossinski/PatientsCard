package com.example.patientscard.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.patientscard.R
import com.example.patientscard.adapters.MedicationRequestRVAdapter
import com.example.patientscard.models.MedicationRequest
import kotlinx.android.synthetic.main.fragment_medications_requests.*

class MedicationsRequestsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    var medicationRequests = ArrayList<MedicationRequest>()
    var adapter : MedicationRequestRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medications_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MedicationRequestRVAdapter(medicationRequests)
        medicationRequestRecyclerView.layoutManager = LinearLayoutManager(context)
        medicationRequestRecyclerView.adapter = adapter!!
        adapter!!.notifyDataSetChanged()
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(medicationRequests: ArrayList<MedicationRequest>):MedicationsRequestsFragment {
            val fragment = MedicationsRequestsFragment()
            fragment.medicationRequests.clear()
            fragment.medicationRequests.addAll(medicationRequests)
            fragment.medicationRequests.sort()
            return fragment
        }
    }
}
