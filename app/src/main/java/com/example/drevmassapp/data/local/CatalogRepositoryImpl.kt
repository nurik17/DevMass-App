package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.data.remote.DevMassApi
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val api: DevMassApi
) : CatalogRepository {

    override suspend fun getProducts(token: String): ProductX {
        val bearerToken = "Bearer $token"
        return api.getProducts(bearerToken)
    }

    override suspend fun getFamousProducts(token: String): ProductX {
        val bearerToken = "Bearer $token"
        return api.getFamousProducts(bearerToken)

    }

    override suspend fun getProductsPriceDown(token: String): ProductX {
        val bearerToken = "Bearer $token"
        return api.getProductsPriceDown(bearerToken)
    }

    override suspend fun getProductsPriceUp(token: String): ProductX {
        val bearerToken = "Bearer $token"
        return api.getProductsPriceUp(bearerToken)
    }

    /*private fun getToken(token: String): String {
        return "Bearer $token"
    }*/
}