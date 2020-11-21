package com.mindorks.example.coroutines.data

import com.mindorks.example.coroutines.data.api.ApiHelper
import com.mindorks.example.coroutines.data.local.DatabaseHelperImpl
import com.mindorks.example.coroutines.data.local.entity.User

class UserRepository(private val apiHelper: ApiHelper, private val dao: DatabaseHelperImpl) {

    suspend fun getUsers(): List<User> {
        val users = dao.getUsers()
        return if (users.isEmpty()) {
            val usersToInsert = apiHelper.getUsers().map { apiUser ->
                User(
                    apiUser.id,
                    apiUser.name,
                    apiUser.email,
                    apiUser.avatar
                )
            }
            dao.insertAll(usersToInsert)
            usersToInsert
        } else {
            users
        }
    }
}