package com.app.test_lock_device

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //https://suihan74.github.io/posts/2021/01_21_00_lock_device_from_app/
//    https://qiita.com/tadaogi/items/a6b0bafc734e40cd3ccf

    private val ADMIN_INTENT = 1
    private var mDevicePolicyManager: DevicePolicyManager? = null
    private var mComponentName: ComponentName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDevicePolicyManager = getSystemService(
            DEVICE_POLICY_SERVICE
        ) as DevicePolicyManager
        mComponentName = ComponentName(this, Admin::class.java)
    }
    override fun onResume() {
        super.onResume()
    }
    fun LockScreen(view: View?) {
        Log.d("LockScreen", "LockScreen")
        if (mDevicePolicyManager!!.isAdminActive(mComponentName!!)) {
            mDevicePolicyManager!!.lockNow()
        } else {
            Log.d("LockScreen", "admin not active")
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Administrator description")
            startActivityForResult(intent, ADMIN_INTENT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADMIN_INTENT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(applicationContext, "Registered As Admin", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Failed to register as Admin",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun buttonOnClick(view: View){

    }


}