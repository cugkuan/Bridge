package top.brightk.bridge.process
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import top.brightk.bridge.annotation.CsUrl
import top.brightk.bridge.annotation.FcUrl
import top.brightk.bridge.annotation.KspBridgeFc
import top.brightk.bridge.annotation.KspBridgeService
import top.brightk.bridge.process.ksp.CsServiceVisitor
import top.brightk.bridge.process.ksp.create.CreateCsTransfer
import top.brightk.bridge.process.ksp.create.CreateFcTransfer
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
            // 处理函数
            val fcList = ArrayList<FcNode>()
            val fcAnnotated = resolver.getSymbolsWithAnnotation(FcUrl::class.java.name)
                .filterIsInstance<KSFunctionDeclaration>()
            fcAnnotated.forEach { function ->
                if (function.annotations.any{it.shortName.asString() == "Composable"}){
                    log("Fc",function.simpleName.asString())
                    if (function.parameters.size > 1){
                        error("最多一个参数，且参数的类型为 FcRequest")
                        throw IllegalArgumentException(" @FcUrl 方法最多一个参数")
                    }
                    if (function.parameters.isNotEmpty()) {
                        val parameter = function.parameters.first()
                        val parameterType = parameter.type.resolve().declaration.qualifiedName
                        if (parameterType?.asString() == "top.brightk.bridge.core.FcRequest"){
                            val fcName = function.qualifiedName?.asString()
                            val fcUrl =  function.getAnnotationsByType(FcUrl::class).first().uri
                            log("Fc",function.qualifiedName?.asString().orEmpty())
                            fcList.add(FcNode(fcName!!,fcUrl.toKey(),true))
                        }else{
                            error("参数类型只能是FcRequest")
                            throw IllegalArgumentException("@FcUrl 修饰的方法，参数类型只能是 FcRequest")
                        }
                    }else{
                        // 没有参数
                        val fcName = function.qualifiedName?.asString()
                        val fcUrl =  function.getAnnotationsByType(FcUrl::class).first().uri
                        log("Fc",function.qualifiedName?.asString().orEmpty())
                        fcList.add(FcNode(fcName!!,fcUrl.toKey(),false))
                    }

                }else{
                    error("${function.simpleName.asString()}没有被@Composable修饰")
                }
            }
            if (fcList.isNotEmpty()){
                CreateFcTransfer(codeGenerator,fcList).create()
            }
            isScan = false
            return ArrayList<KSAnnotated>().apply {
                addAll(ksAnnotated.toList())
                addAll(fcAnnotated.toList())
            }
        }
        val application = options["application"]
        if (application == "true" && !isFinish) {
            val csFinalServices = ArrayList<CsServiceNode>()
            val fcList = ArrayList<FcNode>()
            resolver.getDeclarationsFromPackage(CS_TRANSFER_PACKET).forEach { declaration ->
                declaration.getAnnotationsByType(KspBridgeService::class).forEach { node ->
                    val json = node.json
                    csFinalServices.addAll(json.getServiceNodes())
                }
                declaration.getAnnotationsByType(KspBridgeFc::class).forEach { node ->
                    val json = node.json
                    fcList.addAll(json.getFcNodes())
                }
            }
            log("一共有${csFinalServices.size}个CsService ${fcList.size}个FcUrl方法")
            CreateFinalTransfer(codeGenerator, csFinalServices,fcList)
                .create()
            isFinish = true
        }

        return emptyList()
    }
    override fun finish() {
        super.finish()
    }

}