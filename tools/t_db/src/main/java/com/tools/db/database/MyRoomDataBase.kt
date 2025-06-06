package com.tools.db.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frame.base.BaseApp
import com.tools.db.Constants
import com.tools.db.dao.UserInfoDao
import com.tools.db.bean.UserInfoBean

/**
 * @Author: james
 * @Date: 2024/4/16 16:38
 * @Description:
 */
@Database(
    entities = [UserInfoBean::class],
    version = 1,
    exportSchema = false
)
abstract class MyRoomDataBase : RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao


    companion object {
        private var dataBase: MyRoomDataBase? = null

        //同步锁，可能在多个线程中同时调用
        @Synchronized
        fun getInstance(): MyRoomDataBase {
            Room.inMemoryDatabaseBuilder(BaseApp.getInstance(), MyRoomDataBase::class.java)
            return dataBase ?: Room.databaseBuilder(
                BaseApp.getInstance(),
                MyRoomDataBase::class.java,
                Constants.DB_NAME
            )
                //是否允许在主线程查询，默认是false
                .allowMainThreadQueries()
                //数据库被创建或者被打开时的回调
                .addCallback(MyCallback())
                //指定数据查询的线程池，不指定会有个默认的
                //.setQueryExecutor {  }
                //任何数据库有变更版本都需要升级，升级的同时需要指定migration，如果不指定则会报错
                //数据库升级 1-->2， 怎么升级，以什么规则升级
                .addMigrations(DBMigrations(0,1))
                //设置数据库工厂，用来链接room和SQLite，可以利用自行创建SupportSQLiteOpenHelper，来实现数据库加密
                //.openHelperFactory()
                .build()
        }
    }
}
