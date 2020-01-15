package com.egiwon.publictransport.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ServiceResult", strict = false)
class ServiceResult {

    @field:Element(name = "msgHeader")
    var msgHeader: MsgHeader? = null

    @field:Element(name = "msgBody")
    var msgBody: MsgBody? = null
}

@Root(name = "msgHeader", strict = false)
class MsgHeader {

    @field:Element(name = "headerCd")
    var headerCd: String = ""
    @field:Element(name = "headerMsg")
    var headerMsg: String = ""
    @field:Element(name = "itemCount")
    var itemCount: Int = 0
}

@Root(name = "msgBody", strict = false)
class MsgBody {
    @field:ElementList(inline = true, name = "itemList")
    var itemList = mutableListOf<Item>()
}

@Root(name = "itemList", strict = false)
data class Item(
    @field:Element(name = "arsId")
    var arsId: String = "",
    @field:Element(name = "posX")
    var posX: String = "",
    @field:Element(name = "posY")
    var posY: String = "",
    @field:Element(name = "stId")
    var stId: String = "",
    @field:Element(name = "stNm")
    var stNm: String = "",
    @field:Element(name = "tmX")
    var tmX: String = "",
    @field:Element(name = "tmY")
    var tmY: String = ""
)