package com.tools.blue

import android.bluetooth.BluetoothGatt
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.tools.logger.logA
import java.util.UUID

/**
 * Author: james
 * Created: 2024/12/16 09:52
 * Description: 蓝牙扩展函数
 */

val TAG: String = BluetoothUtils::class.java.name

/**
 * 配置扫描，增加扫描速度
 * @param deviceName    String  设备名称
 * @param isFuzzy       Boolean 是否模糊搜索设备名称
 * @param deviceMac     String  设备 mac 地址
 */
fun blueScanConfig(
    deviceName: String = "",
    isFuzzy: Boolean = false,
    deviceMac: String = "",
    uuid: Array<UUID> = arrayOf()
) {
    val scanRuleConfig = BleScanRuleConfig.Builder()
        .setDeviceName(isFuzzy, deviceName)
        .setDeviceMac(deviceMac) //扫描当前设备的 mac 地址
        .setServiceUuids(uuid)
        .setScanTimeOut(10000) //扫描当前设备的超时时间 10秒
        .build()
    BleManager.getInstance().initScanRule(scanRuleConfig)
}

/**
 * 根据设备 deviceMac 扫描相匹配的设备
 * @param deviceMac 设备 mac 地址
 * @param device 设备信息
 */
fun blueScanDevice(deviceMac: String, device: (BleDevice?, isSuccess: Boolean) -> Unit) {
    BleManager.getInstance().scan(object : BleScanCallback() {
        override fun onScanStarted(success: Boolean) {
            "开始扫描 onScanStarted".logA(TAG)
        }

        override fun onScanning(bleDevice: BleDevice) {
            "设备信息 onScanning " + bleDevice.toString().logA(TAG)
            device.invoke(bleDevice, false)
        }

        override fun onScanFinished(scanResultList: MutableList<BleDevice>) {
            "扫描结束 onScanFinished".logA(TAG)
            if (scanResultList.isNotEmpty()) {
                scanResultList.forEach {
                    if (it.mac == deviceMac) {
                        device.invoke(it, true)
                        "扫描结束 当前设备 " + device.toString().logA(TAG)
                    }
                }
            } else {
                device.invoke(null, true)
                "扫描结束 无扫描蓝牙设备信息".logA(TAG)
            }
        }
    })
}

/**
 * 扫描蓝牙设备
 * @param deviceList 设备信息
 */
fun blueScanDeviceList(deviceList: (MutableList<BleDevice>) -> Unit) {
    BleManager.getInstance().scan(object : BleScanCallback() {
        override fun onScanStarted(success: Boolean) {
            "开始扫描 onScanStarted".logA(TAG)
        }

        override fun onScanning(bleDevice: BleDevice?) {
            "设备信息 onScanning " + bleDevice.toString().logA(TAG)
        }

        override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
            "扫描结束 onScanFinished".logA(TAG)
            if (scanResultList.isNullOrEmpty()) {
                "扫描结束 无扫描蓝牙设备信息".logA(TAG)
            } else {
                deviceList.invoke(scanResultList)
            }
        }
    })
}

/**
 * 连接蓝牙设备
 * @param device 蓝牙设备
 * @param deviceMac 蓝牙设备 mac 地址
 * @param isSuccess true 成功 false 失败
 */
fun blueConnection(device: BleDevice, deviceMac: String = "", isSuccess: (Boolean) -> Unit) {
    BleManager.getInstance().connect(deviceMac.ifEmpty { device.mac }, object : BleGattCallback() {
        override fun onStartConnect() {
            "开始连接 onStartConnect " + device.mac + device.name.logA(TAG)
        }

        override fun onConnectFail(bleDevice: BleDevice, exception: BleException?) {
            "连接失败 onConnectFail " + bleDevice.mac + bleDevice.name.logA(TAG)
            isSuccess.invoke(false)
        }

        override fun onConnectSuccess(bleDevice: BleDevice, gatt: BluetoothGatt?, status: Int) {
            "连接成功 onConnectSuccess " + bleDevice.mac + bleDevice.name.logA(TAG)
            isSuccess.invoke(true)
        }

        override fun onDisConnected(
            isActiveDisConnected: Boolean,
            bleDevice: BleDevice,
            gatt: BluetoothGatt?,
            status: Int
        ) {
            "断开连接 onConnectSuccess " + bleDevice.mac + bleDevice.name.logA(TAG)
        }
    })
}

