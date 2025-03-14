package top.brightk.bridge.process
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.annotation.KspBridgeService
import top.brightk.bridge.process.ksp.CsServiceVisitor
import top.brightk.bridge.process.ksp.create.CreateCsTransfer
import top.brightk.bridge.process.ksp.create.CreateFinalTransfer


const val CS_TRANSFER_FINIAL = "com.brightk.bridge"
const val CS_TRANSFER_FINIAL_CLASS = "CsServiceInit"
const val CS_TRANSFER_PACKET = "com.brightk.bridge.transfer"


/**
 * 针对非增量更新设计
 */
class KspProcessor(environment: SymbolProcessorEnvironment) :
    BaseProcessor(environment) {
    private var isScan: Boolean = true
    private var isFinish = false
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        log("bridge正在工作：${resolver.getModuleName().asString()}")
        if (isScan){
            val csServices = ArrayList<CsServiceNode>()
            val csServiceVisitor = CsServiceVisitor(this, csServices)
            val ksAnnotated = resolver.getSymbolsWithAnnotation(CsUrl::class.java.name)
            log("CsService: ${CsUrl::class.java.name}${ksAnnotated.toList().size}")
            ksAnnotated.forEach {
                it.accept(csServiceVisitor, Unit)
            }
            if (csServices.isNotEmpty()) {
                CreateCsTransfer(codeGenerator, csServices)
                    .create()
            }
            isScan = false
            return ArrayList<KSAnnotated>().apply {
                addAll(ksAnnotated.toList())
            }
        }
        val application = options["application"]
        if (application == "true" && !isFinish) {
            val csFinalServices = ArrayList<CsServiceNode>()
            resolver.getDeclarationsFromPackage(CS_TRANSFER_PACKET).forEach { declaration ->
                declaration.getAnnotationsByType(KspBridgeService::class).forEach { node ->
                    val json = node.json
                    csFinalServices.addAll(json.getServiceNodes())
                }
            }
            log("一共有${csFinalServices.size}个CsService ")
            CreateFinalTransfer(codeGenerator, csFinalServices)
                .create()
            isFinish = true
        }

        return emptyList()
    }

    override fun finish() {
        super.finish()
    }
}