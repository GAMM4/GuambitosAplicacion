package com.gammadelta.gambitos.Login

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.gammadelta.gambitos.Padre.InicioPadresActivity

import com.gammadelta.gambitos.R
import com.gammadelta.gambitos.Registro.RegistroActivity

class IngresarActivity : AppCompatActivity() {

    private var texto: TextView? = null
    private var coolvetica: Typeface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar)

        val fuente1 = "fuentes/coolvetica.ttf"
        this.coolvetica = Typeface.createFromAsset(assets, fuente1)

        texto = findViewById<View>(R.id.guambitos) as TextView?
        texto!!.typeface = coolvetica
    }

    fun irInicioPadre(view: View) {
        val intent = Intent(this, InicioPadresActivity::class.java)
        startActivity(intent)
    }

    fun irRegistro(view: View) {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}
