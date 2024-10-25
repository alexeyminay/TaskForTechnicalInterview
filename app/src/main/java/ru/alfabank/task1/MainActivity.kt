package ru.alfabank.task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alfabank.task1.repository.LoginData
import ru.alfabank.task1.repository.Profile
import ru.alfabank.task1.repository.ProfileRepository
import ru.alfabank.task1.ui.theme.Task1Theme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy { ProfileViewModel(ProfileRepository(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task1Theme {
                val profile by viewModel.profileState.collectAsState()
                val progressBarState by viewModel.progressBarState.collectAsState()
                val isLoginSuccess by viewModel.isLoginSuccess.collectAsState()
                val loginData by viewModel.loginData.collectAsState()

                if (isLoginSuccess) {
                    ProfileScreen(profile = profile)
                } else {
                    LoginScreen(
                        loginData = loginData,
                        onLoginChanged = viewModel::onLoginChanged,
                        onPasswordChanged = viewModel::onPasswordChanged,
                        onLogin = viewModel::login
                    )
                }

                if (progressBarState) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    loginData: LoginData,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogin: () -> Unit
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(R.string.header),
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        TextField(
            value = loginData.login,
            onValueChange = { onLoginChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = { Text(text = stringResource(R.string.login)) }
        )

        TextField(
            value = loginData.password,
            onValueChange = { onPasswordChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = { Text(text = stringResource(R.string.password)) }
        )

        Button(onClick = { onLogin() }) {
            Text(stringResource(R.string.login_button))
        }
    }
}

@Composable
fun ProfileScreen(profile: Profile?) {
    if (profile == null) return

    Column(Modifier.padding(horizontal = 16.dp)) {
        Row {
            Text(text = stringResource(R.string.first_name), color = Color.Black)
            Text(text = profile.firstName)
        }

        Row {
            Text(text = stringResource(R.string.last_name), color = Color.Black)
            Text(text = profile.lastName)
        }
    }
}