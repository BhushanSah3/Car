package com.example.car

import android.app.AlertDialog
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var bon: Button
    private lateinit var boff: Button
    private lateinit var bnext: Button
    private lateinit var bshow: Button
    private lateinit var tname: TextView

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var selectedDeviceAddress: String? = null
    private var bluetoothSocket: BluetoothSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bon = findViewById(R.id.on)
        bshow = findViewById(R.id.show)
        bnext = findViewById(R.id.next)
        boff = findViewById(R.id.off)
        tname = findViewById(R.id.devicename)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        bon.setOnClickListener { enableBluetooth() }
        bshow.setOnClickListener { showPairedDevices() }
        bnext.setOnClickListener { connectToSelectedDevice() }
        boff.setOnClickListener { turnOffBluetooth() }
    }

    private fun enableBluetooth() {
        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
    }

    private fun showPairedDevices() {
        val pairedDevices = bluetoothAdapter?.bondedDevices
        if (pairedDevices.isNullOrEmpty()) {
            Toast.makeText(this, "No bonded devices found", Toast.LENGTH_SHORT).show()
            return
        }

        val deviceList = pairedDevices.map { "${it.name} - ${it.address}" }
        val deviceNamesArray = deviceList.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a device")
        builder.setItems(deviceNamesArray) { _, which ->
            val selectedDevice = deviceList[which]
            selectedDeviceAddress = selectedDevice.split(" - ")[1].trim()
            tname.text = selectedDevice  // Show selected device on screen
        }
        builder.show()
    }

    private fun connectToSelectedDevice() {
        if (selectedDeviceAddress == null) {
            Toast.makeText(this, "No device selected", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Connecting to device...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Thread {
            val device = bluetoothAdapter?.getRemoteDevice(selectedDeviceAddress!!)
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard UUID for SPP

            try {
                bluetoothSocket = device?.createRfcommSocketToServiceRecord(uuid)
                bluetoothSocket?.connect()
                progressDialog.dismiss()

                // Move to the next activity
                val intent = Intent(this, Controler::class.java)
                intent.putExtra(EXTRA_DEVICE_ADDRESS, selectedDeviceAddress)
                startActivity(intent)

            } catch (e: IOException) {
                progressDialog.dismiss()
                runOnUiThread {
                    Toast.makeText(this, "Failed to connect to device", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }.start()
    }

    private fun turnOffBluetooth() {
        if (bluetoothAdapter?.isEnabled == true) {
            bluetoothAdapter?.disable()
            selectedDeviceAddress = null
            tname.text = ""
            Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val REQUEST_ENABLE_BLUETOOTH = 2
        const val EXTRA_DEVICE_ADDRESS = "device_address"
    }
}
