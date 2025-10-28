package com.prototype.demonhsapp.di

import com.prototype.demonhsapp.viewmodels.MessagesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MessagesViewModel)
}