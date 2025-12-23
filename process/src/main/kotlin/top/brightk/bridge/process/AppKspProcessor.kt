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

const val SERVICE_QUALIFIER = "top.brightk.bridge.annotation.KspBridgeService"
const val CF_QUALIFIER = "top.brightk.bridge.annotation.KspBridgeCf"
const val NAV_QUALIFIER = "top.brightk.bridge.annotation.KspBridgeNav"
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
        val isApplication = options["bridgeEntry"] == "true"
        if (!isApplication){
            return emptyList()
        }
        resolver.getDeclarationsFromPackage(CS_TRANSFER_PACKET).forEach { declaration ->
            declaration.annotations.forEach { annotation ->
                // 解析注解的全限定名
                val qualifiedName = annotation.annotationType.resolve().declaration.qualifiedName?.asString()
                // 4. 提取名为 "json" 的参数值（关键：直接从符号参数列表中取值）
                val jsonValue = annotation.arguments
                    .find { it.name?.asString() == "json" }
                    ?.value as? String
                if (!jsonValue.isNullOrBlank()) {
                    log("bride work : $jsonValue ===>$qualifiedName")
                    try {
                        // 5. 根据当前处理的注解名，分发解析逻辑
                        when (qualifiedName) {
                            SERVICE_QUALIFIER -> serviceNodes.addAll(jsonValue.getServiceNodes())
                            CF_QUALIFIER -> cfNodes.addAll(jsonValue.getFcNodes())
                            NAV_QUALIFIER -> navNodes.addAll(jsonValue.getNavNode())
                        }
                    } catch (e: Exception) {
                        error("解析 $jsonValue 失败")
                    }
                }
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
