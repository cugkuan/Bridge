package top.brightk.bridge.process

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.annotation.CfUrl
import top.brightk.bridge.annotation.Init
import top.brightk.bridge.annotation.KspBridgeCf
import top.brightk.bridge.annotation.KspBridgeNav
import top.brightk.bridge.annotation.KspBridgeService
import top.brightk.bridge.process.ksp.CsServiceVisitor
import top.brightk.bridge.process.ksp.create.CreateCsTransfer
import top.brightk.bridge.process.ksp.create.CreateFcTransfer
import top.brightk.bridge.process.ksp.create.CreateFinalNavTransfer
import top.brightk.bridge.process.ksp.create.CreateFinalTransfer
import top.brightk.bridge.process.ksp.create.CreateInitTransfer
import top.brightk.bridge.process.ksp.create.CreateInjectTransfer
import top.brightk.bridge.process.ksp.create.CreateNavTransfer


const val CS_TRANSFER_FINIAL = "com.brightk.bridge"
const val CS_TRANSFER_FINIAL_CLASS = "CsServiceInit"
const val CS_TRANSFER_FINIAL_NAV_CLASS ="NavBridgeInit"
const val CS_TRANSFER_PACKET = "com.brightk.bridge.transfer"

const val NAV_INJECT = "top.brightk.bridge.annotation.NavGraphInject"
const val NAV_INJECT_BRIDGE = "top.brightk.bridge.annotation.KspBridgeNavInject"


const val NAV_URL = "top.brightk.bridge.annotation.NavGraph"
const val NAV_URL_BRIDGE = "top.brightk.bridge.annotation.KspBridgeNav"


/**
 * 针对非增量更新设计
 */
