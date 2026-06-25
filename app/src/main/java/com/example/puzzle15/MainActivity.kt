package com.example.puzzle15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puzzle15.ui.theme.Puzzle15Theme
import kotlin.math.absoluteValue
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var outerlist by remember { mutableStateOf(Array(4) { MutableList(4) { 0 } }) }
            var isFirstClick = remember { mutableStateOf(true) }
            var x1 by remember { mutableIntStateOf(0) }
            var y1 by remember { mutableIntStateOf(0) }
            shufflePuzzle(outerlist)
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
                                .padding(top = 30.dp)
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
                                            var isAdjacentZero =
                                                outerlist[i].getOrNull(j + 1) == 0 || outerlist[i].getOrNull(
                                                    j - 1
                                                ) == 0 || outerlist.getOrNull(i + 1)
                                                    ?.get(j) == 0 || outerlist.getOrNull(i - 1)
                                                    ?.get(j) == 0
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .fillMaxSize()
                                                    .then(
                                                        if (isAdjacentZero && outerlist[i][j] != 0 && isFirstClick.value) {
                                                            Modifier
                                                                .background(Color(0xFFE0E0E0))
                                                                .clickable {
                                                                    x1 = j
                                                                    y1 = i
                                                                    isFirstClick.value = false
                                                                }
                                                        } else if (outerlist[i][j] == 0 && !isFirstClick.value) {
                                                            Modifier
                                                                .background(Color(0xFF6B6B6B))
                                                                .clickable {
                                                                    outerlist[i][j] = outerlist[y1][x1]
                                                                    outerlist[y1][x1] = 0
                                                                    isFirstClick.value = true
                                                                }
                                                        } else if (outerlist[i][j] == 0 && isFirstClick.value) {
                                                            Modifier
                                                                .background(Color.DarkGray)
                                                        } else {
                                                            Modifier
                                                        }
                                                    )
                                            )
                                            {
                                                Text(
                                                    text = if (outerlist[i][j] == 0) "" else outerlist[i][j].toString(),
                                                    modifier = Modifier.align(Alignment.Center),
                                                    fontSize = 30.sp
                                                )
                                            }
                                        }
                                    }
                                }
//                            Button(onClick = {
//                                shufflePuzzle(outerlist)
//                                outerlist = outerlist.copyOf()
//                                isFirstClick.value = true
//                            }) {
//                                Text(text = "Shuffle")
//                            }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun shufflePuzzle(
    outerArray: Array<MutableList<Int>>
){
    val pieces = arrayOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
    pieces.shuffle()
    for(i in 0..3){
        for(j in 0..3){
            outerArray[i][j] = pieces[i*4+j]
        }

    }
}

fun isSolved(outerArray: Array<MutableList<Int>>): Boolean {
    for (i in 0..3) {
        for (j in 0..3) {
            val expected = if (i == 3 && j == 3) 0 else i * 4 + j + 1
            if (outerArray[i][j] != expected) return false
        }
    }
    return true
}