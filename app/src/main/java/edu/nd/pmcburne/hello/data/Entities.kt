package edu.nd.pmcburne.hello.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)

@Entity(tableName = "location_tags", primaryKeys = ["locationId", "tag"])
data class LocationTagEntity(
    val locationId: Int,
    val tag: String
)


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTag(tag: LocationTagEntity)

    @Query("""
        SELECT l.* FROM locations l
        INNER JOIN location_tags t ON l.id = t.locationId
        WHERE t.tag = :tag
    """)
    fun getLocationsByTag(tag: String): Flow<List<LocationEntity>>

    @Query("SELECT DISTINCT tag FROM location_tags ORDER BY tag ASC")
    fun getAllTags(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM locations")
    suspend fun count(): Int
}
