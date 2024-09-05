plugins {
    id("java")
}

group = "net.savagedev"
version = "1.0.0-SNAPSHOT"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://maven.citizensnpcs.co/repo")

    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")

    compileOnly("net.citizensnpcs:citizensapi:2.0.32-SNAPSHOT")
    compileOnly("org.mcmonkey:sentinel:2.9.1-SNAPSHOT")
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(sourceSets.main.get().resources.srcDirs) {
            expand(Pair("version", project.version))
                .include("plugin.yml")
        }
    }
}
