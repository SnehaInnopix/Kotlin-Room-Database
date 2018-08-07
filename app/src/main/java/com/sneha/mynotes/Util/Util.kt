package com.sneha.mynotes.Util

import java.text.SimpleDateFormat
import java.util.Calendar

object Util {


    val currentTime: String
        get() {
            val calander = Calendar.getInstance()
            val simpledateformat = SimpleDateFormat("dd-MM-yyyy HH:mm aa")

            return simpledateformat.format(calander.time)
        }
}
