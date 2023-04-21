package com.privin.movies

import com.privin.movies.model.Movie
import java.text.SimpleDateFormat
import kotlin.math.floor

object Util {

    fun displayTextVotes(voteCount: Long) =
        if (voteCount < 1000)
            "$voteCount votes"
        else "${String.format("%.1f", floor(voteCount.toDouble()) / 1000)}K votes"


    fun formatDate(date: String, originalFormat: String, displayFormat: String): String {
        return SimpleDateFormat(originalFormat).run {
            val d = parse(date)
            applyPattern(displayFormat)
            d?.let { format(it) } ?: ""
        }
    }
}