package com.example.alarm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        val textViewLogin: TextView = findViewById(R.id.textViewLogin)
        val editTextFullName: EditText = findViewById(R.id.editTextFullName)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextNewPassword: EditText = findViewById(R.id.editTextNewPassword)

        buttonRegister.setOnClickListener {
            // Kaydol butonuna tıklanınca yapılacak işlemler
            val adinizSoyadiniz = editTextFullName.text.toString()
            val epostaAdresi = editTextEmail.text.toString()
            val yeniSifre = editTextNewPassword.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        textViewLogin.setOnClickListener {
            // "Giriş yapın" metnine tıklanınca yapılacak işlemler
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
