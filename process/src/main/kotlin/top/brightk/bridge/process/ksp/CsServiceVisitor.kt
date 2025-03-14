package top.brightk.bridge.process.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import top.brightk.bridge.process.CsServiceNode
import top.brightk.bridge.process.KspProcessor
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.process.toKey

class CsServiceVisitor(
    val process: KspProcessor,
    private val csServices: MutableList<CsServiceNode>
) : KSVisitorVoid() {

    @OptIn(KspExperimental::class)
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val name = classDeclaration.qualifiedName!!.asString()
        classDeclaration.getAnnotationsByType(CsUrl::class).forEach { csUrl ->
            val url = csUrl.uri
            val type = csUrl.type
            process.log("CsService:$name => $url  $type")
            csServices.add(CsServiceNode(name , url.toKey(),type))
        }

    }
}