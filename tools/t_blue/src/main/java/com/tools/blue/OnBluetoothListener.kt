package com.tools.blue

import com.clj.fastble.data.BleDevice

/**
 * Author: james
 * Created: 2024/12/16 09:48
 * Description: 蓝牙连接监听
 */
interface OnBluetoothListener {
    /**
     * 连接成功
     * @param bleDevice BleDevice 设备信息
     */
    fun onConnectionSuccess(bleDevice: BleDevice)

    /**
     * 连接失败
     * @param bleDevice BleDevice 设备信息
     * @param msg String 失败原因
     */
    fun onConnectionFail(bleDevice: BleDevice, msg: String)

    /**
     * 关闭
     * @param msg String
     */
    fun onCancel(msg: String)
}