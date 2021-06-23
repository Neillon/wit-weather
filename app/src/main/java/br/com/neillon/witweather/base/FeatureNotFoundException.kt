package br.com.neillon.witweather.base

data class FeatureNotFoundException(val errorMessage: String) : Exception(errorMessage)