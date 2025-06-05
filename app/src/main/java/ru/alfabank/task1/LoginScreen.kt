package ru.alfabank.task1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alfabank.task1.repository.LoginData

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