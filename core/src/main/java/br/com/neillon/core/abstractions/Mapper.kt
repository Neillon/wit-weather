package br.com.neillon.core.abstractions

interface Mapper<in T, out S> {
    fun map(item: T): S
}