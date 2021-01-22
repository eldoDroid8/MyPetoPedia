package `in`.ev.pediadata.di.qualifier

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class DogNetwork(val type: QualifierType)