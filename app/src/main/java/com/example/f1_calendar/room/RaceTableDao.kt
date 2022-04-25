package com.example.f1_calendar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.f1_calendar.model.domain.RaceTable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface RaceTableDao {

    @Query("SELECT * FROM RaceTable WHERE season = :season")
    fun getRacesBySeason(season: String): Single<RaceTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRaceTable(raceTable: RaceTable): Completable
}