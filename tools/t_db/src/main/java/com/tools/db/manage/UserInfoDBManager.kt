package com.tools.db.manage

import com.tools.db.database.MyRoomDataBase
import com.tools.db.entity.UserInfoBean
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @Author: james
 * @Date: 2024/4/16 17:29
 * @Description:
 */
object UserInfoDBManager {

    private val userInfoDao by lazy { MyRoomDataBase.getInstance().userInfoDao() }

    /**
     * 保存视频列表数据
     */
     fun saveVideoList(list: MutableList<UserInfoBean>) {
        userInfoDao.insertAll(list)
    }

    /**
     * 插入一个视频数据
     * @param userInfo
     */
    fun insertVideoInfo(userInfo: UserInfoBean) {
        MainScope().launch {
            userInfoDao.insert(userInfo)
        }
    }

    /**
     * 删除全部数据
     */
    fun deleteAll(userInfo: UserInfoBean) {
        MainScope().launch {
            userInfoDao.deleteAll()
        }
    }

    /**
     * 根据当前数据删除
     */
    fun delete(userInfo: UserInfoBean) {
        MainScope().launch {
            userInfoDao.delete(userInfo)
        }
    }


    /**
     * 根据Id 删除
     * @param id
     */
    fun deleteId(id: Int) {
        MainScope().launch {
            userInfoDao.deleteByIds(id)
        }
    }

    /**
     * 根据userInfo更新视频Item
     * @param userInfo
     */
    fun update(userInfo: UserInfoBean) {
        MainScope().launch {
            userInfoDao.update(userInfo)
        }
    }

    /**
     * 获取视频列表
     */
    fun getVideoList(): MutableList<UserInfoBean>? {
        return userInfoDao.queryAll()
    }

    /**
     * 根据id获取视频Item
     * @param id
     */
    fun getVideoItem(id: Int): UserInfoBean? {
        return userInfoDao.queryByIds(id)
    }
}