package com.example.patientscard.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.patientscard.fragments.ChartFragment
import com.example.patientscard.fragments.DataFragment

class DataFragmentStatePagerAdapter(
    var dataFragment: DataFragment,
    var chartFragment: ChartFragment,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            dataFragment
        } else {
            chartFragment
        }
    }

    override fun getCount(): Int {
        return 2
    }

}