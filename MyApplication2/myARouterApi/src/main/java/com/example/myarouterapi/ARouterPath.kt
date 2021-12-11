package com.example.myarouterapi

import com.example.myarouter_annotations.RouterBean

interface ARouterPath {
    fun getPathMap(): Map<String, RouterBean>
}