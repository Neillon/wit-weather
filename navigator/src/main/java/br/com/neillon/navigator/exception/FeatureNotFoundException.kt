package br.com.neillon.navigator.exception

data class FeatureNotFoundException(val errorMessage: String) : Exception(errorMessage)