package com.egiwon.publictransport.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ServiceResult", strict = false)
class BusStationRouteInfo {

    @field:Element(name = "msgHeader")
    var msgHeader: MsgHeader? = null

    @field:Element(name = "msgBody")
    var routeInfoMsgBody: RouteMsgBody? = null
}

@Root(name = "msgBody", strict = false)
class RouteMsgBody {
    @field:ElementList(inline = true, name = "itemList")
    var itemList = mutableListOf<BusStationRouteInfoItem>()
}

@Root(name = "itemList", strict = false)
data class BusStationRouteInfoItem(
    @field:Element(name = "busRouteId", required = false)
    var busRouteId: String = "",
    @field:Element(name = "busRouteNm", required = false)
    var busRouteNm: String = "",
    @field:Element(name = "busRouteType", required = false)
    var busRouteType: Int = 0,
    @field:Element(name = "firstBusTm", required = false)
    var firstBusTm: String = "",
    @field:Element(name = "firstBusTmLow", required = false)
    var firstBusTmLow: String = "",
    @field:Element(name = "lastBusTm", required = false)
    var lastBusTm: String = "",
    @field:Element(name = "lastBusTmLow", required = false)
    var lastBusTmLow: String = "",
    @field:Element(name = "length", required = false)
    var length: String = "",
    @field:Element(name = "nextBus", required = false)
    var nextBus: String = "",
    @field:Element(name = "stBegin", required = false)
    var stBegin: String = "",
    @field:Element(name = "stEnd", required = false)
    var stEnd: String = "",
    @field:Element(name = "term", required = false)
    var term: String = ""
)