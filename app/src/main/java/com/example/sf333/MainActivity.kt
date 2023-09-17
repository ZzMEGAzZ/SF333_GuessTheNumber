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
                // A surface container using the 'background' color from the theme
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

@Composable
fun Layout() {
    var randNum: Int by remember { mutableStateOf(randomNumber()) }
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var guessCount by remember { mutableStateOf(0) } // Initialize guess count

    val buttonText = when (result) {
        "That's Right" -> "Try Again"
        else -> "Check"
    }

    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "guess the number between 1 - 100",
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
            text = result,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = "Guess Count: $guessCount", // Display guess count
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Button(
            onClick = {
                when (result) {
                    "That's Right" -> {
                        randNum = randomNumber()
                        input = ""
                        result = ""
                        guessCount = 0 // Reset guess count
                    }
                    else -> {
                        result = checkNumber(input, randNum)
                        if (result != "That's Right") {
                            guessCount++ // Increment guess count on incorrect guess
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

private fun checkNumber(guess: String, randomNumber: Int): String {
    val input = guess.toIntOrNull() ?: 0

    println(input)
    return when {
        input == 0 -> "Try Again"
        input < randomNumber -> "Number Should be Higher"
        input > randomNumber -> "Number Should be Lower"
        else -> "That's Right"
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
