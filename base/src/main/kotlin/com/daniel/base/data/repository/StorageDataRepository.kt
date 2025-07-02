package com.daniel.base.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.daniel.base.domain.repository.StorageRepository
import androidx.core.content.edit

private const val USER_NAME = "user_name"
private const val EMPTY_STRING = ""
private const val START_DATE = "start_date"
private const val END_DATE = "end_date"

class StorageDataRepository(
    private val sharedPreferences: SharedPreferences
) : StorageRepository  {
    override suspend fun getUser(): String? {
        return sharedPreferences.getString(USER_NAME, EMPTY_STRING)
    }

    override suspend fun setUser(user: String) {
        sharedPreferences.edit {
            putString(USER_NAME, user)
        }
        Log.d("BUDGET", "user $user saved successfully")
    }

    override suspend fun setStartDate(startDate: String) {
        sharedPreferences.edit {
            putString(START_DATE, startDate)
        }
        Log.d("BUDGET", "start date $startDate saved successfully")
    }

    override suspend fun setEndDate(endDate: String) {
        sharedPreferences.edit {
            putString(END_DATE, endDate)
        }
        Log.d("BUDGET", "end date $endDate saved successfully")
    }

    override suspend fun getStartDate(): String? {
        return sharedPreferences.getString(START_DATE, EMPTY_STRING)
    }

    override suspend fun getEndData(): String? {
        return sharedPreferences.getString(END_DATE, EMPTY_STRING)
    }
}