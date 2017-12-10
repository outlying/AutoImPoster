package com.antyzero.autoinposter.domain

interface LinkExtractor {

    fun hasActivationLink(message: String): Boolean

    fun linkId(message: String): String?

    companion object Default : LinkExtractor {

        private val LINK_REGEXP = "https://kurier\\.inpost\\.pl/rp\\.aspx\\?t=([\\w\\d]+)".toRegex()

        override fun hasActivationLink(message: String): Boolean = matchInput(message) != null

        override fun linkId(message: String): String? {
            return matchInput(message)?.value
        }

        private fun matchInput(text: String) = LINK_REGEXP.find(text)
    }
}