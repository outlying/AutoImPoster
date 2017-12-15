package com.antyzero.autoinposter.domain

import com.antyzero.autoinposter.domain.data.Message
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory


class InPostMessageDetectorTest {

    private val inPostMessageDetector = InPostMessageDetector()

    init {
        @Suppress("UNUSED_VARIABLE")
        val result = TestData.validMessages
                .map { it.message.toLowerCase() }
                .map { it.split(" ") }
                .map { it.toSet() }
                .reduce { acc, set -> acc.intersect(set) }

        // throw IllegalStateException("$result") // for words collecting
    }

    @TestFactory
    internal fun areAllKeywordAreFound(): Iterator<DynamicTest> {
        return TestData.validMessages.map {
            dynamicTest("Testing message ${it.file}") {
                assertThat(inPostMessageDetector.containKeywords(it.message)).isEqualTo(1f)
            }
        }.iterator()
    }

    @Test
    internal fun checkWholeMessage() {
        val message = Message(
                "InPost",
                resourceText("/message_variant_01.txt"))

        val result = inPostMessageDetector.isInPostMessage(message)

        assertThat(result).isTrue()
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