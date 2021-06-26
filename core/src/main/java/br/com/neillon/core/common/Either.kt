package br.com.neillon.core.common

sealed class Either<out E, out V> {
    data class Value<out L>(val packet: L) : Either<L, Nothing>()
    data class Error<out R>(val packet: R) : Either<Nothing, R>()

    companion object {
        fun <R> error(value: R): Either<Nothing, R> =
            Error(value)

        fun <L> value(value: L): Either<L, Nothing> =
            Value(value)
    }
}