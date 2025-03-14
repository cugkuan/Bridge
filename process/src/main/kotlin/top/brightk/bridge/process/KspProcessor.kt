package top.brightk.bridge.process
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated


const val CS_TRANSFER_FINIAL = "com.brightk.cs"
const val CS_TRANSFER_FINIAL_CLASS = "CsInit"
const val CS_TRANSFER_PACKET = "com.brightk.cs.transfer"
const val CS_TRANSFER_IMPORT_SERVICE = "com.brightk.cs.core.annotation.KspBridgeService"
const val CS_TRANSFER_IMPORT_INTERCEPTOR = "com.brightk.cs.core.annotation.KspBridgeInterceptor"

/**
 * 针对非增量更新设计
 */
class KspProcessor(environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {
    private var isScan: Boolean = true
    private var isFinish = false
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        log("我是测试ksp的：${resolver.getModuleName().asString()}")
        log("============dhjhjahjdhjahdahgdhagghagd>")
        if (isScan){
        }

        return emptyList()
    }

    override fun finish() {
        super.finish()
    }
}