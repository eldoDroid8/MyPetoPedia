package `in`.ev.petopedia.domain.usecase

import `in`.ev.petopedia.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface UseCase<out T> {
    suspend fun execute(): Flow<Response<T>>
}