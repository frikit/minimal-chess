import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.3.61"
}

group = "org.gihub.home"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    build {
        dependsOn(fatJar)
    }

    test {
        useJUnitPlatform()
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Gradle Fat Jar"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "org.github.home.chess.Main"
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks.jar.get() as CopySpec)
}
