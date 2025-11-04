package com.nhs.online.nhsonline.proto.home.domain.repository

import com.nhs.online.nhsonline.proto.home.domain.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getUserDetails(): Flow<UserDetails>
}