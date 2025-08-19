package resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.defey.solitairewell.data.resources.Res
import com.defey.solitairewell.data.resources.diamond
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

//object CardResources {

////    fun hearts(): Painter = painterResource("svg/hearts.svg")
    @Composable
    fun diamonds(): Painter = painterResource(Res.drawable.diamond)
//    @Composable
//    fun clubs(): Painter = painterResource(DrawableResource("svg/clubs.svg"))
////    fun spades(): Painter = painterResource("svg/spades.svg")
////
////    // Ранги
////    fun jack(): Painter = painterResource("files/jack.png")
////    fun queen(): Painter = painterResource("files/queen.png")
////    @Composable
////    fun king(): Painter = painterResource("files/king.png")
////    fun ace(): Painter = painterResource("files/ace.png") // Если нужно
////
////    // Рубашка
////    fun back(): Painter = painterResource("files/card_back.png")
//}