package com.arbitr.test.core

import com.arbitr.test.data.network.model.Ratings
import com.arbitr.test.data.network.model.ResponseData

fun ResponseData.toDomain(): List<Ratings> {
    return listOf(
        Ratings(image = raitings?._0?.image, title = raitings?._0?.title),
        Ratings(image = raitings?._1?.image, title = raitings?._1?.title),
        Ratings(image = raitings?._2?.image, title = raitings?._2?.title),
        Ratings(image = raitings?._3?.image, title = raitings?._3?.title),
        Ratings(image = raitings?._5?.image, title = raitings?._5?.title),
        Ratings(image = raitings?._6?.image, title = raitings?._6?.title),
        Ratings(image = raitings?._7?.image, title = raitings?._7?.title),
        Ratings(image = raitings?._8?.image, title = raitings?._8?.title),
    )
}
