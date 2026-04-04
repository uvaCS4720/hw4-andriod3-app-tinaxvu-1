package edu.nd.pmcburne.hello.data

import kotlinx.coroutines.flow.Flow

class PlacemarkRepository(
    private val dao: LocationDao,
    private val api: PlacemarkApiService
) {

    suspend fun syncIfNeeded() {
        if (dao.count() > 0) return

        val placemarks = api.getPlacemarks()
        for (p in placemarks) {
            dao.insertLocation(
                LocationEntity(
                    id = p.id,
                    name = p.name,
                    description = p.description ?: "",
                    latitude = p.visualCenter.latitude,
                    longitude = p.visualCenter.longitude
                )
            )
            for (tag in p.tagList) {
                dao.insertTag(LocationTagEntity(locationId = p.id, tag = tag))
            }
        }
    }

    fun locationsByTag(tag: String): Flow<List<LocationEntity>> =
        dao.getLocationsByTag(tag)

    fun allTags(): Flow<List<String>> =
        dao.getAllTags()
}
