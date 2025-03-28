package com.tools.blue

import android.app.Application
import com.clj.fastble.BleManager
import java.util.UUID

/**
 * Author: james
 * Created: 2024/12/16 09:57
 * Description: 蓝牙连接
 */
class BluetoothUtils private constructor() {

    val TAG: String = BluetoothUtils::class.java.name

    private var isInitBlue = false // 是否初始化

    companion object {
        @Volatile
        private var instance: BluetoothUtils? = null

        fun getInstance(): BluetoothUtils {
            return instance ?: synchronized(this) {
                instance ?: BluetoothUtils().also { instance = it }
            }
        }
    }

    /**
     * 配置蓝牙连接
     */
    fun init(context: Application) {
        if (!isInitBlue) {
            BleManager.getInstance()
                .enableLog(false) //重新连接和间隔
                .setSplitWriteNum(50)
                .setConnectOverTime(5000)
                .setOperateTimeout(10000)
                .init(context)
            isInitBlue = true // 标记为已配置
        }
    }

    /**
     * 配置扫描，增加扫描速度
     * @param deviceName    String  设备名称
     * @param isFuzzy       Boolean 是否模糊搜索设备名称
     * @param deviceMac     String  设备 mac 地址
     */
    fun initScanConfig(
        deviceName: String = "",
        isFuzzy: Boolean = false,
        deviceMac: String = "",
        uuid: Array<UUID> = arrayOf()
    ) {
        if(checkInit()){
            blueScanConfig(deviceName, isFuzzy, deviceMac, uuid)
        }
    }


    private fun checkInit():Boolean {
        if (!isInitBlue) {
            throw NullPointerException("Bluetooth No init")
        }
        return true
    }

}