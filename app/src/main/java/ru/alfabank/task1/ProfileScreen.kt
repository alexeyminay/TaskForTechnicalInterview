package ru.alfabank.task1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.alfabank.task1.repository.ProfileInfo

@Composable
fun ProfileScreen(profileInfo: ProfileInfo?) {
    if (profileInfo == null) return

    Column(Modifier.padding(horizontal = 16.dp)) {
        Row {
            Text(text = stringResource(R.string.first_name), color = Color.Black)
            Text(text = profileInfo.profile.firstName)
        }

        Row {
            Text(text = stringResource(R.string.last_name), color = Color.Black)
            Text(text = profileInfo.profile.lastName)
        }

        Row {
            Text(text = stringResource(R.string.organization_info), color = Color.Black)
            Text(text = profileInfo.jobInfo.organizationName)
        }
    }
}