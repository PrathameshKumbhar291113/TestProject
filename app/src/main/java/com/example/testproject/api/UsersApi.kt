package com.example.testproject.api

import com.example.testproject.data.UserResult
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET(USERS)
    suspend fun getUsers(
        @Query("users") users: String = Constants.BASE_URL
    ): UserResult

    @GET(NEXTPAGE)
    suspend fun getNextPage(
        @Query("nextPage") nextPage: String = Constants.BASE_URL
    ): UserResult

    @GET(PREVIOUSPAGE)
    suspend fun getPrevPage(
        @Query("prevPage") prevPage: String = Constants.BASE_URL
    ): UserResult


    companion object{
        const val USERS = "users/"
        const val NEXTPAGE = "users/?page=2"
        const val PREVIOUSPAGE = "users/?page=1"
    }
}