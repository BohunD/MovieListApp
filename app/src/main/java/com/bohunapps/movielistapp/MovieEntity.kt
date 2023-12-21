package com.bohunapps.movielistapp

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Parcelize
data class MovieEntity(
    val photo: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val duration: String,
    val genre: List<String>,
    val releasedDate: String,
    val trailerLink: String,
    var isInWatchList: Boolean = false
) : Parcelable {
    fun showGenre(): String{
        var genreString = ""
        for(i in genre.indices){
            genreString += if(i<genre.size-1){
                ("${genre[i]}, ")
            }else{
                genre[i]
            }
        }
        return genreString
    }
    fun convertDateFormat(): String {
        val inputFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy, d MMMM", Locale.ENGLISH)
        return try {
            val date = inputFormat.parse(releasedDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            releasedDate
        }
    }

    fun parseDateString(): Date? {
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
        return try {
            dateFormat.parse(releasedDate)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun extractYear(): Int? {
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
        return try {
            val date = dateFormat.parse(releasedDate)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            year
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
