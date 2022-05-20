package com.example.myapplication.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Repository")
data class Repos(
    @PrimaryKey()
    @ColumnInfo(name = "reposId")
    var reposId: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "language")
    var language: String = "",

    @ColumnInfo(name = "imageIcon")
    var image: String = "",

    ) : Parcelable