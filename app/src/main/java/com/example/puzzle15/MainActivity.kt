package com.example.puzzle15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
            var x2 by remember { mutableIntStateOf(0) }
            var y1 by remember { mutableIntStateOf(0) }
            var y2 by remember { mutableIntStateOf(0) }
            shufflePuzzle(outerlist)
            Puzzle15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .padding(innerPadding)

                    ) {
                        for (i in 0..3){
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                                    .border(1.dp, Color.Black)
                                    .background(Color.LightGray)
                            ) {
                                for (j in 0..3){
                                    Box(
                                            modifier = if(outerlist[i].getOrNull(j+1)==0 || outerlist[i].getOrNull(j-1)==0 || outerlist.getOrNull(i+1)?.get(j)==0 || outerlist.getOrNull(i-1)?.get(j)==0){
                                                Modifier
                                                    .weight(1f)
                                                    .fillMaxSize()
                                                    .background(Color.Green)
                                                    .border(1.dp, Color.Black)
                                                    .clickable{
                                                    if (isFirstClick.value) {
                                                        x1 = j
                                                        y1 = i
                                                        isFirstClick.value = false
                                                    } else {
                                                        if (outerlist[i][j] == 0) {
                                                            x2 = j
                                                            y2 = i
                                                            outerlist[i][j] = outerlist[y1][x1]
                                                            outerlist[y1][x1] = 0
                                                            isFirstClick.value = true
                                                        }
                                                    }
                                                }
                                            }else{
                                                Modifier
                                                    .weight(1f)
                                                    .fillMaxSize()
                                                    .border(1.dp, Color.Black)

                                            }

                                    )
                                    {
                                        Text(
                                            text = if(outerlist[i][j] == 0) "" else outerlist[i][j].toString(), 
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
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

