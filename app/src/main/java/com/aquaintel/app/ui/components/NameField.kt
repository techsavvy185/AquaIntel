package com.aquaintel.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aquaintel.app.ui.theme.LocalAppColors

@Composable
fun NameField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Full Name"
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Name Icon"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LocalAppColors.current.primary,
            unfocusedBorderColor = LocalAppColors.current.divider,
            focusedLabelColor = LocalAppColors.current.primary,
            unfocusedLabelColor = LocalAppColors.current.editText,
            cursorColor = LocalAppColors.current.primary
        )
    )
}
