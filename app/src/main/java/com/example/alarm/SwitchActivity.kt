package com.example.alarm

    import android.annotation.SuppressLint
    import android.os.Bundle
    import android.widget.Switch
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity

        class SwitchActivity : AppCompatActivity() {

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.alarmscreen)

            val switchbutton: Switch = findViewById(R.id.switchbutton)

            switchbutton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(this@SwitchActivity, "Switch aktif", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@SwitchActivity, "Switch pasif", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }