package com.muath.composecleanarch.core.ui

import com.muath.composecleanarch.R
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.muath.composecleanarch.core.common.AppError


@Composable
private fun ShowToast(message: String, onDismiss: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        onDismiss()
    }
}
@Composable
fun RenderAppError(
    error: AppError?,
    onDismiss: () -> Unit = {}
) {
    error?.let {
        when (it) {
            is AppError.Network.NoConnection -> {
                ShowToast(stringResource(R.string.error_no_connection), onDismiss)
            }
            is AppError.Network.ServerError -> {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = { Text(stringResource(R.string.error_server)) },
                    text = { Text(it.message) },
                    confirmButton = {
                        TextButton(onClick = onDismiss) {
                            Text(stringResource(R.string.ok))
                        }
                    }
                )
            }
            is AppError.Network.ClientError -> {
                ShowToast(stringResource(R.string.error_client), onDismiss)
            }
            else -> {
                ShowToast(stringResource(R.string.error_unknown), onDismiss)
            }
        }
    }
}
