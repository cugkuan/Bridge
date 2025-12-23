package top.brightk.bridge.process

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import top.brightk.bridge.annotation.KspBridgeCf
import top.brightk.bridge.annotation.KspBridgeNav
import top.brightk.bridge.annotation.KspBridgeService
import top.brightk.bridge.process.ksp.create.CreateFinalNavTransfer
import top.brightk.bridge.process.ksp.create.CreateFinalTransfer

class AppKspProcessor(environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {
    private var isFinish = false
    private var navInject = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        log("bridge正在工作：${resolver.getModuleName().asString()}")

        if (isFinish && navInject) {
            return emptyList()
        }
        val serviceNodes = mutableListOf<CsServiceNode>()
        val cfNodes = mutableListOf<CfNode>()
        val navNodes = mutableListOf<NavNode>()
        // Only iterate once.
        resolver.getDeclarationsFromPackage(CS_TRANSFER_PACKET).forEach { declaration ->
            declaration.getAnnotationsByType(KspBridgeService::class).forEach { annotation ->
                serviceNodes.addAll(annotation.json.getServiceNodes())
            }
            declaration.getAnnotationsByType(KspBridgeCf::class).forEach { annotation ->
                cfNodes.addAll(annotation.json.getFcNodes())
            }
            declaration.getAnnotationsByType(KspBridgeNav::class).forEach { annotation ->
                navNodes.addAll(annotation.json.getNavNode())
            }
        }
        val isApplication = options["bridgeEntry"] == "true"
        if (isApplication && !isFinish) {
            log("一共有${serviceNodes.size}个CsService ${cfNodes.size}个FcUrl方法")
            CreateFinalTransfer(codeGenerator, serviceNodes, cfNodes).create()
            isFinish = true
        }
        val shouldNavInject = options["navInject"] == "true"
        if (shouldNavInject && !navInject && navNodes.isNotEmpty()) {
            CreateFinalNavTransfer(codeGenerator, navNodes).create()
            log("Nav 注入=========>")
            log("一共有${navNodes.size}个NavNode")
            navInject = true
        }
        return emptyList()

    }

    override fun finish() {
        super.finish()
    }

}