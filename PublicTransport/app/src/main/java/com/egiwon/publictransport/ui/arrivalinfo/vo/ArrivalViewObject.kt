package com.egiwon.publictransport.ui.arrivalinfo.vo

data class ArrivalViewObject(
    val arsId: String = "",
    val stationName: String = "",
    val routeName: String = "",
    val routeWay: String = "",
    val arrivalTime: String = "",
    val nextBus: String = "",
    val routeTypeColor: Int = 0
)