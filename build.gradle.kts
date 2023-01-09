plugins {
    id("java")
}

allprojects {
    apply(plugin = "java")

    group = "com.github.diegonighty.winter"

    repositories {
        mavenCentral()

        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        testImplementation(rootProject.libs.junit.api)
        testRuntimeOnly(rootProject.libs.junit.engine)

        implementation(rootProject.libs.guice)
        implementation(rootProject.libs.spigot)
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}
