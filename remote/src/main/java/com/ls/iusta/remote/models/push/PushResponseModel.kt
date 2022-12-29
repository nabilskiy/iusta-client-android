package com.ls.iusta.remote.models.push

import com.google.gson.annotations.SerializedName
import com.ls.iusta.remote.models.customer.CustomerModel
import com.ls.iusta.remote.models.terms.TermsModel
import com.ls.iusta.remote.models.worker.RatingModel

data class PushResponseModel(
    var success: Boolean,
    var pageNumber: Int,
    var perPage: Int,
    var pageSize: Int,
    var totalPagesCount: Int,
    @SerializedName("response")
    val response: List<PushModel>
)
