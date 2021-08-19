package com.ai.tirios.dataclasses

class ResponseDownloadPic : ArrayList<ResponseDownloadPic.ResponseDownloadPicItem>(){
    data class ResponseDownloadPicItem(
        val document: Any,
        val documentExtension: Any,
        val documentSide: Int,
        val documentType: Int,
        val documentURL: String,
        val mobile: Any,
        val s3FileName: String
    )
}