@file:Suppress("UNREACHABLE_CODE")

package com.github.mceldeen.fpkt.test

import com.github.mceldeen.fpkt.*
import kotlin.test.Test
import kotlin.test.assertEquals

class EitherTest {
    @Test
    fun testMap() {
        assertEquals(
            Either.Right("123"),
            Either.Right(123).map { it.toString() }
        )
        assertEquals(
            Either.Left(123),
            Either.Left(123).map { it.toString() }
        )
    }

    @Test
    fun testMapLeft() {
        assertEquals(
            Either.Left("123"),
            Either.Left(123).mapLeft { it.toString() }
        )
        assertEquals(
            Either.Right(123),
            Either.Right(123).mapLeft { it.toString() }
        )
    }

    @Test
    fun testBimap() {
        assertEquals(
            Either.Left("123"),
            Either.Left(123).bimap({ it.toString() }, { it.toInt() })
        )
        assertEquals(
            Either.Right(123),
            Either.Right("123").bimap({ it.toString() }, { it.toInt() })
        )
    }

    @Test
    fun testApply() {
        assertEquals(
            Either.Right(124),
            Either.Right({ v: Int -> v + 1 }).ap(Either.Right(123))
        )
    }

    @Test
    fun testFlatMap() {
        assertEquals(
            Either.Left("123"),
            Either.Right(123).flatMap { Either.Left(it.toString()) }
        )
        assertEquals(
            Either.Left(123),
            Either.Left(123).flatMap { Either.Left(it.toString()) }
        )
    }

    @Test
    fun testAlt() {
        assertEquals(
            Either.Right(123),
            Either.Right(123).alt(Either.Right(456))
        )
        assertEquals(
            Either.Right(456),
            Either.Left("error").alt(Either.Right(456))
        )
    }

    @Test
    fun testEither() {
        assertEquals(
            124,
            Either.Right(123).either({ e: String -> e.toInt() }, { i: Int -> i + 1 })
        )
        assertEquals(
            456,
            Either.Left("456").either({ e: String -> e.toInt() }, { i: Int -> i + 1 })
        )
    }

    @Test
    fun testGetOrElse() {
        assertEquals(
            123,
            Either.Right(123).getOrElse { e: String -> e.toInt() }
        )
        assertEquals(
            456,
            Either.Left("456").getOrElse { e: String -> e.toInt() }
        )
    }

    @Test
    fun testGetOrDefault() {
        assertEquals(
            123,
            Either.Right(123).getOrDefault(0)
        )
        assertEquals(
            0,
            Either.Left("456").getOrDefault(0)
        )
    }

    @Test
    fun testGetOrNull() {
        assertEquals(
            123,
            Either.Right(123).getOrNull()
        )
        assertEquals(
            null,
            Either.Left("456").getOrNull()
        )
    }
}
