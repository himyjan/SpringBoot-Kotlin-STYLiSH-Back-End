package com.example.backend

import java.util.concurrent.atomic.AtomicInteger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Receiver {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(Receiver::class.java)
    }

    private val counter: AtomicInteger = AtomicInteger()

    fun receiveMessage(message: String) {
        LOGGER.info("Received <$message>")
        counter.incrementAndGet()
    }

    fun getCount(): Int {
        return counter.get()
    }
}
