package com.nhs.online.nhsonline.proto.home.domain.datasource

import com.nhs.online.nhsonline.proto.home.domain.model.UserDetails

interface HomeDataSource {
    suspend fun fetchUserDetails(): UserDetails
}