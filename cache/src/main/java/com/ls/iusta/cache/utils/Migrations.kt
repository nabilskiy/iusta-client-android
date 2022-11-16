package com.ls.iusta.cache.utils

import androidx.room.migration.Migration

class Migrations {
    companion object {
        const val DB_VERSION = 1

        fun getMigrations(): Array<Migration> {
            return arrayOf()
        }
    }
}
