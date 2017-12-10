package com.antyzero.autoinposter

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory


class InPostMessageDetectorTest {

    @TestFactory
    internal fun areAllKeywordAreFound(): Iterator<DynamicTest> {
        return TestData.validMessages.map {
            dynamicTest("Testing message ${it.first}") {
                assertThat(InPostMessageDetector.containKeywords(it.second)).isEqualTo(1f)
            }
        }.iterator()
    }

    @Test
    internal fun checkValidNumber() {
        InPostMessageDetector.isNumberValid("InPost")
    }

    @Test
    internal fun checkInvalidNumber() {
        InPostMessageDetector.isNumberValid("Someone else")
    }
}