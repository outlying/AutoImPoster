package com.antyzero.autoinposter.domain

import com.antyzero.autoinposter.domain.logger.Logger
import com.antyzero.autoinposter.domain.network.InPostCalls


class ImPoster(
        val inPostMessageDetector: InPostMessageDetector,
        val linkExtractor: LinkExtractor,
        val inPostCalls: InPostCalls,
        val logger: Logger = Logger) {


}