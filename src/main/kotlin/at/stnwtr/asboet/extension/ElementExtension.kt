package at.stnwtr.asboet.extension

import at.stnwtr.asboet.utility.TextVisitor
import org.jsoup.nodes.Element

fun Element.textList(): List<String> {
    val visitor = TextVisitor()

    traverse(visitor)

    return visitor.textList()
}
