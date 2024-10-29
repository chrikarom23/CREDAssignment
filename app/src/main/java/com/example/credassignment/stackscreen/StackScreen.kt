package com.example.credassignment.stackscreen

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import com.example.credassignment.R
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

@Composable
fun StackScreen(modifier: Modifier = Modifier) {
    //This composable calls the Stack manager. Since I couldnt reliably build a Repository and thus a viewModel, I have hard coded the data in here.
    val context = LocalContext.current
    val viewModel: StackViewModel = viewModel()

    val creditAmount by viewModel.creditAmount
    val emiOptionCost by viewModel.emiOptionCost
    val emiOptionDuration by viewModel.emiOptionDuration
    val bank by viewModel.bank


    StackManager(
        stackSize = 3,
        composable1 = {
            StackScreen1(
                footer = "stash is instant. money will be credited within seconds",
                value = creditAmount,
                minValue = 500f,
                maxValue = 500000f,
                header = "Credit Amount",
                description = "@1.04% monthly",
                onPositionChanged = { newVal -> viewModel.updateCredit(newVal); Log.i("tag", newVal.toString()) }
            )
        },
        composable1OpenStateValues = OpenStateValues(
            title = "nikunj, how much do you need?",
            description = "move the dial and set any amount you need upto ₹487891 "
        ),
        composable1ClosedStateValues = ClosedStateValues(
            key1 = "credit amount",
            key1Value = "₹${creditAmount.toInt().toString()}"
        ),
        composable2 = {
            StackScreen2(items = viewModel.emiItems, updateItems = { cost, duration ->
                viewModel.updateEmiOption(cost, duration)
            }, checkedItem = emiOptionCost)
        },
        composable2OpenStateValues = OpenStateValues(
            title = "how do you wish to repay?",
            description = "choose one of the recommended plans or make your own"
        ),
        composable2ClosedStateValues = ClosedStateValues(
            key1 = "EMI",
            key1Value = emiOptionCost,
            key2 = "duration",
            key2Value = emiOptionDuration
        ),
        composable3 = {
            StackScreen3(
                bank = "State Bank of India",
                accountNumber = 98991749212,
                updateItems = { newBank ->
                    viewModel.updateBank(newBank)
                },
                checkedItem = bank
            )
        },
        composable3OpenStateValues = OpenStateValues(
            title = "where should we send the money?",
            description = "amount will be credited to this bank account, EMI will also be debited from this bank account"
        ),
        doneEvent = { Toast.makeText(context, "Money Sent", Toast.LENGTH_LONG).show() }
    )
}

@Composable
fun StackManager(
    modifier: Modifier = Modifier,
    stackSize: Int = 2,
    composable1: @Composable () -> Unit,
    composable1OpenStateValues: OpenStateValues = OpenStateValues(),
    composable1ClosedStateValues: ClosedStateValues = ClosedStateValues(),
    composable2: @Composable () -> Unit,
    composable2OpenStateValues: OpenStateValues = OpenStateValues(),
    composable2ClosedStateValues: ClosedStateValues = ClosedStateValues(),
    composable3: @Composable () -> Unit = {},
    composable3OpenStateValues: OpenStateValues = OpenStateValues(),
    composable3ClosedStateValues: ClosedStateValues = ClosedStateValues(),
    composable4: @Composable () -> Unit = {},
    composable4OpenStateValues: OpenStateValues = OpenStateValues(),
    composable4ClosedStateValues: ClosedStateValues = ClosedStateValues(),
    doneEvent: () -> Unit
) {
    var level1 by remember { mutableStateOf(true) }
    var level2 by remember { mutableStateOf(true) }
    var level3 by remember { mutableStateOf(true) }
    var level4 by remember { mutableStateOf(true) }

    fun level1Invert() {
        level1 = !level1
    }

    fun level2Invert() {
        level2 = !level2
    }

    fun level3Invert() {
        level3 = !level3
    }

    fun level4Invert() {
        level4 = !level4
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { TopBar() },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(top = innerPadding.calculateTopPadding() - 20.dp)),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                StackItem(
                    composable = composable1,
                    clickedState = level1,
                    clickStateEvent = { level1Invert() },
                    stackSize = stackSize,
                    stackId = 1,
                    doneEvent = doneEvent,
                    openStateValues = composable1OpenStateValues,
                    closedStateValues = composable1ClosedStateValues,
                    nextStackItem = {
                        StackItem(
                            composable = composable2,
                            clickedState = level2,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            clickStateEvent = { level2Invert() },
                            stackSize = stackSize,
                            stackId = 2,
                            doneEvent = doneEvent,
                            openStateValues = composable2OpenStateValues,
                            closedStateValues = composable2ClosedStateValues,
                            nextStackItem = {
                                StackItem(
                                    composable = composable3,
                                    clickedState = level3,
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    clickStateEvent = { level3Invert() },
                                    stackSize = stackSize,
                                    stackId = 3,
                                    doneEvent = doneEvent,
                                    openStateValues = composable3OpenStateValues,
                                    closedStateValues = composable3ClosedStateValues,
                                    nextStackItem = {
                                        StackItem(
                                            composable = composable4,
                                            clickedState = level4,
                                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                                            clickStateEvent = { level4Invert() },
                                            stackSize = stackSize,
                                            stackId = 4,
                                            openStateValues = composable4OpenStateValues,
                                            closedStateValues = composable4ClosedStateValues,
                                            doneEvent = doneEvent
                                        )
                                    })
                            })
                    })
            }
        }
    }


    BackHandler {
        if (!level4) level4 = true
        else if (!level3) level3 = true
        else if (!level2) level2 = true
        else if (!level1) level1 = true
        else exitProcess(0)
    }
}

