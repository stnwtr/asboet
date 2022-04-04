package at.stnwtr.asboet.utility

import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.jsoup.select.NodeVisitor

class TextVisitor : NodeVisitor {
    private val textList = mutableListOf<String>()

    override fun head(node: Node, depth: Int) {
        if (node is TextNode && node.text().isNotBlank()) {
            textList.add(node.text())
        }
    }

    override fun tail(node: Node, depth: Int) {
    }

    fun textList() = textList.toList()
}
