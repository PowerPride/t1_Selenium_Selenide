plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.14.4")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.47.0")
    testImplementation("com.codeborne:selenide:7.17.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "failed", "skipped")
    }
}
