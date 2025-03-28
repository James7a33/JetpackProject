package com.tools.db.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tools.logger.logA

/**
 * Author: james
 * Created: 2024/8/19 16:47
 * Description: 数据库监听
 */
class MyCallback : RoomDatabase.Callback() {

    private val TAG = MyRoomDataBase::class.java.simpleName

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        "RoomDatabase onCreate success".logA(TAG)
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        "RoomDatabase onOpen success".logA(TAG)
    }
}
