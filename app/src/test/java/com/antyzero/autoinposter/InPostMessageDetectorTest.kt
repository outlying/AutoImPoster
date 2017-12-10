package com.antyzero.autoinposter

import com.antyzero.autoinposter.domain.InPostMessageDetector
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory


class InPostMessageDetectorTest {

    private val inPostMessageDetector = InPostMessageDetector()

    init {
        val result = TestData.validMessages
                .map { it.second.toLowerCase() }
                .map { it.split(" ") }
                .map { it.toSet() }
                .reduce { acc, set -> acc.intersect(set) }

        // throw IllegalStateException("$result") // for words collecting
    }

    @TestFactory
    internal fun areAllKeywordAreFound(): Iterator<DynamicTest> {
        return TestData.validMessages.map {
            dynamicTest("Testing message ${it.first}") {
                assertThat(inPostMessageDetector.containKeywords(it.second)).isEqualTo(1f)
            }
        }.iterator()
    }

    @Test
    internal fun checkValidNumber() {
        val validNumber = "InPost"
        assertThat(inPostMessageDetector.isNumberValid(validNumber)).isEqualTo(1f)
    }

    @Test
    internal fun checkInvalidNumber() {
        val invalidNumber = "Someone else"
        assertThat(inPostMessageDetector.isNumberValid(invalidNumber)).isEqualTo(0f)
    }
}