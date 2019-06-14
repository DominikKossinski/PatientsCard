package com.example.patientscard.models

import com.google.gson.JsonObject

data class Medication(var id: String?, var code: String, var text: String, var version: String?) {

    companion object {
        fun getMedicationFromJsonObject(medicationObject: JsonObject): Medication {
            val id = medicationObject.get("id").asString

            val meta = medicationObject.getAsJsonObject("meta")
            val version = meta.get("versionId").asString

            val code = medicationObject.getAsJsonObject("code")
            val coding = code.getAsJsonArray("coding")
            val mCode = coding[0].asJsonObject.get("code").asString

            val text = code.get("text").asString

            return Medication(id, mCode, text, version)
        }
    }

}
