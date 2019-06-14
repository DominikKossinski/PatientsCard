package com.example.patientscard.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.patientscard.R
import com.example.patientscard.activities.MainActivity
import com.example.patientscard.models.MedicationRequest

class MedicationRequestRVAdapter(var medicationRequests: ArrayList<MedicationRequest>): RecyclerView.Adapter<MedicationRequestRVAdapter.MedicationRequestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MedicationRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_request_row, parent, false)
        return MedicationRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicationRequests.size
    }

    override fun onBindViewHolder(viewHolder: MedicationRequestViewHolder, position: Int) {
        val medicationRequest = medicationRequests[position]
        viewHolder.dateTextView.text = MainActivity.dateTimeFormat.format(medicationRequest.date)
        viewHolder.textTextView.text = medicationRequest.text
        val medication = "Medication: ${medicationRequest.mediication.text}"
        viewHolder.medicationTextView.text = medication
    }


    class MedicationRequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dateTextView = itemView.findViewById(R.id.medicationRequestDateTextView) as TextView
        val textTextView = itemView.findViewById(R.id.medicationRequestTextTextView) as TextView
        val medicationTextView = itemView.findViewById(R.id.medicationTextView) as TextView
    }
}