plugins {
    id("com.android.application") version "4.1.2" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