class KspProcessor(environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {
    private var isScan: Boolean = true
    private var isFinish = false
    private var navInject = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        log("bridge正在工作：${resolver.getModuleName().asString()}")
        val application = options["application"]

        val processAnnotated = ArrayList<KSAnnotated>()
        if (isScan) {
            // 处理 Init，因为Ksp 的工作原理决定的
            if (application == "true") {
                resolver.getSymbolsWithAnnotation(Init::class.java.name)
                    .also {
                        processAnnotated.addAll(it.toList())
                    }
                    .filterIsInstance<KSFunctionDeclaration>()
                    .firstOrNull()?.let { function ->
                        log("BridgeInit: ${function.qualifiedName?.asString()}")
                        CreateInitTransfer(
                            codeGenerator,
                            FunNode(function.qualifiedName!!.asString())
                        )
                            .create()
                    }
            }
            // 处理注入
            resolver.getSymbolsWithAnnotation(NAV_INJECT)
                .also { processAnnotated.addAll(it.toList()) }
                .filterIsInstance<KSFunctionDeclaration>()
                .firstOrNull()?.let { function ->
                    log("BridgeNavInject: ${function.qualifiedName?.asString()}")
                    CreateInjectTransfer(
                        codeGenerator,
                        FunNode(function.qualifiedName!!.asString())
                    )
                        .create()
                }
            // 处理 NavHost 的页面注入
            resolver.getSymbolsWithAnnotation(NAV_URL)
                .also { processAnnotated.addAll(it.toList()) }
                .filterIsInstance<KSFunctionDeclaration>()
                .let {
                    return@let navProcess(it.toList()) { msg ->
                        log("Nav", msg)
                    }
                }.takeIf { it.isNotEmpty() }
                ?.let {
                    CreateNavTransfer(codeGenerator, it).create()
                }


            // 处理Cs服务
            val csServices = ArrayList<CsServiceNode>()
            val csServiceVisitor = CsServiceVisitor(this, csServices)
            val ksAnnotated = resolver.getSymbolsWithAnnotation(CsUrl::class.java.name)
                .also {
                    processAnnotated.addAll(it.toList())
                }
            log("CsService: ${CsUrl::class.java.name}${ksAnnotated.toList().size}")
            ksAnnotated.forEach {
                it.accept(csServiceVisitor, Unit)
            }
            if (csServices.isNotEmpty()) {
                CreateCsTransfer(codeGenerator, csServices)
                    .create()
            }
            // 处理函数
            val fcList = ArrayList<CfNode>()
            val fcAnnotated = resolver.getSymbolsWithAnnotation(CfUrl::class.java.name)
                .also {
                    processAnnotated.addAll(it.toList())
                }
                .filterIsInstance<KSFunctionDeclaration>()
            fcAnnotated.forEach { function ->
                if (function.annotations.any { it.shortName.asString() == "Composable" }) {
                    log("Cf", function.simpleName.asString())
                    if (function.parameters.size > 1) {
                        error("最多一个参数，且参数的类型为 CfParams")
                        throw IllegalArgumentException(" @FcUrl 方法最多一个参数")
                    }
                    if (function.parameters.isNotEmpty()) {
                        val parameter = function.parameters.first()
                        val parameterType = parameter.type.resolve().declaration.qualifiedName
                        if (parameterType?.asString() == "top.brightk.bridge.core.CfParams") {
                            val fcName = function.qualifiedName?.asString()
                            val cfUrl = function.getAnnotationsByType(CfUrl::class).first().uri
                            log("Cf", function.qualifiedName?.asString().orEmpty())
                            fcList.add(CfNode(fcName!!, cfUrl.toKey(), true))
                        } else {
                            error("参数类型只能是CfParams")
                            throw IllegalArgumentException("@CfUrl 修饰的方法，参数类型只能是 CfParams")
                        }
                    } else {
                        // 没有参数
                        val fcName = function.qualifiedName?.asString()
                        val cfUrl = function.getAnnotationsByType(CfUrl::class).first().uri
                        log("Fc", function.qualifiedName?.asString().orEmpty())
                        fcList.add(CfNode(fcName!!, cfUrl.toKey(), false))
                    }

                } else {
                    error("${function.simpleName.asString()}没有被@Composable修饰")
                }
            }
            if (fcList.isNotEmpty()) {
                CreateFcTransfer(codeGenerator, fcList).create()
            }

            isScan = false
            return processAnnotated
        }

        if (application == "true" && !isFinish) {
            val csFinalServices = ArrayList<CsServiceNode>()
            val cfList = ArrayList<CfNode>()
            val navList = ArrayList<NavNode>()
            resolver.getDeclarationsFromPackage(CS_TRANSFER_PACKET).forEach { declaration ->
                declaration.getAnnotationsByType(KspBridgeService::class).forEach { node ->
                    val json = node.json
                    csFinalServices.addAll(json.getServiceNodes())
                }
                declaration.getAnnotationsByType(KspBridgeCf::class).forEach { node ->
                    val json = node.json
                    cfList.addAll(json.getFcNodes())
                }

                declaration.getAnnotationsByType(KspBridgeNav::class).forEach { node ->
                    val json = node.json
                    navList.addAll(json.getNavNode())
                }
            }
            log("一共有${csFinalServices.size}个CsService ${cfList.size}个FcUrl方法")
            CreateFinalTransfer(codeGenerator, csFinalServices, cfList)
                .create()

            isFinish = true
        }
        if (options["navInject"] == "true" && !navInject){
            val navList = ArrayList<NavNode>()
            resolver.getDeclarationsFromPackage(CS_TRANSFER_PACKET).forEach { declaration ->
                declaration.getAnnotationsByType(KspBridgeNav::class).forEach { node ->
                    val json = node.json
                    navList.addAll(json.getNavNode())
                }
            }
            if (navList.isNotEmpty()) {
                CreateFinalNavTransfer(codeGenerator, navList).create()
            }
            navInject = true
        }
        return emptyList()
    }

    override fun finish() {
        super.finish()
    }

}