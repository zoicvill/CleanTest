package com.arbitr.test.data.network.model

import com.google.gson.annotations.SerializedName


data class ResponseData(
    @SerializedName("raitings") var raitings: RatingsNum? = RatingsNum()
)

data class Ratings2(

    @field:SerializedName("image") var image: String? = null,
    @field:SerializedName("title") var title: String? = null
)

data class RatingsNum(

    @field:SerializedName("0")
    var _0: Ratings2 = Ratings2(),
    @field:SerializedName("1")
    var _1: Ratings2 = Ratings2(),
    @field:SerializedName("2")
    var _2: Ratings2 = Ratings2(),
    @field:SerializedName("3")
    var _3: Ratings2 = Ratings2(),
    @field:SerializedName("5")
    var _5: Ratings2 = Ratings2(),
    @field:SerializedName("6")
    var _6: Ratings2 = Ratings2(),
    @field:SerializedName("7")
    var _7: Ratings2 = Ratings2(),
    @field:SerializedName("8")
    var _8: Ratings2 = Ratings2(),
)
