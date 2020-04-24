package info.nightscout.androidaps.receivers

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import dagger.android.DaggerBroadcastReceiver
import info.nightscout.androidaps.events.EventBTChange
import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.logging.LTag
import info.nightscout.androidaps.plugins.bus.RxBusWrapper
import javax.inject.Inject

class BTReceiver : DaggerBroadcastReceiver() {
    @Inject lateinit var rxBus: RxBusWrapper
    @Inject lateinit var aapsLogger: AAPSLogger

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val device : BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

        aapsLogger.debug(LTag.CORE,  "BT Device: " + device)

        when (intent.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED ->
                rxBus.send(EventBTChange(EventBTChange.Change.CONNECT, device.name))
            BluetoothDevice.ACTION_ACL_DISCONNECTED ->
                rxBus.send(EventBTChange(EventBTChange.Change.DISCONNECT, device.name))
        }
    }
}