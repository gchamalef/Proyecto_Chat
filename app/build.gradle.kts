plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mychat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mychat"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Firebase
        // Importa la BoM para la plataforma Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
        // Importa la dependencia para Firebase Realtime Database
    implementation("com.google.firebase:firebase-database:20.3.1")

    // Agrega la dependencia para la biblioteca de autenticación de Firebase
        // Cuando usas la BoM, no especificas versiones en las dependencias de la biblioteca de Firebase
    implementation("com.google.firebase:firebase-auth")
        // FirebaseUI para Cloud Firestore
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")
        // Importacion de la dependencia para Firestore
    implementation("com.google.firebase:firebase-firestore:24.11.0")
        // Importacion de la dependencia para Firebase Storage
    implementation("com.google.firebase:firebase-storage:20.3.0")
        // Importando la dependencia para Firebase Cloud Messaging
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")

    //Agregados
        // Importa country code
    implementation("com.hbb20:ccp:2.5.0")
        // Importa imagepicker
    implementation("com.github.dhaval2404:imagepicker:2.1")
        // Importa glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
        // Importando la dependencia para OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
        // Imagen circular
    implementation("de.hdodenhof:circleimageview:3.1.0")

}