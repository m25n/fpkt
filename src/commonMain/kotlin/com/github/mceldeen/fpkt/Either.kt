package com.github.mceldeen.fpkt

sealed class Either<out A, out B> {
    data class Left<out A>(internal val v: A) : Either<A, Nothing>()
    data class Right<out B>(internal val v: B) : Either<Nothing, B>()
}

fun <A, B, C> Either<A, B>.map(f: (B) -> C): Either<A, C> =
    when (this) {
        is Either.Right -> Either.Right(f(this.v))
        is Either.Left -> this
    }

fun <A, B, C> Either<A, B>.mapLeft(f: (A) -> C): Either<C, B> =
    when (this) {
        is Either.Right -> this
        is Either.Left -> Either.Left(f(this.v))
    }

fun <A, B, C, D> Either<A, B>.bimap(l: (A) -> C, r: (B) -> D): Either<C, D> =
    when (this) {
        is Either.Right -> Either.Right(r(this.v))
        is Either.Left -> Either.Left(l(this.v))
    }

fun <A, B, C> Either<A, (B) -> C>.ap(e: Either<A, B>): Either<A, C> =
    when (this) {
        is Either.Right -> e.map(this.v)
        is Either.Left -> this
    }

fun <A, B, C> Either<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> =
    when (this) {
        is Either.Right -> f(this.v)
        is Either.Left -> this
    }

fun <A, B> Either<A, B>.alt(e: Either<A, B>): Either<A, B> =
    when (this) {
        is Either.Right -> this
        is Either.Left -> e
    }

fun <A, B, C> Either<A, B>.either(l: (A) -> C, r: (B) -> C): C =
    when (this) {
        is Either.Right -> r(this.v)
        is Either.Left -> l(this.v)
    }

fun <A, B> Either<A, B>.getOrElse(l: (A) -> B): B =
    this.either(l, { it })

fun <A, B> Either<A, B>.getOrDefault(l: B): B =
    this.either({ l }, { it })


fun <A, B> Either<A, B>.getOrNull(): B? =
    this.either({ null }, { it })