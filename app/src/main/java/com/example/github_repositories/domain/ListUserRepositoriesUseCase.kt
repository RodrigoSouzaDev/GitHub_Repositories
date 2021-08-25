package com.example.github_repositories.domain

import com.example.github_repositories.core.UseCase
import com.example.github_repositories.data.model.Repo
import com.example.github_repositories.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class ListUserRepositoriesUseCase(
    private val repository: RepoRepository): UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}
