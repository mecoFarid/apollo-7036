plugins {
  kotlin("jvm") version "2.4.0"
  id("com.apollographql.apollo") version "5.2.0"
}


dependencies {
  implementation("com.apollographql.apollo:apollo-runtime:5.2.0")
  testImplementation("com.apollographql.mockserver:apollo-mockserver:0.3.1")
  testImplementation("com.apollographql.apollo:apollo-testing-support:5.2.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
  testImplementation("junit:junit:4.13.2")
}

apollo {
  apollo {
    service("service") {
      srcDir("src/main/graphql")
      packageName.set("com.example")
    }
  }
}
