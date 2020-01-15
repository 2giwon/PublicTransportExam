package com.egiwon.publictransport.ext

fun String.toStationId(): String =
    if (toInt() != 0) {
        StringBuilder()
            .append(substring(0, 2))
            .append('-')
            .append(substring(2, length))
            .toString()
    } else {
        toString()
    }
