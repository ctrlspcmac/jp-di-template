package utilities

import android.util.Log
import com.jg.app.no.idea.constants.TimeInTheDay
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    const val AFTERNOON_HOUR = 12
    const val EVENING_HOUR = 18

    const val DEFAULT_DATE_FORMAT = "MMM d, yyyy HH:mm"

    fun getTimeInDay(): TimeInTheDay {
        val today = Calendar.getInstance()
        val tod = today.get(Calendar.HOUR_OF_DAY)
        var result = TimeInTheDay.MORNING
        if( tod >= EVENING_HOUR || tod <= 5) {
            result = TimeInTheDay.EVENING
        }
        else {
            if(tod >= AFTERNOON_HOUR){
                result = TimeInTheDay.AFTERNOON
            }
        }
        return result
    }

    fun formatDateFromLong(time: Long): String {
        var result = ""
        try {
            val date = Date(time)
            val format = SimpleDateFormat(DEFAULT_DATE_FORMAT)
            result = format.format(date)
        }
        catch (e: java.lang.Exception){
            Log.e("TimeConversion", e.message.toString())
        }
        return result
    }

    fun getDateNowStr(): String {
        val today = Calendar.getInstance()
        val format = SimpleDateFormat(DEFAULT_DATE_FORMAT)
        return format.format(today.time)
    }
}