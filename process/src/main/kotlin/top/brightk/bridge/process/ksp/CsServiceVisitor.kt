package top.brightk.bridge.process.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import top.brightk.bridge.process.CsServiceNode
import top.brightk.bridge.process.KspProcessor
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.annotation.Type
import top.brightk.bridge.process.toKey
import kotlin.math.log

class CsServiceVisitor(
    val process: KspProcessor,
    private val csServices: MutableList<CsServiceNode>
) : KSVisitorVoid() {

//    @OptIn(KspExperimental::class)
//    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
//        val name = classDeclaration.qualifiedName!!.asString()
//        classDeclaration.getAnnotationsByType(CsUrl::class).forEach { csUrl ->
//            val url = csUrl.uri
//            val type = csUrl.type
//            process.log("CsService:$name => $url  $type")
//            csServices.add(CsServiceNode(name , url.toKey(),type))
//        }
//
//    }
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val name = classDeclaration.qualifiedName?.asString() ?: return
        val annotation = classDeclaration.annotations.firstOrNull {
             it.annotationType.resolve().declaration.qualifiedName?.asString() == "top.brightk.bridge.annotation.CsUrl"
        } ?: return
        var url = ""
        var type:Type = Type.DEFAULT
        annotation.arguments.forEach { arg ->
            when (arg.name?.asString()) {
                "uri" -> url = arg.value as? String ?: ""
                "type" -> {
                    val value = arg.value.toString()
                    process.log("CsService:$name => $url 类型： ~~~~~~~~~~~ $value")
                    type = when (value) {
                        "Type.NEW" -> {
                            Type.NEW
                        }
                        "Type.SINGLE" -> {
                            Type.SINGLE
                        }
                        else -> {
                            Type.DEFAULT
                        }
                    }
                }
            }
        }
        process.log("CsService:$name => $url   type:  $type")
        csServices.add(CsServiceNode(name, url.toKey(), type))
    }
}