@Composable
fun StackItem(
    modifier: Modifier = Modifier,
    composable: @Composable () -> Unit,
    clickedState: Boolean,
    clickStateEvent: () -> Unit,
    nextStackItem: @Composable () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    stackSize: Int,
    stackId: Int,
    openStateValues: OpenStateValues,
    closedStateValues: ClosedStateValues,
    doneEvent: () -> Unit
) {
        if (stackId <= stackSize) {
            Surface(
                modifier = modifier
                    .fillMaxSize(),
                color = color,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ) {
                Column {
                    CardTitleRow(
                        clickStateEvent = clickStateEvent,
                        clickedState = clickedState,
                        openStateValues = openStateValues,
                        closedStateValues = closedStateValues
                    )
                    AnimatedVisibility(
                        visible = clickedState,
                        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn()
                    ) {
                        CardMainContent(
                            composable = composable,
                            clickStateEvent = if (stackId == stackSize) {
                                doneEvent
                            } else clickStateEvent
                        )
                    }
                    if (stackId < stackSize) nextStackItem()
                }
            }
    }
}

@Composable
fun CardMainContent(
    modifier: Modifier = Modifier,
    clickStateEvent: () -> Unit,
    composable: @Composable () -> Unit,
) {
    var loadDelay by remember { mutableStateOf(true) }
    LaunchedEffect(0) {
        delay(500)
        loadDelay = false
    }
    if(!loadDelay){
        Column(modifier = modifier) {
            StackMainContent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f),
                composable = composable
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.08f)
            )
            Button(
                onClick = { clickStateEvent() },
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(), shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ) {
                Text(text = "Some text", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
    else{
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun StackMainContent(modifier: Modifier = Modifier, composable: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(15.dp))
    ) {
        composable()
    }
}

@Composable
fun CardTitleRow(
    modifier: Modifier = Modifier,
    clickStateEvent: () -> Unit,
    clickedState: Boolean,
    openStateValues: OpenStateValues,
    closedStateValues: ClosedStateValues
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 16.dp)
            .padding(vertical = 20.dp)
            .alpha(if (clickedState) 1f else 0.55f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            if (clickedState) {
                AnimatedVisibility(
                    visible = clickedState,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        Text(
                            text = openStateValues.title,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = openStateValues.description,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            } else {
                AnimatedVisibility(
                    visible = !clickedState,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row {
                        Column {
                            Text(
                                text = closedStateValues.key1,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = closedStateValues.key1Value,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = closedStateValues.key2,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = closedStateValues.key2Value,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = !clickedState,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            IconButton(
                onClick = { clickStateEvent() }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

data class OpenStateValues(
    val title: String = "This is the Title",
    val description: String = "This is the description"
)

data class ClosedStateValues(
    val key1: String = "",
    val key1Value: String = "",
    val key2: String = "",
    val key2Value: String = ""
)

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    NavigationBar(containerColor = Color.Transparent, modifier = Modifier.padding(top = 20.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Close Window",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.QuestionMark,
                    contentDescription = "Close Window",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StackScreenPreview() {
    AppTheme {
        StackScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StackScreenNightPreview() {
    AppTheme {
        StackScreen()
    }
}