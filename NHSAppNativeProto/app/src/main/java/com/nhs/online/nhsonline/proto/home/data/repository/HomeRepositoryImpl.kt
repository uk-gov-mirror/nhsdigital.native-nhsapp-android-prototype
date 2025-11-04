package com.nhs.online.nhsonline.proto.home.data.repository

import com.nhs.online.nhsonline.proto.home.domain.datasource.HomeDataSource
import com.nhs.online.nhsonline.proto.home.domain.model.UserDetails
import com.nhs.online.nhsonline.proto.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepositoryImpl(private val dataSource: HomeDataSource): HomeRepository {
    override fun getUserDetails(): Flow<UserDetails> {
        return flow {
            emit(dataSource.fetchUserDetails())
        }.flowOn(Dispatchers.IO)
    }
}