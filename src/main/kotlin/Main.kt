import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import drawing.FractalPainter
import drawing.SelectionRect
import drawing.colors.colors
import drawing.convertation.Converter
import drawing.convertation.Plane
import math.fractals.Fractal
import math.fractals.funcs
import java.awt.Dimension




@Composable
@Preview
fun App() {

    val fp = remember { mutableStateOf(FractalPainter(Fractal, colors["color1"]!!))}
    

    fp.value.plane = Plane(-2.0, 1.0, -1.0, 1.0, 0f, 0f)

    fp.value.plane?.let {
        fp.value.xMin = it.xMin
        fp.value.xMax = it.xMax
        fp.value.yMax = it.yMax
        fp.value.yMin = it.yMin
    }

    MaterialTheme {
        menu(fp)
    }
}


@Composable
fun menu(fp:  MutableState<FractalPainter>){

    val fractalColor = remember { mutableStateOf("color1")  }
    val fractalFunction = remember { mutableStateOf("Mandelbrot") }

    var expandedMenu by remember { mutableStateOf(false) }
    var expandedMenuColor by remember { mutableStateOf(false) }
    var expandedFractalFunctions by remember { mutableStateOf(false) }
    val checkedState = remember { mutableStateOf(true) }
    val juliaButtonState = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth().background(Color.Blue)) {

                Box {
                    Button(onClick = {expandedMenuColor = true}){
                        Text("Цвета")
                    }
                    DropdownMenu(expanded = expandedMenuColor, onDismissRequest = {expandedMenuColor = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fp.value.colorFunc != colors["color1"]){
                                    fractalColor.value = "color1"
                                }
                            }
                        ) {
                            Text("1 цвет", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fp.value.colorFunc != colors["color2"]){
                                    fractalColor.value = "color2"
                                }
                            }
                        ) {
                            Text("2 цвет", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fp.value.colorFunc != colors["color3"]){
                                    fractalColor.value = "color3"
                                }
                            }
                        ) {
                            Text("3 цвет", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                }

                Box {
                    Button(onClick = {expandedFractalFunctions = true}){
                        Text("Функции")
                    }
                    DropdownMenu(expanded = expandedFractalFunctions, onDismissRequest = {expandedFractalFunctions = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fractalFunction.value != "Mandelbrot") {
                                    fractalFunction.value = "Mandelbrot"
                                }
                            }
                        ) {
                            Text("1 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fractalFunction.value != "square") {
                                    fractalFunction.value = "square"
                                }
                            }
                        ) {
                            Text("2 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fractalFunction.value != "third_pow") {
                                    fractalFunction.value = "third_pow"
                                }
                            }
                        ) {
                            Text("3 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (fractalFunction.value != "multiply_and_plus") {
                                    fractalFunction.value = "multiply_and_plus"
                                }
                            }
                        ) {
                            Text("4 функция", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                }


                Spacer(Modifier.weight(1f, true))

                Button(onClick = {TODO()}, enabled = juliaButtonState.value){
                    Text("Построить множество Жюлиа")
                }

                Spacer(Modifier.weight(0.5f, true))

                var i = 0                                                                             //i убрать. Сделал так, чтобы ошибка не вылетала
                IconButton(onClick = {println(fractalColor.value)}){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Вернуться на шаг назад")
                }

                Box {
                    IconButton(onClick = {expandedMenu = true}){
                        Icon(Icons.Filled.MoreVert, contentDescription = "Сохранение")
                    }
                    DropdownMenu(expanded = expandedMenu, onDismissRequest = {expandedMenu = false}){
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {TODO()}
                        ) {
                            Text("Сохранить", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {TODO()}
                        ) {
                            Text("Сохранить в формате", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {TODO()}
                        ) {
                            Text("Выгрузить фрактал", fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                        DropdownMenuItem(
                            modifier = Modifier.height(35.dp),
                            onClick = {checkedState.value = !checkedState.value}
                        ) {
                            Text("Динамическое изменение\nчисла итераций", fontSize = 11.sp)
                            Checkbox(checked = checkedState.value, onCheckedChange = {checkedState.value = it})
                        }
                    }
                }
            }
        }
    ) {
            DrawingPanel(fp, fractalColor, fractalFunction){size ->
                fp.value.width = size.width.toInt()
                fp.value.height = size.height.toInt()
                fp.value.refresh = true
            }
            SelectionPanel{
                fp.value.plane?.let{ plane ->
                    val xMin = Converter.xScr2Crt(it.topLeft.x, plane)
                    val xMax = Converter.xScr2Crt(it.topLeft.x+it.size.width, plane)
                    val yMax = Converter.yScr2Crt(it.topLeft.y, plane)
                    val yMin = Converter.yScr2Crt(it.topLeft.y+it.size.height, plane)

                    fp.value.xMin = xMin
                    fp.value.xMax = xMax
                    fp.value.yMin = yMin
                    fp.value.yMax = yMax
                    fp.value.refresh = true
                }
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionPanel(
    onSelected: (SelectionRect)->Unit
) {
    var rect by remember {mutableStateOf(SelectionRect(Offset.Zero))}
    Canvas(Modifier.fillMaxSize().padding(8.dp).pointerInput(Unit){
        detectDragGestures(
            onDragStart = {
                rect = SelectionRect(it)
            },
            onDrag = {
                rect.addPoint(it)
            },
            onDragEnd = {
                onSelected(rect)
                rect = SelectionRect(Offset.Zero)
            },
            matcher = PointerMatcher.Primary)
    }){
        drawRect(Color(0f, 1f, 1f, 0.3f), rect.topLeft, rect.size)
    }
}

@Composable
fun DrawingPanel(
    fp: MutableState<FractalPainter>,
    fpcolors:  MutableState<String>,
    fpfunctions:  MutableState<String>,
    onResize: (Size)-> Unit = {},
) {

    Canvas(Modifier.fillMaxSize().padding(8.dp)) {

        if(fp.value.colorFunc != colors[fpcolors.value]) {
            fp.value.colorFunc = colors[fpcolors.value]!!
            fp.value.refresh = true
            println(fpcolors.value)
        }
        if(fp.value.fractal.function != funcs[fpfunctions.value]) {

            fp.value.fractal.function = funcs[fpfunctions.value]!!
            fp.value.refresh = true
            println(fpfunctions.value)
        }
        if(fp.value.width != size.width.toInt() || fp.value.height != size.height.toInt() ) {

            onResize(size)
            println("Размер изменился")
        }

        fp.value.paint(this)
    }
}


fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "Множество Мандельброта"
    ) {
        this.window.minimumSize = Dimension(600, 400)
        App()
    }
}