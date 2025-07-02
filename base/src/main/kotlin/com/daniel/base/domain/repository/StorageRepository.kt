package com.daniel.base.domain.repository

interface StorageRepository {
    suspend fun getUser(): String?
    suspend fun setUser(user: String)
    suspend fun setStartDate(startDate: String)
    suspend fun setEndDate(endDate: String)
    suspend fun getStartDate(): String?
    suspend fun getEndData(): String?
}