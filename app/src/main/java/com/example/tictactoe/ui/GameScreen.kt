package com.example.tictactoe.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.tictactoe.*
import com.example.tictactoe.ui.theme.Aqua
import com.example.tictactoe.ui.theme.BlueCustom
import com.example.tictactoe.ui.theme.GrayBackground
import com.example.tictactoe.ui.theme.GreenishYellow


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel

) {


    val state = viewModel.state


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player '0' : ${state.playerCirlceCount}", fontSize = 16.sp)
            Text(text = "Draw: ${state.drawCount}", fontSize = 16.sp)
            Text(text = "Player 'X' : ${state.playerCrossCount}", fontSize = 16.sp)
        }
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = BlueCustom
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {
            BoardBase()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3),
                ){
                viewModel.boardItems.forEach{ (cellNo, boardCellValue)->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    viewModel.onAction(
                                        UserAction.BoardTapped(cellNo)
                                    )

                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(visible = viewModel.boardItems[cellNo] != BoardCellValue.NONE,
                                enter = scaleIn(tween(200))
                                ) {
                                if(boardCellValue == BoardCellValue.CIRCLE){
                                    Circle()
                                }else if(boardCellValue == BoardCellValue.CROSS){
                                    Cross()
                                }
                            }



                        }
                    }

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center


            ) {
                AnimatedVisibility(visible = state.hasWon,
                    enter = fadeIn(tween(500)))
                {
                    DrawVictoryLine(state = state)
                }
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.hintText,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
            Button(
                onClick = {
                          viewModel.onAction(
                              UserAction.PlayAgainButtonClicked
                          )
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueCustom,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 16.sp

                )


            }
        }

    }
}

@Composable
fun Cross() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)

    ) {
        drawLine(
            color = GreenishYellow,
            strokeWidth = 20f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)

        )
        drawLine(
            color = GreenishYellow,
            strokeWidth = 20f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)

        )
    }
}

@Composable
fun Circle() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawCircle(
            color = Aqua,
            style = Stroke(width = 20f)
        )
    }
}

@Composable
fun WinHorizontalLine1() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height * 5 / 12),
            end = Offset(x = size.width, y = size.height * 1 / 6)
        )
    }
}

@Composable
fun WinHorizontalLine2() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height * 3 / 6),
            end = Offset(x = size.width, y = size.height * 3 / 6)
        )
    }
}

@Composable
fun WinHorizontalLine3() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height * 13 / 18),
            end = Offset(x = size.width, y = size.height * 5 / 6)
        )
    }
}

@Composable
fun WinVerticalLine1() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width * 1 / 6, y = 0f),
            end = Offset(x = size.width * 1 / 6, y = size.height)
        )
    }
}

@Composable
fun WinVerticalLine2() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width * 3 / 6, y = 0f),
            end = Offset(x = size.width * 3 / 6, y = size.height)
        )
    }
}

@Composable
fun WinVerticalLine3() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width * 5 / 6, y = 0f),
            end = Offset(x = size.width * 5 / 6, y = size.height)
        )
    }
}


@Composable
fun WinDiagonalLine1() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
    }
}


@Composable
fun WinDiagonalLine2() {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}

@Composable
fun DrawVictoryLine(
    state: GameState
){
    when(state.victoryType){
        VictoryType.HORIZONTAL1-> WinHorizontalLine1()
        VictoryType.HORIZONTAL2-> WinHorizontalLine2()
        VictoryType.HORIZONTAL3-> WinHorizontalLine3()
        VictoryType.VERTICAL1-> WinVerticalLine1()
        VictoryType.VERTICAL2-> WinVerticalLine2()
        VictoryType.VERTICAL3-> WinVerticalLine3()
        VictoryType.DIAGONAL1-> WinDiagonalLine1()
        VictoryType.DIAGONAL2-> WinDiagonalLine2()
        VictoryType.NONE-> {}
    }
}


@Preview
@Composable
fun Prev() {
    GameScreen(viewModel = GameViewModel())


}