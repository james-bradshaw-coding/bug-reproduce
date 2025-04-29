import io.fabric8.crd.generator.collector.CustomResourceCollector
import io.fabric8.crdv2.generator.CRDGenerationInfo
import io.fabric8.crdv2.generator.CRDGenerator
import org.gradle.api.internal.tasks.JvmConstants
import java.nio.file.Files

buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath("io.fabric8:crd-generator-api-v2:7.1.0")
    classpath("io.fabric8:crd-generator-collector:7.1.0")
  }
}


plugins {
  kotlin("jvm").version("2.1.20")
}

dependencies {
  implementation("io.fabric8:crd-generator-api-v2:7.1.0")
  compileOnly("io.fabric8:kubernetes-client-api:7.1.0")
}

tasks {
  // https://github.com/fabric8io/kubernetes-client/tree/main/crd-generator/gradle#crd-generator---usage-with-gradle-in-build-script
  val generateCRDs = register("generateCrds") {
    description = "Generate CRDs"
    group = "crd"
    // this line is an addition from the guide linked above
    dependsOn(JvmConstants.CLASSES_TASK_NAME)

    val sourceSet = project.sourceSets["main"]

    val compileClasspathElements = sourceSet.compileClasspath.map { e -> e.absolutePath }

    val outputClassesDirs = sourceSet.output.classesDirs
    val outputClasspathElements = outputClassesDirs.map { d -> d.absolutePath }

    val classpathElements = listOf(outputClasspathElements, compileClasspathElements).flatten()
    val filesToScan = listOf(outputClassesDirs).flatten()
    val outputDir = sourceSet.output.resourcesDir

    doLast {
      Files.createDirectories(outputDir!!.toPath())

      val collector = CustomResourceCollector()
        .withParentClassLoader(Thread.currentThread().contextClassLoader)
        .withClasspathElements(classpathElements)
        .withFilesToScan(filesToScan)

      val crdGenerator = CRDGenerator()
        .customResourceClasses(collector.findCustomResourceClasses())
        .inOutputDir(outputDir)

      val crdGenerationInfo: CRDGenerationInfo = crdGenerator.detailedGenerate()

      crdGenerationInfo.crdDetailsPerNameAndVersion.forEach { (crdName, versionToInfo) ->
        println("Generated CRD $crdName:")
        versionToInfo.forEach { (version, info) -> println(" $version -> ${info.filePath}") }
      }
    }
  }

  named(JvmConstants.CLASSES_TASK_NAME) {
    finalizedBy(generateCRDs)
  }
}
