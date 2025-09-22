package com.muath.composecleanarch.features.settings.presentation


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.settings.domain.entity.Language
import com.muath.composecleanarch.features.settings.domain.entity.ThemeMode
import com.muath.composecleanarch.features.settings.domain.entity.getDisplayName


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    applyChrome: (ChromeConfig) -> Unit,
    navController: NavController,
) {

    LaunchedEffect(Unit) {
        applyChrome(ChromeConfig(title = "Settings", showTopBar = true))
    }
    val settings by viewModel.settings.collectAsState()
    val canUseDynamicColor: Boolean = viewModel.canUseDynamicColor

    // Screen content

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (canUseDynamicColor) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Use Dynamic Color",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))
                Switch(checked = settings.useDynamicColor,
                    onCheckedChange = {
                    viewModel.setSettings(settings.copy(useDynamicColor = it))
                })
            }

            Spacer(Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Dark Mode",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.weight(1f))

                if(settings.themeMode == ThemeMode.SYSTEM){
                    Switch(checked = isSystemInDarkTheme(), onCheckedChange = {
                        viewModel.setSettings(settings.copy(themeMode = mapTheme(it)))
                    })

                }else{
                    Switch(checked = settings.themeMode == ThemeMode.DARK, onCheckedChange = {
                        viewModel.setSettings(settings.copy(themeMode = mapTheme(it)))
                    })
                }

            }

            // Language
            Spacer(Modifier.height(10.dp))
            LanguageSettingDropdown(selected = settings.language, onSelected = {
                viewModel.setSettings(settings.copy(language = it))
            })
        }

    }

}

fun mapTheme(isDark: Boolean): ThemeMode {
    return if (isDark) ThemeMode.DARK else ThemeMode.LIGHT
}
//
//@Composable
//fun LanguageSettingRadio(
//    selected: Language,
//    onSelected: (Language) -> Unit
//) {
//    Column(
//        modifier = Modifier
//    ) {
//        Row() {
//            RadioButton(
//                selected = selected == Language.ENGLISH,
//                onClick = { onSelected(Language.ENGLISH) }
//            )
//            Text("English")
//        }
//
//        Row() {
//            RadioButton(
//                selected = selected == Language.ARABIC,
//                onClick = { onSelected(Language.ARABIC) }
//            )
//            Text("العربية")
//        }
//    }
//}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun LanguageSettingDropdown(
    selected: Language,
    onSelected: (Language) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selected.getDisplayName(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Language") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    )
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Language.values().forEach { lang ->
                    DropdownMenuItem(
                        text = { Text(lang.getDisplayName()) },
                        onClick = {
                            onSelected(lang)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
