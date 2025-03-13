package labs.creative.bookxpertassigment.common.utils


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


@Composable
fun CustomAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    body: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = {},
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            body()
        },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        shape = RoundedCornerShape(5.dp),
    )
}