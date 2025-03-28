package com.tools.db.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @Author: james
 * @Date: 2024/4/16 16:51
 * @Description:数据库迁移
 */
class DBMigrations(private val oldVersion: Int, private val newVersion: Int) :
    Migration(oldVersion, newVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {
        when {
            oldVersion == 1 && newVersion == 2 -> {
                // 在版本从 1 升级到 2 时执行的迁移操作 例子
//                database.execSQL("ALTER TABLE cer ADD COLUMN password TEXT DEFAULT ''")
//                val sql =
//                    "CREATE TABLE IF NOT EXISTS invoice (userId TEXT PRIMARY KEY NOT NULL , open INTEGER NOT NULL DEFAULT 1, invoiceType INTEGER NOT NULL DEFAULT 1, name TEXT NOT NULL, email TEXT NOT NULL, taxNumber TEXT NOT NULL)"
//                database.execSQL(sql)
            }
        }
    }
}
