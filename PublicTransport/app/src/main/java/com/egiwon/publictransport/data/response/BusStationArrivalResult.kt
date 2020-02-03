package com.egiwon.publictransport.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ServiceResult", strict = false)
class BusStationArrivalResult {

    @field:Element(name = "msgHeader")
    var msgHeader: MsgHeader? = null

    @field:Element(name = "msgBody")
    var arrivalMsgBody: ArrivalMsgBody? = null
}

@Root(name = "msgBody", strict = false)
class ArrivalMsgBody {
    @field:ElementList(inline = true, name = "itemList")
    var itemList = mutableListOf<ArrivalInfoItem>()
}

@Root(name = "itemList", strict = false)
data class ArrivalInfoItem(
    @field:Element(name = "adirection")
    var adirection: String = "",
    @field:Element(name = "arrmsg1")
    var arrmsg1: String = "",
    @field:Element(name = "arrmsg2")
    var arrmsg2: String = "",
    @field:Element(name = "arrmsgSec1")
    var arrmsgSec1: String = "",
    @field:Element(name = "arrmsgSec2")
    var arrmsgSec2: String = "",
    @field:Element(name = "arsId")
    var arsId: String = "",
    @field:Element(name = "busRouteId")
    var busRouteId: String = "",
    @field:Element(name = "busType1")
    var busType1: String = "",
    @field:Element(name = "busType2")
    var busType2: String = "",
    @field:Element(name = "congestion")
    var congestion: String = "",
    @field:Element(name = "deTourAt")
    var deTourAt: String = "",
    @field:Element(name = "firstTm")
    var firstTm: String = "",
    @field:Element(name = "gpsX")
    var gpsX: String = "",
    @field:Element(name = "gpsY")
    var gpsY: String = "",
    @field:Element(name = "isArrive1")
    var isArrive1: String = "",
    @field:Element(name = "isArrive2")
    var isArrive2: String = "",
    @field:Element(name = "isFullFlag1")
    var isFullFlag1: String = "",
    @field:Element(name = "isFullFlag2")
    var isFullFlag2: String = "",
    @field:Element(name = "isLast1")
    var isLast1: String = "",
    @field:Element(name = "isLast2")
    var isLast2: String = "",
    @field:Element(name = "lastTm")
    var lastTm: String = "",
    @field:Element(name = "nextBus")
    var nextBus: String = "",
    @field:Element(name = "nxtStn")
    var nxtStn: String = "",
    @field:Element(name = "posX")
    var posX: String = "",
    @field:Element(name = "posY")
    var posY: String = "",
    @field:Element(name = "repTm1", required = false)
    var repTm1: String = "",
    @field:Element(name = "rerdieDiv1")
    var rerdieDiv1: String = "",
    @field:Element(name = "rerdieDiv2")
    var rerdieDiv2: String = "",
    @field:Element(name = "rerideNum1")
    var rerideNum1: String = "",
    @field:Element(name = "rerideNum2")
    var rerideNum2: String = "",
    @field:Element(name = "routeType")
    var routeType: String = "",
    @field:Element(name = "rtNm")
    var rtNm: String = "",
    @field:Element(name = "sectNm")
    var sectNm: String = "",
    @field:Element(name = "sectOrd1")
    var sectOrd1: String = "",
    @field:Element(name = "sectOrd2")
    var sectOrd2: String = "",
    @field:Element(name = "stId")
    var stId: String = "",
    @field:Element(name = "stNm")
    var stNm: String = "",
    @field:Element(name = "staOrd")
    var staOrd: String = "",
    @field:Element(name = "stationNm1", required = false)
    var stationNm1: String = "",
    @field:Element(name = "stationNm2", required = false)
    var stationNm2: String = "",
    @field:Element(name = "stationTp")
    var stationTp: String = "",
    @field:Element(name = "term")
    var term: String = "",
    @field:Element(name = "traSpd1")
    var traSpd1: String = "",
    @field:Element(name = "traSpd2")
    var traSpd2: String = "",
    @field:Element(name = "traTime1")
    var traTime1: String = "",
    @field:Element(name = "traTime2")
    var traTime2: String = "",
    @field:Element(name = "vehId1")
    var vehId1: String = "",
    @field:Element(name = "vehId2")
    var vehId2: String = ""
)