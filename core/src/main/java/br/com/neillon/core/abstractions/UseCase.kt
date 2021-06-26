package br.com.neillon.core.abstractions

interface UseCase<in T, out S> {
    suspend fun execute(param: T): S
}