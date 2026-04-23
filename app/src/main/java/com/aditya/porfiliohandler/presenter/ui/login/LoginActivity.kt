package com.aditya.porfiliohandler.presenter.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.porfiliohandler.data.datasource.AuthDataSource
import com.aditya.porfiliohandler.data.local.SessionManager
import com.aditya.porfiliohandler.data.repository.AuthRepositoryImpl
import com.aditya.porfiliohandler.databinding.ActivityLoginBinding
import com.aditya.porfiliohandler.presenter.ui.main.MainActivity
import com.aditya.porfiliohandler.presenter.viewmodel.LoginViewModel
import com.aditya.porfiliohandler.presenter.viewmodel.LoginViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.aditya.porfiliohandler.domain.usecase.LoginUseCase
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    private lateinit var useremail : TextInputEditText
    private lateinit var userpassword : TextInputEditText
    private lateinit var loginBtn : Button

    private val viewModel: LoginViewModel by viewModels {
        val dataSource = AuthDataSource(FirebaseAuth.getInstance())
        val authRepo = AuthRepositoryImpl(dataSource)
        val loginUseCase = LoginUseCase(authRepo)
        LoginViewModelFactory(loginUseCase)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mSessionManager = SessionManager(this)

        if (mSessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        useremail = binding.useremail
        userpassword = binding.userpassword
        loginBtn = binding.loginbutton

        setClickListeners()
        setObservers()
    }

    fun setClickListeners(){
        loginBtn.setOnClickListener {
            var email = useremail.text.toString()
            var password = userpassword.text.toString()

            if(email.isEmpty()){
                useremail.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if (password.isEmpty()){
                userpassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            viewModel.login(email, password)

        }
    }

    private fun setObservers() {
        viewModel.state.observe(this) { result ->
            if(result!=null) {

                result.onSuccess { user ->
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    // Save session
                    val mSessionManager = SessionManager(this)
                    mSessionManager.saveUserSession(user.id, user.email)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("USER_ID", user.id)
                    intent.putExtra("USER_EMAIL", user.email)
                    startActivity(intent)
                    finish()
                }

                result.onFailure { exception ->
                    Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}