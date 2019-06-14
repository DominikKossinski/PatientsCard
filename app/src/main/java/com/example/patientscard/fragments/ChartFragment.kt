package com.example.patientscard.fragments

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.patientscard.BuildConfig
import com.example.patientscard.R
import com.example.patientscard.activities.MainActivity
import com.example.patientscard.models.Observation
import kotlinx.android.synthetic.main.fragment_chart.*
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.model.Viewport
import kotlin.math.max
import kotlin.math.min


class ChartFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    val observations = ArrayList<Observation>()
    var maxChartY = 100f
    var minChartY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val axisValues = ArrayList<AxisValue>()
        val yAxisValues = ArrayList<PointValue>()
        val line = Line(yAxisValues)

        for (i in 0 until observations.size) {
            val axisVal = AxisValue(i.toFloat())
            axisVal.setLabel(MainActivity.simpleDateFormat.format(observations[i].date))
            axisValues.add(i, axisVal)
            val point = PointValue(i.toFloat(), observations[i].value)
            yAxisValues.add(point)
            if(BuildConfig.DEBUG) {
              Log.d("MyLog:ChartFragment", "${observations[i]}")
            }
        }


        line.color = Color.parseColor("#9C27B0");
        val lines = ArrayList<Line>()
        lines.add(line)

        val chartData = LineChartData()
        chartData.lines = lines

        val xAxis = Axis()
        xAxis.values = axisValues
        xAxis.textSize = 16
        xAxis.textColor = Color.parseColor("#03A9F4")
        chartData.axisXBottom = xAxis

        val yAxis = Axis()
        if(observations.size > 0) {
            yAxis.name = observations[0].name
        }
        yAxis.textColor = Color.parseColor("#03A9F4")
        yAxis.textSize = 16
        chartData.axisYLeft = yAxis


        chart.lineChartData = chartData

        val viewport = Viewport(chart.maximumViewport)
        viewport.top = maxChartY
        viewport.bottom = minChartY
        chart.maximumViewport = viewport
        chart.currentViewport = viewport
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance(observations: ArrayList<Observation>, minChartY:Float, maxChartY:Float): ChartFragment {
            val fragment = ChartFragment()
            fragment.observations.clear()
            fragment.observations.addAll(observations)
            fragment.minChartY = minChartY
            fragment.maxChartY = maxChartY
            return fragment
        }
    }
}
