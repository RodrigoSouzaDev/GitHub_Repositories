package com.example.github_repositories.presentation.di

import com.example.github_repositories.presentation.RepoListFragViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load(){ loadKoinModules(viewModelModule())}

    private fun viewModelModule(): Module {
        return module {
            viewModel { RepoListFragViewModel(get())}
        }
    }
}

//private fun viewModelModule(): Module {