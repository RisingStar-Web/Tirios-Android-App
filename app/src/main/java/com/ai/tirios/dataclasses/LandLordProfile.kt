package com.ai.tirios.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */
@Entity(tableName = "user_table")
data class LandLordProfile(
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "firstName") var firstName: String,
    @ColumnInfo(name = "lastName") var lastName: String,
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "token") var token: String,
    @ColumnInfo(name = "role") var role: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "emailConfirmed") var emailConfirmed: Boolean,
    var documentsUploaded: Boolean
)
