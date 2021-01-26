package `in`.ev.petopedia.domain.usecase.cat

import CatBreed
import `in`.ev.petopedia.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCatBreedUseCaseImpl: GetCatBreedUseCase {
    override suspend fun execute(): Flow<Response<CatBreed>> {
        return flow {

        }
    }
}