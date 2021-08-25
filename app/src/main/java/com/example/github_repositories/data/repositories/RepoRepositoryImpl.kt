package com.example.github_repositories.data.repositories

import com.example.github_repositories.core.RemoteException
import com.example.github_repositories.data.services.GitHubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl (private val service: GitHubService) :RepoRepository {

    override suspend fun listRepositories(user: String)= flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        } catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }
}


