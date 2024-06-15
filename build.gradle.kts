// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
true // Needed to make the Suppress annotation work for the plugins block