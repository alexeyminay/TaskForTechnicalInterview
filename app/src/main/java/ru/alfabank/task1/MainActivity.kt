package ru.alfabank.task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.alfabank.task1.repository.Repository
import ru.alfabank.task1.ui.theme.Task1Theme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy { ProfileViewModel(Repository(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task1Theme {
                val profileInfo by viewModel.profileInfoState.collectAsState()
                val progressBarState by viewModel.progressBarState.collectAsState()
                val isLoginSuccess by viewModel.isLoginSuccess.collectAsState()
                val loginData by viewModel.loginData.collectAsState()

                if (isLoginSuccess) {
                    ProfileScreen(viewModel, profileInfo = profileInfo)
                } else {
                    LoginScreen(
                        loginData = loginData,
                        onLoginChanged = viewModel::onLoginChanged,
                        onPasswordChanged = viewModel::onPasswordChanged,
                        onLogin = viewModel::login
                    )
                }

                if (progressBarState) {
                    Box {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

