package com.neoutils.chatp2p.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit
) = Scaffold(
    bottomBar = {
        OutlinedTextField(
            value = uiState.message,
            onValueChange = {
                onAction(HomeAction.OnChange(it))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(HomeAction.OnSend)
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.Send,
                        contentDescription = null,
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .safeContentPadding(),
        )
    }
) { padding ->
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(uiState.messages) { message ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = if (message.myself) {
                    Alignment.CenterEnd
                } else {
                    Alignment.CenterStart
                },
            ) {
                Column(
                    horizontalAlignment = if (message.myself) {
                        Alignment.End
                    } else {
                        Alignment.Start
                    },
                ) {
                    Text(
                        text = message.author,
                        fontSize = 12.sp,
                    )

                    Spacer(Modifier.height(4.dp))

                    Card {
                        SelectionContainer {
                            Text(
                                text = message.body,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            uiState = HomeUiState(
                messages = remember {
                    listOf(
                        Message(
                            author = "Me",
                            body = "Hello, how are you?",
                            myself = true
                        ),
                    )
                },
                message = ""
            ),
            onAction = {}
        )
    }
}