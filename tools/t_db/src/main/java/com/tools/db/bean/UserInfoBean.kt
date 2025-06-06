package com.tools.db.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tools.db.Constants

/**
 * @Author: james
 * @Date: 2024/4/16 16:39
 * @Description:
 */
@Entity(tableName = Constants.TABLE.TABLE_USER_NAME)
class UserInfoBean {

    @PrimaryKey
    var id: String = ""

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "avatar")
    var avatar: String? = null

    @ColumnInfo(name = "birthday")
    var birthday: String? = null
}