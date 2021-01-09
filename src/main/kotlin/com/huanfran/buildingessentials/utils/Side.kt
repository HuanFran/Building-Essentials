package com.huanfran.buildingessentials.utils

enum class Side(val client: Boolean, val server: Boolean) {


    CLIENT_ONLY(true, false),

    SERVER_ONLY(false, true),

    BOTH(true, true)


}