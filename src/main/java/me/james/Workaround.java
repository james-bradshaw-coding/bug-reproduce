package me.james;

/**
 * This file is only necessary to allow the CRD generator to work properly.
 * </br>
 * Without a throwaway Java class, the CRD generator fails with:
 * </br>
 * <code>
 *   Execution failed for task ':generateCRDs'. </br>
 *   Not a class file, JAR file or directory: [path]/bug-reproduce/build/classes/java/main
 * </code>
 * </br>
 * To reproduce, simply delete this file and run the `generateCrds` task.
 * </br>
 * There doesn't even need to be a Java class, simply something that generates `build/classes/java/main`
 */
