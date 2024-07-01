package com.example.pdfdownloader.VMs

import com.example.whatsapp.VMs.API.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private  val  retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val api: WhatsAppApi by lazy {
        retrofit.create(WhatsAppApi::class.java)
    }

}