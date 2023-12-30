import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.MainActivity
import com.example.alarm.R
import com.example.alarm.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Layout dosyanızın adını düzelttim

        auth = FirebaseAuth.getInstance()

        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textViewRegister: TextView = findViewById(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "E-posta ve şifre boş olamaz", Toast.LENGTH_SHORT).show()
            }
        }

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Giriş başarılı, istediğiniz işlemleri yapabilirsiniz
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Giriş başarısız, kullanıcıya hata mesajı gösterin
                    Toast.makeText(
                        this,
                        "Giriş başarısız. E-posta veya şifrenizi kontrol edin.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
