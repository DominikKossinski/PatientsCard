package com.example.patientscard.models

import android.util.Log
import com.example.patientscard.BuildConfig
import com.example.patientscard.activities.MainActivity
import com.google.gson.JsonObject
import java.util.*
import kotlin.collections.ArrayList

data class Observation(
    var id: String,
    var value: Float,
    var name: String,
    var unit: String,
    var date: Date,
    var version: String
) : Comparable<Observation> {
    override fun compareTo(other: Observation): Int {
        return when {
            this.date.time > other.date.time -> 1
            this.date.time == other.date.time -> 0
            else -> -1
        }
    }

    companion object {
        fun getObservationFromJsonObject(observationObject: JsonObject): Observation? {
                val id = observationObject.get("id").asString


                val meta = observationObject.getAsJsonObject("meta")
                val version = meta.get("versionId").asString

                val code = observationObject.getAsJsonObject("code")
                val coding = code.getAsJsonArray("coding")
                val name = coding[0].asJsonObject.get("display").asString

                var value = 0.0f
                var unit = ""
                if (!name!!.contentEquals("Blood Pressure")) {
                    val valueQuantity = observationObject.getAsJsonObject("valueQuantity")
                    if(valueQuantity != null) {
                        value = valueQuantity.get("value").asFloat
                        unit = valueQuantity.get("unit").asString
                    }

                } else {
                    return null
                }


                val date = MainActivity.isoDateFormat.parse(observationObject.get("effectiveDateTime").asString)

                return Observation(id, value, name, unit, date, version)

        }

        fun getBloodObservations(observationObject: JsonObject): ArrayList<Observation> {
            val bloodObservations = ArrayList<Observation>()
            try {

                val id = observationObject.get("id").asString
                val date = MainActivity.isoDateFormat.parse(observationObject.get("effectiveDateTime").asString)

                val meta = observationObject.getAsJsonObject("meta")
                val version = meta.get("versionId").asString


                val component = observationObject.getAsJsonArray("component")
                for (item in component) {
                    val code = item.asJsonObject.getAsJsonObject("code")
                    val coding = code.getAsJsonArray("coding")
                    val name = coding[0].asJsonObject.get("display").asString
                    val valueQuantity = item.asJsonObject.getAsJsonObject("valueQuantity")
                    val value = valueQuantity.get("value").asFloat
                    val unit = valueQuantity.get("unit").asString
                    val observation = Observation(id, value, name, unit, date, version)
                    bloodObservations.add(observation)
                }
            } catch (e: NullPointerException) {
                Log.d("MyLog:getBloodObs", "$observationObject")
            }
            return bloodObservations
        }

        fun filter(
            observations: ArrayList<Observation>,
            name: String,
            startDate: Date,
            endDate: Date
        ): ArrayList<Observation> {
            val ob = ArrayList<Observation>()
            for (observation in observations) {
                if (name.contentEquals(observation.name) && startDate.time < observation.date.time && observation.date.time < endDate.time) {
                    ob.add(observation)
                }
            }
            return ob
        }

    }

}