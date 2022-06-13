package com.dicoding.diva.pimpledetectku.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ResultPreference private constructor(val dataStore : DataStore<Preferences>) {
    companion object {
        @Volatile
        private var INSTANCE: ResultPreference? = null

        private val ID_KEY = intPreferencesKey("id")
        private val TYPE_KEY = stringPreferencesKey("type")
        private val NAME_KEY = stringPreferencesKey("name")
        private val DESCRIPTION_KEY = stringPreferencesKey("description")
        private val CAUSE_KEY = stringPreferencesKey("cause")
        private val SOLUTION_KEY = stringPreferencesKey("solution")
        private val IMAGE_KEY = stringPreferencesKey("image")
        private val REFERENCE_KEY = stringPreferencesKey("reference")
        private val CREATED_KEY = stringPreferencesKey("created_at")
        private val UPDATED_KEY = stringPreferencesKey("updated_at")
        private val SUCCESS_KEY = booleanPreferencesKey("success")
        private val MESSAGE_KEY = stringPreferencesKey("message")


        fun getInstance(dataStore: DataStore<Preferences>): ResultPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ResultPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }


    fun getResult(): Flow<ResultModel> {
        return dataStore.data.map { preferences ->
            ResultModel(
                preferences[ID_KEY]!!,
                preferences[TYPE_KEY] ?:"",
                preferences[NAME_KEY] ?:"",
                preferences[DESCRIPTION_KEY] ?:"",
                preferences[CAUSE_KEY] ?:"",
                preferences[SOLUTION_KEY] ?:"",
                preferences[IMAGE_KEY] ?:"",
                preferences[REFERENCE_KEY] ?:"",
                preferences[CREATED_KEY] ?:"",
                preferences[UPDATED_KEY] ?:"",
                preferences[SUCCESS_KEY] ?: false,
                preferences[MESSAGE_KEY] ?:"",
            )
        }
    }

    suspend fun result(resultModel : ResultModel) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = resultModel.id
            preferences[TYPE_KEY] = resultModel.type
            preferences[NAME_KEY] = resultModel.name
            preferences[DESCRIPTION_KEY] = resultModel.description
            preferences[CAUSE_KEY] = resultModel.cause
            preferences[SOLUTION_KEY] = resultModel.solution
            preferences[IMAGE_KEY] = resultModel.image
            preferences[REFERENCE_KEY] = resultModel.reference
            preferences[CREATED_KEY] = resultModel.created_at
            preferences[UPDATED_KEY] = resultModel.updated_at
            preferences[SUCCESS_KEY] = resultModel.success
            preferences[MESSAGE_KEY] = resultModel.message

        }
    }
}