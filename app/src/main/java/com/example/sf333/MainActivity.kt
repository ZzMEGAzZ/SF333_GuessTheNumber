package com.example.sf333

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sf333.ui.theme.SF333Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SF333Theme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Layout()
                }
            }
        }
    }
}

enum class GuessResult {
    Correct, TooLow, TooHigh, Invalid
}

@Composable
fun Layout() {
    var randNum: Int by remember { mutableStateOf(randomNumber()) }
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(GuessResult.Invalid) }
    var guessCount by remember { mutableStateOf(0) }

    val buttonText = when (result) {
        GuessResult.Correct -> "Try Again"
        else -> "Check"
    }

    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Guess the number between 1 - 100",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        InputGuessNumber(
            value = input,
            onValueChanged = { input = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Text(
            text = result.name,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = "Guess Count: $guessCount",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Button(
            onClick = {
                when (result) {
                    GuessResult.Correct -> {
                        randNum = randomNumber()
                        input = ""
                        result = GuessResult.Invalid
                        guessCount = 0
                    }
                    else -> {
                        result = checkNumber(input, randNum)
                        if (result != GuessResult.Correct) {
                            guessCount++
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = buttonText)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputGuessNumber(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.guessNum)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun checkNumber(guess: String, randomNumber: Int): GuessResult {
    val input = guess.toIntOrNull() ?: return GuessResult.Invalid
    return when {
        input < randomNumber -> GuessResult.TooLow
        input > randomNumber -> GuessResult.TooHigh
        else -> GuessResult.Correct
    }
}

private fun randomNumber(): Int {
    return (1..100).random()
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SF333Theme {
        Layout()
    }
}
