package com.egiwon.publictransport.data.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class ServiceResult(

    @Path("comMsgHeader/msgHeader/headerCd/headerMsg/itemCount/msgBody")
    @Element
    val itemList: List<ItemList>
)

@Xml
data class ItemList(

    @PropertyElement val arsId: Int = 0,
    @PropertyElement val posX: String = "",
    @PropertyElement val posY: String = "",
    @PropertyElement val stId: String = "",
    @PropertyElement val stNm: String = "",
    @PropertyElement val tmX: String = "",
    @PropertyElement val tmY: String = ""
)