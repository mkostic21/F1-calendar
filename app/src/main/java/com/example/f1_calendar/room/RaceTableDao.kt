package com.example.f1_calendar.room

import androidx.room.*
import com.example.f1_calendar.model.domain.RaceTable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RaceTableDao {

    @Query("SELECT * FROM RaceTable WHERE season = :season")
    fun getRacesBySeason(season: String): Single<RaceTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRaceTable(raceTable: RaceTable): Completable

    // todo: remove this if unused
    @Delete
    fun delete(raceTable: RaceTable)
}