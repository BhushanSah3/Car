package com.example.car

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class Controler : AppCompatActivity() {

    private lateinit var bForward: ImageButton
    private lateinit var bBack: ImageButton
    private lateinit var bLeft: ImageButton
    private lateinit var bRight: ImageButton

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var arduinoDevice: BluetoothDevice? = null
    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    private var isForwardPressed = false
    private var isBackPressed = false
    private var isLeftPressed = false
    private var isRightPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controler)

        bForward = findViewById(R.id.Forward)
        bBack = findViewById(R.id.Back)
        bLeft = findViewById(R.id.Left)
        bRight = findViewById(R.id.Right)

        connectToArduino()

        bForward.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isForwardPressed = true
                    sendCommand("F")
                }
                MotionEvent.ACTION_UP -> {
                    isForwardPressed = false
                    sendCommand("S")
                }
            }
            true
        }

        bBack.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isBackPressed = true
                    sendCommand("B")
                }
                MotionEvent.ACTION_UP -> {
                    isBackPressed = false
                    sendCommand("S")
                }
            }
            true
        }

        bRight.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isRightPressed = true
                    sendCommand("R")
                }
                MotionEvent.ACTION_UP -> {
                    isRightPressed = false
                    sendCommand("S")
                }
            }
            true
        }

        bLeft.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isLeftPressed = true
                    sendCommand("L")
                }
                MotionEvent.ACTION_UP -> {
                    isLeftPressed = false
                    sendCommand("S")
                }
            }
            true
        }
    }

    private fun connectToArduino() {
        val address = "00:23:09:01:61:8A" // Replace with your Arduino Bluetooth module MAC address

        arduinoDevice = bluetoothAdapter.getRemoteDevice(address)
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard UUID for SPP

        try {
            socket = arduinoDevice?.createRfcommSocketToServiceRecord(uuid)
            socket?.connect()
            outputStream = socket?.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(
                    this@Controler,
                    "Failed to connect to Arduino device",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun sendCommand(command: String) {
        outputStream?.let {
            try {
                it.write(command.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } ?: run {
            Toast.makeText(this, "Output stream is null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
