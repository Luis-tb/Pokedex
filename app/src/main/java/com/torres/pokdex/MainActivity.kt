package com.torres.pokdex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("mensaje", "Integración completa")
        analytics.logEvent("InitScreen", bundle)
        setup()
    }

    private fun setup() {
        title = "Autenticacion"
        var ingresar: Button = findViewById(R.id.btningresar)
        var registrar: Button = findViewById(R.id.btnregistrar)
        var email: EditText = findViewById(R.id.txtemail)
        var password: EditText = findViewById(R.id.txtpassword)
        registrar.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(), password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        ListaPokemons(it.result?.user?.email ?: "", TipoProvider.BASIC)
                    } else {
                        Alerta()
                    }
                }
            }
        }
        ingresar.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email.text.toString(), password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        ListaPokemons(it.result?.user?.email ?: "", TipoProvider.BASIC)
                    } else {
                        Alerta()
                    }
                }
            }
        }
    }

    private fun Alerta() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error al autentificar al usuario")
        builder.setPositiveButton("Aceptar", null)
        var dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun ListaPokemons(email: String, provider: TipoProvider) {
        var Lista = Intent(this, listapokemons::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(Lista)
    }

}

private fun onClickSalir(view: View?) {
    finish()
}
