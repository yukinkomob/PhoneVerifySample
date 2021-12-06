package com.ikeda.azumino.nagano.phoneverifysample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ikeda.azumino.nagano.phoneverifysample2.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(applicationContext)).get(
            MainViewModel::class.java
        )
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authTelButton.setOnClickListener {
            val phoneNumber = binding.telEditText.text.toString()
            var phoneNumberE164: String? = null
            if (phoneNumber.length != 11) {
                return@setOnClickListener
            } else {
                phoneNumberE164 = "+81" + phoneNumber.substring(1)
            }
            phoneNumberE164?.let {
                val options = PhoneAuthOptions.newBuilder()
                    .setPhoneNumber(it)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

        binding.sendAuthCodeButton.setOnClickListener {
            val code = binding.authCodeEditText.text.toString()
            // 認証コードを送信
            val authVerificationId = binding.verificationIdTextView.text.toString()
            val credential = PhoneAuthProvider.getCredential(authVerificationId, code)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    val msg = if (task.isSuccessful) "認証コードの確認に成功しました" else "認証コードの確認に失敗しました"
                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
        }
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(applicationContext, "SMS認証要求に成功しました", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(applicationContext, "SMS認証要求に失敗しました", Toast.LENGTH_SHORT).show()
            p0.printStackTrace()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Toast.makeText(applicationContext, "SMS認証要求を行いました", Toast.LENGTH_SHORT).show()
            binding.verificationIdTextView.text = verificationId
        }
    }
}
