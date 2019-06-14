package com.example.patientscard.asynctasks

import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.example.patientscard.BuildConfig
import com.example.patientscard.models.Observation
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.net.URL

class ObservationsAsyncTask(
    private val userId: String, private val observations: ArrayList<Observation>, val swipeRefreshLayout: SwipeRefreshLayout
) : AsyncTask<String, String, JsonObject>() {
    override fun doInBackground(vararg params: String?): JsonObject {
        val url = URL("http://10.0.2.2:8080/baseDstu3/Observation?patient=$userId&_format=json&_pretty=true")
        val gson = GsonBuilder().create()
        return gson.fromJson(url.readText(), JsonObject::class.java)
    }

    override fun onPostExecute(result: JsonObject?) {
        super.onPostExecute(result)
        if (result != null) {
            observations.clear()
            val entry = result.getAsJsonArray("entry")
            if (entry != null) {
                for (observationObject in entry) {
                    val observation =
                        Observation.getObservationFromJsonObject(observationObject.asJsonObject.getAsJsonObject("resource"))
                    if (observation == null) {
                        val obs =
                            Observation.getBloodObservations(observationObject.asJsonObject.getAsJsonObject("resource"))
                        for (observation1 in obs) {
                            if (BuildConfig.DEBUG) {
                                Log.d("MyLog:ObservationsAT", "$observation1")
                            }
                            observations.add(observation1)
                        }
                    } else {
                        observations.add(observation)
                        if (BuildConfig.DEBUG) {
                            Log.d("MyLog:ObservationsAT", observation.name)
                        }
                    }
                }
                observations.sort()
            }
        }
        swipeRefreshLayout.isRefreshing = false
    }

}