import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}


dependencies {
    implementation(libs.configurate.core)
    implementation(libs.configurate.yaml)

    implementation(project(":winter-core"))
    implementation(project(":winter-configuration"))

    annotationProcessor(project(":winter-configuration"))
}

tasks.withType<ShadowJar> {

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
