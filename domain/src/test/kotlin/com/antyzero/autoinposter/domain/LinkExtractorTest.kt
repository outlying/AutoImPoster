package com.antyzero.autoinposter.domain

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory


class LinkExtractorTest {

    private val linkExtractor: LinkExtractor = LinkExtractor

    @TestFactory
    internal fun findLink(): Iterator<DynamicTest> {
        return TestMessages.validMessages.map {
            dynamicTest("Looking for link in ${it.file}") {
                assertThat(linkExtractor.hasActivationLink(it.message)).isTrue()
            }
        }.iterator()
    }

    internal fun findId(): Iterator<DynamicTest> {
        return TestMessages.validMessages.map {
            dynamicTest("Looking for ID ${it.linkId} in ${it.file}") {
                assertThat(linkExtractor.linkId(it.message)).isEqualTo(it.linkId)
            }
        }.iterator()
    }
}