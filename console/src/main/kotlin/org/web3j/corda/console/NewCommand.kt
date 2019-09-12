/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.corda.console

import org.apache.commons.io.FileUtils
import org.gradle.tooling.GradleConnector
import org.web3j.corda.codegen.CorDappGenerator
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 * Custom CLI interpreter to generate a new sample CordApp and web3j client.
 */
@Command(name = "new")
class NewCommand : CommonCommand() {

    @Option(
        names = ["-n", "--name"],
        description = ["CordApp name"],
        required = true
    )
    lateinit var corDappName: String

    override fun run() {
        // Generate CorDapp project
        CorDappGenerator(
            packageName = packageName,
            corDappName = corDappName,
            outputDir = outputDir
        ).generate()

        copyProjectResources()

        // Build CorDapp JAR for the client
        runGradleBuild(outputDir.toPath())

        // Generate the CorDapp client classes
        GenerateCommand().apply {
            cordaResource = GenerateCommand.CordaResource
            cordaResource.corDappsDir = File("${this@NewCommand.outputDir}/build/libs/")
//            cordaResource.openApiUrl = // FIXME - change to path of jar files
//                javaClass.classLoader.getResource("swagger.json")!!
            packageName = this@NewCommand.packageName
            outputDir = File("${this@NewCommand.outputDir}/clients")
            run()
        }

        copyResource("clients/build.gradle", outputDir)
    }

    private fun runGradleBuild(projectRoot: Path) {
        GradleConnector.newConnector().apply {
            useBuildDistribution()
            forProjectDirectory(projectRoot.toFile())
        }.connect().use {
            it.newBuild().apply {
                forTasks("jar")
                run()
            }
        }
    }

    private fun copyProjectResources() {
        copyResource("gradle.properties", outputDir)
        copyResource("build.gradle", outputDir)
        copyResource("settings.gradle", outputDir)
        copyResource("gradlew", outputDir)

        File("${outputDir.toURI().path}/gradlew").setExecutable(true)

        FileUtils.copyDirectory(
            File(javaClass.classLoader.getResource("gradle")?.toURI()!!.path),
            outputDir.resolve("gradle")
        )
        copyResource("README.md", outputDir)
    }

    private fun copyResource(name: String, outputDir: File) {
        Files.copy(
            javaClass.classLoader.getResource(name)?.openStream()!!,
            outputDir.resolve(name).toPath(),
            StandardCopyOption.REPLACE_EXISTING
        )
    }
}
