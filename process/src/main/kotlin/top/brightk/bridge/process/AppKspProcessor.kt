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

class AppKspProcessor(
    environment: SymbolProcessorEnvironment
) : BaseProcessor(environment) {

    /** ===== 聚合缓存（跨 round） ===== */
    private val serviceNodes = mutableListOf<CsServiceNode>()
    private val cfNodes = mutableListOf<CfNode>()
    private val navNodes = mutableListOf<NavNode>()

    private var finalGenerated = false
    private var navGenerated = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        log("bridge working: ${resolver.getModuleName().asString()}")
        // 只做“收集”，不生成任何文件
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
        log("bridge working: ${resolver.getModuleName().asString()} ===>收集完毕")
        return emptyList()
    }

    /**
     * ⭐️ 所有 aggregating 输出，必须在 finish()
     */
    override fun finish() {
        super.finish()

        val isApplication = options["bridgeEntry"] == "true"
        if (isApplication && !finalGenerated) {

            // 保证输出稳定（顺序 + 去重）
            val stableServices = serviceNodes
                .distinctBy { it.key }

            val stableCfs = cfNodes
                .distinctBy { it.key }

            log("FinalTransfer generate: ${stableServices.size} services, ${stableCfs.size} cfs")

            CreateFinalTransfer(
                codeGenerator = codeGenerator,
                csService = stableServices,
                fcList = stableCfs
            ).create()

            finalGenerated = true
        }

        val shouldNavInject = options["navInject"] == "true"
        if (shouldNavInject && navNodes.isNotEmpty() && !navGenerated) {
            val stableNavs = navNodes
                .distinctBy { it.getKey()  }
            log("NavTransfer generate: ${stableNavs.size} nav nodes")

            CreateFinalNavTransfer(
                codeGenerator = codeGenerator,
                navNodes = navNodes
            ).create()
            navGenerated = true
        }
    }
}
