package com.example.exam_02_aam.repositories

import com.example.exam_02_aam.network.ClienteRetrofit
import com.example.exam_02_aam.service.ApiService

class UserRepository(private val apiService: ApiService = ClienteRetrofit.getInstanciaRetrofit) {

}