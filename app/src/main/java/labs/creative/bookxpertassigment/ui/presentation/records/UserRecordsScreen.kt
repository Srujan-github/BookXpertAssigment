package labs.creative.bookxpertassigment.ui.presentation.records

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import labs.creative.bookxpertassigment.R
import labs.creative.bookxpertassigment.common.utils.CustomAlertDialog
import labs.creative.bookxpertassigment.common.utils.UiEvent
import labs.creative.bookxpertassigment.domain.model.UserActDetails
import java.util.Locale


@Composable
fun UserRecordsScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: UserRecordsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val userRecords by viewModel.userActs.collectAsState()
    val scope = rememberCoroutineScope()

    fun updateRecord(actId: Int, actName: String, actAlternativeName: String) {
        scope.launch {
            viewModel.updateUserRecord(actId, actName, actAlternativeName)
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar("Record Updated")
        }
    }

    fun deleteRecord(actId: Int) {
        scope.launch {
            viewModel.deleteUserRecord(actId)
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar("Record Deleted")
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is UiEvent.Failure -> {
                Text(text = (uiState as UiEvent.Failure).error)
            }

            is UiEvent.Loading -> {
                CircularProgressIndicator()
            }

            is UiEvent.Success -> {
                UserRecordRecycleView(
                    records = userRecords,
                    onUpdate = ::updateRecord,
                    onDelete = ::deleteRecord
                )
            }

            else -> {
                Button(onClick = { viewModel.fetchData() }) {
                    Text("Fetch Data")
                }
            }
        }
    }
}

@Composable
fun UserRecordRecycleView(
    records: List<UserActDetails>,
    onUpdate: (Int, String, String) -> Unit,
    onDelete: (Int) -> Unit
) {
    var itemToDelete by remember { mutableStateOf<UserActDetails?>(null) }
    val lazyListState = remember { LazyListState() }

    LazyColumn(state = lazyListState) {
        items(records, key = { it.actId }) { userAct ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        itemToDelete = userAct
                        false
                    } else {
                        false
                    }
                },
                positionalThreshold = { it * 0.5f }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(9.dp)
                            .background(Color.Red, shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                },
                content = {
                    UserRecordItem(
                        id = userAct.actId.toString(),
                        name = userAct.actName,
                        alternativeName = userAct.actAlternativeName ?: "",
                        onUpdate = { newName, newAltName ->
                            onUpdate(userAct.actId, newName, newAltName)
                        }
                    )
                }
            )
        }
    }

    itemToDelete?.let { userAct ->
        CustomAlertDialog(
            title = "Delete Record ID:${userAct.actId}",
            body = { Text("Are you sure you want to delete ${userAct.actName}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete(userAct.actId)
                        itemToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { itemToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun UserRecordItem(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    alternativeName: String,
    onUpdate: (String, String) -> Unit
) {
    var isShown by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        tonalElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "ID: $id",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (alternativeName.isNotEmpty()) {
                    Text(
                        text = alternativeName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(onClick = { isShown = true }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    }

    if (isShown) {
        var newName by remember { mutableStateOf(name) }
        var newAltName by remember { mutableStateOf(alternativeName) }
        val intent = remember {
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
            }
        }

        val micLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                newAltName = matches[0]
            }
        }

        CustomAlertDialog(
            title = "Edit",
            body = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column {
                        Text(
                            text = "Name",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        TextField(
                            value = newName,
                            onValueChange = { newName = it },
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column {
                        Text(
                            text = "Alternative Name",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        TextField(
                            value = newAltName,
                            onValueChange = { newAltName = it },
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(
                                    onClick = { micLauncher.launch(intent) }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_mic_24),
                                        contentDescription = "Mic",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    onUpdate(newName, newAltName)
                    isShown = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { isShown = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
