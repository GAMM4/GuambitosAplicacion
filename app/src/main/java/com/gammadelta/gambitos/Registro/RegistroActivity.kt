package com.gammadelta.gambitos.Registro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.gammadelta.gambitos.R

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun irRegistroPadre(view: View) {
        val intent = Intent(this, RegistroPadreActivity::class.java)
        startActivity(intent)
    }

    fun irRegistroMedico(view: View) {
        val intent = Intent(this, RegistroMedicoActivity::class.java)
        startActivity(intent)
    }
}
