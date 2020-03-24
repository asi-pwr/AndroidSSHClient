package com.asi.sshclient.ssh

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jcraft.jsch.*
import kotlinx.coroutines.*
import java.io.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SshService {
    private val ssh = JSch()
    private var session: Session? = null
    private var channel: Channel? = null
    @Volatile
    private var outputStream : OutputStream = ByteArrayOutputStream()
    //used for observing changes
    val outputLiveData = MutableLiveData<StringBuffer>(StringBuffer(""))
    @Volatile
    private var inputStream : InputStream = "".byteInputStream()
    private val inOutLock = ReentrantLock()

    suspend fun start() : String {
        return withContext(Dispatchers.IO) {
            val name = ""
            val host = ""
            val password = ""
            session = ssh.getSession(name, host, 22)
            session!!.setPassword(password)
            session!!.userInfo = MyUserInfo
            try {
                session!!.connect(7000)
            } catch (e: Exception) {
                Log.d("JSCH", "Couldn't connect")
                return@withContext "Couldn't connect\n"
            }

            channel = session!!.openChannel("shell")
            channel?.connect(3000)
            inOutLock.withLock {
                outputStream = channel!!.outputStream
                inputStream = channel!!.inputStream
            }

            watchInput()

            return@withContext if (channel!!.isConnected){
                Log.d("JSCH", "Connected")
                "Connected\n"
            }else {
                Log.d("JSCH", "Couldn't connect")
                "Couldn't connect\n"
            }
        }
    }

    fun send(str: String) {
        GlobalScope.launch(Dispatchers.IO) {
            inOutLock.withLock {
                outputStream.write(str.toByteArray())
                outputStream.flush()
            }
        }
    }

    private fun watchInput() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                inOutLock.withLock {
                    if (inputStream.available() > 0) {
                        val temp = ByteArray(inputStream.available())
                        inputStream.read(temp, 0, inputStream.available())
                        outputLiveData.postValue(outputLiveData.value?.append(temp.toString(Charsets.UTF_8)))
                    }
                }
                delay(50)
            }
        }
    }

    fun stop() {
        GlobalScope.launch(Dispatchers.IO) {
            channel?.disconnect()
            session?.disconnect()
        }
    }

    private companion object MyUserInfo : UserInfo, UIKeyboardInteractive {
        override fun getPassword(): String? {
            return null
        }

        override fun promptYesNo(str: String) : Boolean {
            //TODO implement function
            return true
        }

        override fun getPassphrase(): String? {
            return null
        }

        override fun promptPassphrase(message: String): Boolean {
            return false
        }

        override fun promptPassword(message: String): Boolean {
            return false
        }

        override fun showMessage(message: String) {
            //TODO implement function
        }

        override fun promptKeyboardInteractive(
            destination: String,
            name: String,
            instruction: String,
            prompt: Array<String>,
            echo: BooleanArray
        ): Array<String>? {
            return null
        }
    }
}



