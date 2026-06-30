package com.example.puzzle15.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puzzle15.view.theme.Puzzle15Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: PuzzleViewModel = viewModel()
            val state by viewModel.state.collectAsState()

            Puzzle15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(300.dp)
                                .padding(top = 30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .height(300.dp)
                            ) {
                                for (i in 0..3) {
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxSize()
                                            .background(Color.LightGray)
                                    ) {
                                        for (j in 0..3) {
                                            val isClickable = state.isTileClickable(i, j)
                                            val isSelected = state.firstSelection == (i to j)
                                            val tileValue = state.board[i][j]

                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxSize()
                                                    .background(
                                                        when {
                                                            tileValue == 0 -> Color.DarkGray
                                                            isSelected -> Color.Yellow
                                                            isClickable -> Color(0xFFE0E0E0)
                                                            else -> Color.LightGray
                                                        }
                                                    )
                                                    .clickable(enabled = isClickable) {
                                                        viewModel.onIntent(PuzzleIntent.TileClicked(j, i))
                                                    }
                                            ) {
                                                Text(
                                                    text = if (tileValue == 0) "" else tileValue.toString(),
                                                    modifier = Modifier.align(Alignment.Center),
                                                    fontSize = 30.sp,
                                                    color = if (tileValue == 0) Color.White else Color.Black
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            
                            Text(
                                text = "Moves: ${state.moves}",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(16.dp)
                            )

                            if (state.isSolved) {
                                Text(
                                    text = "Solved!",
                                    fontSize = 32.sp,
                                    color = Color.Green,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            Button(onClick = {
                                viewModel.onIntent(PuzzleIntent.ShuffleClicked)
                            }) {
                                Text(text = "Shuffle")
                            }
                        }
                    }
                }
            }
        }
    }
}
