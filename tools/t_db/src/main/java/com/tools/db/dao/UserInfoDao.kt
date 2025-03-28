package com.tools.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tools.db.DBConstants
import com.tools.db.entity.UserInfoBean

/**
 * @author: james
 * @Description:
 * @Date: 2023/5/16 10:52
 */
@Dao
interface UserInfoDao {

    /*插入*/
    @Insert(entity = UserInfoBean::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: UserInfoBean?)

    /*插入 全部*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: MutableList<UserInfoBean>?)

    /*删除*/
    @Query("delete from ${DBConstants.TABLE.TABLE_USER_NAME}")
    fun deleteAll()

    /*删除指定实体*/
    @Delete
    fun delete(vararg persons: UserInfoBean?)

    /*根据id删除*/
    @Query("delete from ${DBConstants.TABLE.TABLE_USER_NAME} where id in (:ids)")
    fun deleteByIds(ids: Int)

    /*修改*/
    @Update
    fun update(users: UserInfoBean?)

    /*根据Id查询*/
    @Query("select * from ${DBConstants.TABLE.TABLE_USER_NAME} where id in (:ids)")
    fun queryByIds(ids: Int): UserInfoBean

    /*查询所有*/
    @Query("select * from ${DBConstants.TABLE.TABLE_USER_NAME} order by id desc")
    fun queryAll(): MutableList<UserInfoBean>?

}
