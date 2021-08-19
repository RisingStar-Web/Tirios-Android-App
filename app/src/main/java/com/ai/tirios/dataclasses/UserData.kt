package com.ai.tirios.dataclasses

data class UserData(
    val accessFailedCount: Int,
    val concurrencyStamp: String,
    val documentsUploaded: Boolean,
    val email: Any,
    val emailConfirmed: Boolean,
    val firstName: String,
    val id: String,
    val isActive: Boolean,
    val lastName: String,
    val lastPasswordChangeDate: Any,
    val lockoutEnabled: Boolean,
    val lockoutEnd: Any,
    val mobile: String,
    val normalizedEmail: Any,
    val normalizedUserName: String,
    val otp: Any,
    val passwordHash: String,
    val phoneNumber: Any,
    val phoneNumberConfirmed: Boolean,
    val securityStamp: String,
    val status: Any,
    val twoFactorEnabled: Boolean,
    val userClaims: Any,
    val userName: String,
    val userRoles: List<UserRole>
)