package com.torres.pokdex

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle().apply {
            putString("mensaje", "Integración completa")
        }
        analytics.logEvent("InitScreen", bundle)

        setup()

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                val firebaseUser = firebaseAuth.currentUser
                                if (firebaseUser != null) {
                                    ListaPokemons(firebaseUser.email ?: "", TipoProvider.GOOGLE)
                                } else {
                                    Alerta()
                                }
                            } else {
                                Log.e("TAG", "Firebase signInWithCredential failed", authTask.exception)
                                Alerta()
                            }
                        }


                    }
                } catch (e: ApiException) {
                    Log.e("MainActivity", "Google sign-in failed", e)
                    Alerta()
                }
            }
        }
    }

    private fun setup() {
        title = "Autenticacion"
        val ingresar: Button = findViewById(R.id.btningresar)
        val registrar: Button = findViewById(R.id.btnregistrar)
        val google: Button = findViewById(R.id.btngoogle)
        val email: EditText = findViewById(R.id.txtemail)
        val password: EditText = findViewById(R.id.txtpassword)

        registrar.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
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
                firebaseAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            ListaPokemons(it.result?.user?.email ?: "", TipoProvider.BASIC)
                        } else {
                            Alerta()
                        }
                    }
            }
        }

        google.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_key))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            signInLauncher.launch(googleClient.signInIntent)
        }
    }

    private fun Alerta() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error al autentificar al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun ListaPokemons(email: String, provider: TipoProvider) {
        val listaIntent = Intent(this, listapokemons::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(listaIntent)
    }
}
