package com.example.patientscard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import com.example.patientscard.activities.PatientActivity

class DatePickerDialogFragment : DialogFragment() {

    var fragmentName : String = ""

    companion object {
        fun newInstance(fragmentName: String):DatePickerDialogFragment {
            val fragment = DatePickerDialogFragment()
            fragment.fragmentName = fragmentName
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity!!.layoutInflater
        val dialogView = layoutInflater.inflate(R.layout.date_layout, null)

        val startDatePicker = dialogView.findViewById(R.id.startDatePicker) as DatePicker
        val endDatePicker = dialogView.findViewById(R.id.endDatePicker) as DatePicker

        builder.setView(dialogView)
        builder.setPositiveButton(
            getString(android.R.string.ok)
        ) { _, _ ->
            run {
                val year = startDatePicker.year
                val month = startDatePicker.month + 1
                val day = startDatePicker.dayOfMonth

                val endYear = endDatePicker.year
                val endMonth = endDatePicker.month + 1
                val endDay = endDatePicker.dayOfMonth

                (activity as PatientActivity).openFragment(
                    fragmentName,
                    "$year-$month-$day",
                    "$endYear-$endMonth-$endDay"
                )
            }
        }

        builder.setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
            run {
                dialog.cancel()
            }
        }

        return builder.create()
    }
}