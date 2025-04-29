# bug-reproduce - Fabric8 CRD Generate

This branch demonstrates how the new CRD generator will fail when Java doesn't exist in the project (Kotlin only project)

## Reproduction Steps

1. Create new basic Gradle project
2. Create a basic Kotlin class (`src/main/kotlin/...`) that extends `CustomResource<*,*>` and is annotated with `@Group` and `@Version`
3. Follow the directions for generating CRDs [here](https://github.com/fabric8io/kubernetes-client/tree/main/crd-generator/gradle#crd-generator---usage-with-gradle-in-build-script)
4. Run `./gradlew clean generateCrds`
5. Observe failure `Execution failed for task ':generateCrds'. Not a class file, JAR file or directory: <system path>/bug-reproduce/build/classes/java/main`

## Workaround steps
1. Create a Java file under `src/main/java`
2. Run `./gradlew clean generateCrds`
3. Observe that the generate task works as expected, including generating a CRD from the Kotlin class extending `CustomResource<*,*>`

## Example in Action

See:
- [the workflow](https://github.com/james-bradshaw-coding/bug-reproduce/blob/fabric8crd/.github/workflows/build.yaml) 
- [the run](https://github.com/james-bradshaw-coding/bug-reproduce/actions/runs/14721681756/job/41316539308)
