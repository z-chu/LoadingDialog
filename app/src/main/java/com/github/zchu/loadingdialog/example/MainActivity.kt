package com.github.zchu.loadingdialog.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.zchu.loadingdialog.LoadingDialogFragment

class MainActivity : AppCompatActivity() {

    private val btn1: Button by lazy { findViewById<Button>(R.id.btn1) }
    private val btn2: Button by lazy { findViewById<Button>(R.id.btn2) }
    private val btn3: Button by lazy { findViewById<Button>(R.id.btn3) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener {
            LoadingDialogFragment.newInstance().show(supportFragmentManager, "tag")
        }
        btn2.setOnClickListener {
            LoadingDialogFragment.newInstance(com.github.zchu.loadingdialog.R.style.ThemeOverlay_LoadingDialog_SmallDialog)
                .show(supportFragmentManager, "tag")
        }
        btn3.setOnClickListener {
            LoadingDialogFragment.newInstance(R.style.ThemeOverlay_MyApplication_LoadingDialog)
                .show(supportFragmentManager, "tag")
        }
    }
}