package `in`.ev.petopedia.domain.usecase.dog

class DogBreed : ArrayList<DogBreedItem>()

data class DogBreedItem(
    val bredFor: String,
    val breedGroup: String,
    val countryCode: String,
    val description: String,
    val height: Height,
    val history: String,
    val id: Int,
    val image: Image,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val referenceImageId: String,
    val temperament: String,
    val weight: Weight
)

data class Height(
    val imperial: String,
    val metric: String
)

data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

data class Weight(
    val imperial: String,
    val metric: String
)