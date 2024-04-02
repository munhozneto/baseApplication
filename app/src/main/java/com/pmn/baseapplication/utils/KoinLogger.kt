package com.pmn.baseapplication.utils

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class KoinLogger : Logger() {
    override fun display(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> {
                Timber.d(msg)
            }

            Level.ERROR -> {
                Timber.i(msg)
            }

            Level.INFO -> {
                Timber.i(msg)
            }

            Level.NONE -> {
            }

            Level.WARNING -> {
                Timber.w(msg)
            }
        }
    }
}