package com.example.myarouterapi

interface ARouterGroup {
    fun getGroupMap(): Map<String, Class<out ARouterPath>>
}