package com.nhs.online.nhsonline.proto.home.data.datasource

import com.nhs.online.nhsonline.proto.home.domain.datasource.HomeDataSource
import com.nhs.online.nhsonline.proto.home.domain.model.UserDetails

class HomeDataSourceImpl: HomeDataSource {
    override suspend fun fetchUserDetails(): UserDetails {
        return UserDetails("Mary", "Swanson", "123 456 7890")
    }
}