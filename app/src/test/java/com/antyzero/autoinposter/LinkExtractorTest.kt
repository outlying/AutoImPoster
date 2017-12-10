package com.antyzero.autoinposter

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory


class LinkExtractorTest {

    val linkExtractor: LinkExtractor = LinkExtractor.Default

    @TestFactory
    internal fun findLink(): Iterator<DynamicTest> {
        return TestData.validMessages.map {
            dynamicTest("Looking for link in ${it.first}") {
                assertThat(linkExtractor.hasActivationLink(it.second)).isTrue()
            }
        }.iterator()
    }
}