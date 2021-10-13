package com.app.pranavfreshworks.model

data class GiphyResponseModel(
    val data: ArrayList<Data>,
    val pagination: Pagination,
    val meta: Meta
)

data class Data(
    val type: String,
    val id: String,
    val url: String,
    val title: String,
    val content_url: String,
    val images: Images,
)

data class Images(
    val downsized_medium: DownSizeMedium,
)

data class DownSizeMedium(

    val height: Int,
    val width: Int,
    val size: Int,
    val url: String
)

data class Meta(
    val status: Int,
    val msg: String,
    val response_id: String
)

data class Pagination(
    val count: Int?,
    val offset: Int?,
    val total_count: Int?
)