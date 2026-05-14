# DEVARA-KAADU: SACRED GROVE SENTINEL
## Master Developer Guide, Implementation Handbook & Viva Preparation Manual

---

# 1. Project Overview

### What is Devara-Kaadu?
**Devara-Kaadu – Sacred Grove Sentinel** is an **offline-first Android application** built to promote the preservation, documentation, and exploration of Karnataka’s traditional sacred groves (known locally as *Devara Kaadu*, *Kaavu*, *Bana*, *Nagabana*, or *Kans*). 

Sacred groves are pristine forest patches protected by indigenous communities for centuries driven purely by cultural and religious beliefs. They act as critical biodiversity hotspots, natural water recharge zones, and genetic reservoirs for rare medicinal plants.

### Core Objectives
1. **Bridging Knowledge Systems:** Merges **Traditional Ecological Knowledge (TEK)**—such as oral myths, village deity histories, and customary rules—with **Modern Biodiversity Science** (botanical names, IUCN status, and ecological roles).
2. **100% Offline Availability:** Designed specifically to function deep inside dense forest canopies or remote rural regions where cellular networks and internet connectivity are entirely absent.
3. **Youth Engagement via Gamification:** Introduces **Guardian Mode**, a badge and points collection system that rewards users for actively visiting groves, scanning species, and reporting environmental threats.
4. **Community Conservation Monitoring:** Features an offline **Conservation Alert** reporting tool where users can document local ecological threats (logging, fires, poaching) directly into the device database.

### Core Technical Specifications
- **Programming Language:** Kotlin (v2.0+)
- **User Interface Framework:** Jetpack Compose (Material Design 3)
- **Architecture Pattern:** Model-View-ViewModel (MVVM) with Repository Layer
- **Local Persistence:** Room Database (SQLite wrapper) with compile-time verification via KSP
- **Routing & Routing State:** Navigation Compose with sealed classes
- **Reactive State Flow:** Kotlin Coroutines and `StateFlow` / `MutableStateFlow`
- **Location Services:** Google Play Services `FusedLocationProviderClient`
- **Data Serialization:** GSON library loading structured JSON assets

---

# 2. Architecture Explanation

The application follows the **Clean Architecture-inspired MVVM pattern**. This creates a strict separation of concerns, ensuring UI components only display state, ViewModels only handle presentation logic, Repositories orchestrate data, and DAOs interact directly with the underlying SQLite database.

```
[ Compose UI Screens ]  <-- Observes StateFlow --  [ ViewModels ]
         |                                               |
   User Actions                                    Calls Suspend/Flow
         v                                               v
[ Navigation Graph ]                              [ Repositories ]
                                                         |
                                                  Executes Queries
                                                         v
                                                [ Room DAOs & DB ]
```

### Layer Breakdown
1. **View Layer (Jetpack Compose):** 
   Consists of highly modular composable functions. Screens reactively observe state streams exposed by the ViewModels using `collectAsStateWithLifecycle()`. User interactions (button taps, text inputs) trigger lightweight event callbacks directed to the ViewModels.
2. **ViewModel Layer (`AndroidViewModel`):**
   Survives configuration changes (like screen rotations). Manages application logic and converts raw data from Repositories into clean, immutable UI states. Exposes read-only `StateFlow` variables to prevent UI from directly mutating data.
3. **Repository Layer:**
   Acts as the single source of truth for application data. Decouples the ViewModels from knowing whether data comes from local storage, preloaded assets, or runtime cache.
4. **Data Layer (Room Database & Assets):**
   Manages standard SQLite database creation, caching, and entity operations. Preloads initial application data seamlessly from local JSON files packaged inside the APK's `assets` directory.

[Insert Image: system_architecture_diagram.png]  
*Figure 1 — Complete Layered System Architecture Workflow*

---

# 3. Folder Structure Walkthrough

The codebase is organized cleanly by functional domain and architectural layers inside `app/src/main/java/com/example/devara_kaadu/`:

```
com.example.devara_kaadu/
│
├── data/
│   ├── local/             # Database initialization & DAOs
│   │   ├── AppDatabase.kt # Main Room database class with JSON preloading callback
│   │   ├── AlertDao.kt    # SQL operations for user-reported conservation threats
│   │   ├── GroveDao.kt    # SQL operations for sacred groves filtering & search
│   │   ├── SpeciesDao.kt  # SQL operations for native flora/fauna entities
│   │   └── UserProgressDao.kt # SQL operations for single-row gamification stats
│   │
│   ├── model/             # Data Entities (Tables) and Enum classes
│   │   ├── Alert.kt       # Table entity for conservation alerts
│   │   ├── Badge.kt       # Enum definitions for Guardian Mode unlockable badges
│   │   ├── Grove.kt       # Table entity holding comprehensive grove records
│   │   ├── Species.kt     # Table entity for trilingual species data
│   │   └── UserProgress.kt# Table entity tracking user XP, badges, and counters
│   │
│   └── repository/        # Repository implementations mapping DAOs to ViewModels
│       ├── AlertRepository.kt
│       ├── GroveRepository.kt
│       ├── SpeciesRepository.kt
│       └── UserProgressRepository.kt
│
├── navigation/            # App routing setup
│   ├── NavGraph.kt        # Primary Composable orchestrating Screen destinations
│   └── Screen.kt          # Sealed class defining strict type-safe routes & arguments
│
├── ui/
│   ├── components/        # Reusable custom UI widgets
│   │   ├── ChipRow.kt     # Horizontal scrollable tags for species associations
│   │   └── SectionCard.kt # Collapsible text accordion container
│   │
│   ├── screens/           # Individual full-screen Composables (Views)
│   │   ├── ConservationAlertScreen.kt
│   │   ├── EcoEducationScreen.kt
│   │   ├── GroveDetailScreen.kt
│   │   ├── GroveDirectoryScreen.kt
│   │   ├── GuardianModeScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── MythLegendScreen.kt
│   │   ├── NearbyGroveScreen.kt
│   │   ├── OnboardingScreen.kt
│   │   ├── SearchScreen.kt
│   │   ├── SettingsScreen.kt
│   │   ├── SpeciesScanScreen.kt
│   │   └── SplashScreen.kt
│   │
│   └── theme/             # Design styling tokens
│       ├── Color.kt       # Premium Forest Green, Earth Brown, Sacred Gold palette
│       ├── Shape.kt       # Rounded corner configurations
│       ├── Theme.kt       # MaterialTheme initialization and Status Bar setup
│       └── Typography.kt  # Custom hierarchical font styling
│
├── utils/                 # General helper logic
│   ├── JsonLoader.kt      # GSON utility parsing local files into entity lists
│   ├── LocationHelper.kt  # GPS coroutine wrapper and Haversine math utility
│   └── NotificationHelper.kt # WorkManager integration for daily eco-reminders
│
├── DevaraKaaduApp.kt      # Custom Application entry point handling manual DI
└── MainActivity.kt        # Primary Single-Activity container mapping edge-to-edge UI
```

---

# 4. Application Workflow (End-to-End User Journey)

Here is exactly what happens internally during a complete end-to-end flow:

1. **App Launch:** User taps the app icon. `DevaraKaaduApp` initializes manual singletons (Database and Repositories). `MainActivity` loads the compose theme and triggers the `NavGraph` starting at `Screen.Splash`.
2. **First-Launch Seeding:** Room Database initializes. Since the SQLite file is empty, `AppDatabase.Callback.onCreate()` executes. It calls `JsonLoader` to read `groves_data.json` and `species_data.json` from the `assets/` folder, converting them into objects using GSON, and inserts them directly into the SQLite tables using asynchronous coroutines.
3. **Splash & Onboarding Transitions:** The Splash screen plays smooth scale animations and automatically redirects to `Screen.Onboarding` after 2.5 seconds. The user swipes through three educational pages and clicks "Begin Journey", landing on `Screen.Home`.
4. **Dashboard Interactions:** `HomeScreen` loads the primary dashboard grid. The user taps the **Grove Directory** card. Navigation routes to `Screen.GroveDirectory`.
5. **Reactive Database Reading:** `GroveDirectoryScreen` requests data from `GroveViewModel`. The ViewModel collects a reactive `Flow<List<Grove>>` from the `GroveRepository` (which executes `SELECT * FROM groves` via `GroveDao`). As the user taps filter chips (e.g., "Nagabana"), the ViewModel updates its `MutableStateFlow` criteria, instantly recompiling the query and updating the UI grid smoothly.
6. **Detailed Exploration:** User taps a specific grove card. The route updates to `grove_detail/3`. The `GroveDetailScreen` reads the ID argument from the `NavBackStackEntry`, calls `GroveViewModel.loadGroveById(3)`, and displays comprehensive offline tabs separating oral mythologies from pure environmental science.
7. **Action Triggering:** From the detail view, the user taps "Explore Native Species". The screen navigates to `Screen.SpeciesScan`. The user aims the camera viewfinder and taps "Scan Species". A 2.4-second simulated analysis progress bar animates. The ViewModel awards Guardian XP, updates the user progress table, and reveals a detailed species card.

[Insert Image: complete_app_workflow.png]  
*Figure 2 — End-to-End Application Lifecycle and User Interaction Flow*

---

# 5. MVVM Architecture Deep Dive

### Why MVVM was Chosen
MVVM resolves classic Android lifecycle pitfalls. In standard Activity-based designs, rotating the screen destroys and recreates the UI, causing ongoing background tasks or database calls to leak memory or crash. In MVVM:
- **ViewModels** persist across configuration changes automatically.
- **StateFlow** acts as a reliable state container that emits the latest available state immediately to new observers.
- **Unidirectional Data Flow (UDF)** ensures state flows downwards while UI events flow upwards, making multi-screen user interfaces highly predictable and easily testable.

### Practical Implementation Trace
Let's trace how data binding works without traditional XML files using Compose and Flow:

1. **Model Layer (`GroveRepository.kt`):** Exposes a clean data stream directly from Room.
   ```kotlin
   fun getAllGroves(): Flow<List<Grove>> = groveDao.getAllGroves()
   ```
2. **ViewModel Layer (`GroveViewModel.kt`):** Converts the cold database flow into a hot, state-holding `StateFlow` inside the `viewModelScope`.
   ```kotlin
   val groves: StateFlow<List<Grove>> = repository.getAllGroves()
       .stateIn(
           scope = viewModelScope,
           started = SharingStarted.WhileSubscribed(5000),
           initialValue = emptyList()
       )
   ```
3. **View Layer (`GroveDirectoryScreen.kt`):** Recomposes automatically whenever the underlying SQLite database updates.
   ```kotlin
   val allGroves by vm.groves.collectAsStateWithLifecycle()
   // UI automatically renders the updated list inside LazyVerticalGrid
   ```

[Insert Image: mvvm_data_binding_diagram.png]  
*Figure 3 — Practical MVVM Data Binding and Reactive Updates with Jetpack Compose*

---

# 6. Room Database Flow

The local persistence layer relies entirely on **Android Room**, ensuring compile-time SQL verification and absolute offline reliability.

### Entity Schemas
- **`groves` Table:** Stores unique IDs, geographic names, coordinates, structural area sizes, detailed legend descriptions, and raw JSON-formatted string lists representing native flora and bird species.
- **`species` Table:** Stores trilingual titles (English, Scientific, Kannada), broad biological types, official IUCN conservation categories, medicinal applications, and sacred folklore associations.
- **`alerts` Table:** Stores runtime user entries capturing environmental threats with status strings initialized to `"Pending"`.
- **`user_progress` Table:** Designed specifically as a **Single-Row Database Table** (`id = 1`). Tracks gamified points, streak counters, and a serialized GSON list containing earned badge strings.

### Database Initialization & Preloading
To ensure full offline capabilities directly upon app installation, the database utilizes a `RoomDatabase.Callback` combined with Kotlin Coroutines to seed initial data automatically:

```kotlin
@Database(entities = [Grove::class, Species::class, Alert::class, UserProgress::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groveDao(): GroveDao
    // ... other DAOs

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "devara_kaadu_db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Launch asynchronous coroutine directly inside GlobalScope to seed tables
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getDatabase(context)
                                val groves = JsonLoader.loadGroves(context)
                                val species = JsonLoader.loadSpecies(context)
                                database.groveDao().insertAll(groves)
                                database.speciesDao().insertAll(species)
                                // Initialize base user progress row
                                database.userProgressDao().insert(UserProgress(id = 1, totalPoints = 0))
                            }
                        }
                    })
                    .build().also { INSTANCE = it }
            }
        }
    }
}
```

[Insert Image: room_db_er_diagram.png]  
*Figure 4 — Room Database Entity Relationship Diagram (ERD)*

---

# 7. Navigation Flow

Routing is handled via **Navigation Compose**. Routes are managed using a strict sealed class design to avoid hardcoded string errors across multiple screen files.

### Route Implementation (`Screen.kt`)
```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object GroveDirectory : Screen("grove_directory")
    object GroveDetail : Screen("grove_detail/{groveId}") {
        fun createRoute(groveId: Int) = "grove_detail/$groveId"
    }
}
```

### Argument Passing & Back Stack Handling
When a user taps a grove card, the application navigates by formatting the integer ID directly into the string route:
```kotlin
navController.navigate(Screen.GroveDetail.createRoute(grove.id))
```
Inside `NavGraph.kt`, the destination defines the expected parameters using `navArgument`:
```kotlin
composable(
    route = Screen.GroveDetail.route,
    arguments = listOf(navArgument("groveId") { type = NavType.IntType })
) { backStackEntry ->
    val groveId = backStackEntry.arguments?.getInt("groveId") ?: 1
    GroveDetailScreen(
        groveId = groveId,
        onBack = { navController.popBackStack() }
    )
}
```
Using `popBackStack()` smoothly drops the current view from memory and restores the preceding list exactly where the user left off.

[Insert Image: app_navigation_flowchart.png]  
*Figure 5 — Composable Screen Navigation Routes and Back Stack Transitions*

---

# 8. State Management Workflow

State management relies on reactive streams utilizing Kotlin `StateFlow`. This prevents UI inconsistencies by guaranteeing single-source-of-truth updates.

### Unidirectional Flow Pattern
ViewModels encapsulate mutable states as private variables while exposing public read-only streams. For example, search queries and search results are synchronized natively:

```kotlin
class SearchViewModel(private val repository: GroveRepository) : AndroidViewModel(application) {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // Reactively executes search SQL queries automatically whenever query updates
    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<Grove>> = _query
        .debounce(300) // Avoids overwhelming DB on fast keystrokes
        .flatMapLatest { q ->
            if (q.isBlank()) flowOf(emptyList()) else repository.searchGroves(q)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setQuery(input: String) { _query.value = input }
}
```

In the UI, `collectAsStateWithLifecycle()` observes these states safely. It pauses flow collection automatically when the app goes into the background, saving device battery and computational overhead.

---

# 9. Offline-First Workflow & Local JSON Loading

The offline architecture guarantees that the app functions instantly without connecting to external cloud backends or online REST APIs.

### The File Parsing Workflow
1. Raw schema JSON files (`groves_data.json` containing 8 comprehensive test sites, and `species_data.json` containing 10 representative species) reside in `app/src/main/assets/`.
2. Upon first launch, `JsonLoader` accesses the stream via Android's `AssetManager`.
3. GSON reads the JSON array, mapping properties directly to corresponding Kotlin Data Class files.
4. Room batch inserts records directly into internal persistent tables. All future app views interact strictly with local tables.

```kotlin
object JsonLoader {
    fun loadGroves(context: Context): List<Grove> {
        val jsonString = context.assets.open("groves_data.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<Grove>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}
```

[Insert Image: offline_first_workflow.png]  
*Figure 6 — Fully Offline Database Initialization and Data Seeding Lifecycle*

---

# 10. GPS Workflow

The app integrates location detection directly to calculate proximity to nearby protected groves, functioning smoothly even without active data packages.

### Permission Handling
The application uses Google's `Accompanist Permissions` framework to abstract runtime location checks cleanly inside Compose views:
```kotlin
val permState = rememberMultiplePermissionsState(
    listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
)
if (!permState.allPermissionsGranted) {
    PermissionRequestCard(onRequest = { permState.launchMultiplePermissionRequest() })
}
```

### Proximity Logic & Haversine Distance
Once permission is granted, `LocationHelper` requests the user's geographic position using `FusedLocationProviderClient`. To keep code clean and asynchronous, raw Google Tasks are wrapped safely using `suspendCancellableCoroutine`.

The app calculates straight-line spatial distance using the standard **Haversine formula** implementation provided directly by Android:
```kotlin
fun distanceMeters(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
    val results = FloatArray(1)
    Location.distanceBetween(lat1, lng1, lat2, lng2, results)
    return results[0]
}
```
If a user is inside a 500-meter threshold, the app automatically triggers an ambient highlight and updates a specialized *"Nearby Status"* label.

### The Demonstration Fallback Logic
Because college vivas or project evaluations occur inside enclosed academic buildings where satellite GPS signals are obstructed, the application logic includes an explicit fallback path. If hardware location requests return null, the application automatically substitutes the geographic coordinates of **Bangalore City Center (12.9716° N, 77.5946° E)**, allowing students to demonstrate distance computations smoothly on screen.

[Insert Image: gps_workflow_diagram.png]  
*Figure 7 — Proximity Radar and Device GPS Location Fallback Calculation Flow*

---

# 11. Species Scan Workflow (Simulated AI Deep Dive)

During project evaluations, invigilators frequently scrutinize features utilizing Artificial Intelligence. Here is the exact internal breakdown of how the species identification scanner operates.

### Core Implementation Concept
> **CRITICAL VIVA EXPLANATION:**  
> *"This module implements a simulated AI-based species identification system utilizing localized application logic and predefined botanical datasets rather than heavy on-device neural networks."*

Integrating complete ML models (like TensorFlow Lite) directly into a prototype drastically inflates APK build weights and complicates offline execution speeds. To demonstrate the complete real-world user interface journey perfectly, the project implements a programmatic simulation pipeline.

### Step-by-Step Internal Execution Trace
1. **Idle Interface:** View loads camera permissions and displays a digital crosshair viewfinder overlay mapping scanning targets.
2. **Scan Activation:** User clicks the "Scan Species" action button.
3. **Simulation Coroutine Lifecycle:** `SpeciesViewModel.simulateScan()` launches directly inside the asynchronous ViewModel Scope:
   ```kotlin
   fun simulateScan() {
       viewModelScope.launch {
           _isScanning.value = true
           _scanProgress.value = 0f
           // Loop simulates model processing latency over 2.4 seconds
           for (i in 1..20) {
               delay(120)
               _scanProgress.value = i / 20f
           }
           // Randomly select target species from database list
           val speciesList = repository.getAllSpeciesSync()
           if (speciesList.isNotEmpty()) {
               _scanResult.value = speciesList.random()
               // Generate highly plausible simulated model confidence values
               _confidenceScore.value = Random.nextFloat() * 0.15f + 0.84f // Range: 84% - 99%
               // Award gamified progress points dynamically
               progressRepository.awardScanPoints()
           }
           _isScanning.value = false
       }
   }
   ```
4. **Dynamic UI Recomposition:** As `_scanProgress` increments, a Compose `LinearProgressIndicator` updates fluidly alongside an infinite animated translation line sweeping up and down the viewport.
5. **Result Display:** Upon completion, the scanning indicator hides, displaying the chosen entity alongside its generated confidence score, ecological role, and traditional religious significance.

[Insert Image: species_scan_workflow.png]  
*Figure 8 — Simulated AI Species Identification Pipeline and Asynchronous Progress Lifecycle*

---

# 12. Screen-by-Screen & Module-by-Module Explanation

### 1. Splash Screen (`SplashScreen.kt`)
- **Purpose:** Premium brand introduction mapping visual identity.
- **UI Components:** Implements custom radial gradients, three infinite animated scaling green boundary concentric circles, and local text styling featuring Kannada script (`ದೇವರಕಾಡು`).
- **Data/Navigation:** Purely visual presentation. Triggers a programmatic timer automatically redirecting to `Screen.Onboarding` via `popUpTo(inclusive = true)` to prevent users from navigating backward to the splash screen.

### 2. Onboarding Screen (`OnboardingScreen.kt`)
- **Purpose:** Guides users through core conservation concepts before initiating operations.
- **UI Components:** Implements a swipeable `HorizontalPager` container featuring custom dot width transformation transitions (`8.dp` expanding smoothly to `28.dp` on active indices).
- **Data/Navigation:** Static informational arrays. Tapping "Begin Journey" permanently clears introductory routes, loading the main dashboard.

### 3. Home Dashboard (`HomeScreen.kt`)
- **Purpose:** Serves as the central interface hub connecting all application layers.
- **UI Components:** Features a global action header banner, statistical summary indicators, an alternating random "Eco Fact of the Day" card, and an elegant 8-item feature grid supporting subtle dynamic press micro-animations.
- **Data/Navigation:** Acts as the main navigational dispatcher triggering individual sealed module destinations.

### 4. Grove Directory (`GroveDirectoryScreen.kt`)
- **Purpose:** Filterable, searchable, full-screen list cataloging preserved ecosystems.
- **UI Components:** Integrates search input bars, categorical tags (e.g., *Kaavu*, *Bana*, *Kans*), and dynamic layout toggles switching between compact row layouts and multi-column grid cards.
- **Data/Navigation:** Subscribes to `GroveViewModel`. Uses reactive flatMap operators to pass user query strings directly to `GroveDao.searchGroves()`. Tapping items appends target identifiers directly to routing strings leading to `Screen.GroveDetail`.

### 5. Grove Detail (`GroveDetailScreen.kt`)
- **Purpose:** Exhaustive educational view detailing distinct attributes of individual forest patches.
- **UI Components:** Header layout displaying geographic data and tree coverage counts. Integrates custom expandable `SectionCard` containers separating historical *Sacred Legends* from pure *Scientific & Ecological Facts*. Integrates horizontal scrolling tags rendering native plants via `ChipRow`.
- **Data/Navigation:** Queries specific row entries directly from SQLite tables based on received routing parameters. Includes action shortcuts facilitating direct navigation to Species Scanning or Alert Reporting modules.

### 6. Nearby Grove GPS (`NearbyGroveScreen.kt`)
- **Purpose:** Proximity tracker calculating distance parameters to surrounding sacred spaces.
- **UI Components:** Visual animated radar rings, permission request dialogs, and dynamic spatial sorting lists sorting available locations closest to farthest.
- **Data/Navigation:** Leverages Google location libraries directly. Invokes asynchronous database read tasks mapping geographic calculations onto loaded local entities.

### 7. Species Scan (`SpeciesScanScreen.kt`)
- **Purpose:** Simulated botanical identification tool mapping native flora species.
- **UI Components:** Custom animated crosshair bounds, visual scanning line sweep animations, progress completion indicators, and comprehensive entity detail breakdown layouts.
- **Data/Navigation:** Orchestrated via `SpeciesViewModel`. Directly awards experience points to persistent tables upon successful execution cycles.

### 8. Guardian Mode (`GuardianModeScreen.kt`)
- **Purpose:** Gamified engagement tracker motivating continuous environmental participation.
- **UI Components:** Renders user impact metrics (Groves Visited, Alerts Documented), visual dynamic points completion bars mapping targeted progression thresholds, and an indexed list rendering static custom badge containers displaying locked or unlocked icons.
- **Data/Navigation:** Directly observes internal single-row stats records maintained within the `user_progress` table.

### 9. Conservation Alerts (`ConservationAlertScreen.kt`)
- **Purpose:** Community reporting utility capturing localized environmental damage.
- **UI Components:** Form fields supporting category enum selections (Logging, Fire Risk, Encroachment), text descriptors, stateful submission loaders, and toggleable history cards displaying documented incidents.
- **Data/Navigation:** Interacts natively with `AlertViewModel`. Inserts row structures into local databases and automatically triggers corresponding point counter increments inside gamified stats rows.

### 10. Global Search (`SearchScreen.kt`)
- **Purpose:** Consolidated access utility finding targets across separate domain boundaries.
- **UI Components:** Debounced continuous typing input bars and dynamic multi-section scrollable list containers displaying matching grove sites and species descriptions simultaneously.
- **Data/Navigation:** Managed via `SearchViewModel`. Simultaneously executes SQL queries across multiple database tables.

### 11. Settings (`SettingsScreen.kt`)
- **Purpose:** Configuration viewer displaying internal environment info and open-source project attributions.
- **UI Components:** Informational meta layout headers, visual Switch toggle states, and text displays mapping the traditional Guardian's Conservation Oath.

---

# 13. Running the Project & APK Generation Guide

Follow these verified steps to run, compile, and install the project smoothly from scratch:

### 1. Setup Requirements
- **IDE:** Android Studio (Hedgehog 2023.1.1 or newer recommended)
- **Java Development Kit:** JDK 17 or JDK 21 (Available natively via Android Studio's bundled JetBrains Runtime)
- **Android SDK:** Compile SDK version 35, Min SDK version 26 (Android 8.0)

### 2. Opening & Syncing
1. Launch Android Studio. Select **File → Open** and choose the `DevaraKaadu` base project directory.
2. Allow Gradle to execute initialization passes. The environment automatically reads dependencies using the version catalog configuration located at `gradle/libs.versions.toml`.
3. Verify Gradle syncs completely without warnings.

### 3. Running Locally on an Emulator
1. Navigate to **Tools → Device Manager** on the right sidebar.
2. Click **Create Device** (+ icon). Select a modern hardware profile (e.g., **Pixel 8**).
3. Select a system image running **API 35** (Download if missing). Complete setup passes.
4. Click the green Play button next to the created target device to start the emulator.
5. Once fully booted, click the primary Run button in the top Android Studio toolbar (or press **Shift + F10**). The app will compile cleanly and install automatically.

### 4. Running Manually via Command Line
Open the integrated IDE Terminal tab at the base of the screen and execute standard Gradle wrapper tasks directly:

```powershell
# Clean build environment
.\gradlew clean

# Compile debug application passes
.\gradlew assembleDebug

# Install build output directly onto connected target environments
.\gradlew installDebug
```

### 5. Generating APK Build Outputs
To export binary installation files directly for project submissions or mobile deployment testing:
1. Navigate to **Build → Build Bundle(s) / APK(s) → Build APK(s)** in the top menu.
2. Gradle will execute assembly logic. Once complete, click the standard popup link labeled **"locate"** in the lower right notification corner.
3. The file explorer opens directly to `app\build\outputs\apk\debug\app-debug.apk` (Approximate file weight: **21.9 MB**). Transfer this file directly onto physical mobile devices for standard installation passes.

---

# 14. Common Errors and Fixes (Debugging Guide)

### 1. Room / KSP Build Failure (`Cannot find implementation for AppDatabase`)
- **Cause:** KSP compiler plugins failed to generate corresponding Room implementation classes due to mismatched internal library versions or unresolved data class constructor signatures.
- **Fix:** Open `libs.versions.toml` and ensure KSP versions precisely match the configured Kotlin toolchain version. Execute a clean pass via terminal: `.\gradlew clean` followed by `.\gradlew assembleDebug`.

### 2. Emulator Stuck Loading (`Can't find service: package`)
- **Cause:** Android's internal Package Manager Service crashed during improper emulator state snapshots, locking installation pipelines.
- **Fix:** Stop the running target device. Open **Device Manager**, click the vertical menu (⋮) options next to the device, and select **"Cold Boot Now"** or **"Wipe Data"**. Restart the emulator normally.

### 3. File Deletion Errors (`The process cannot access the file because it is being used`)
- **Cause:** Executing CLI process termination passes while Android Studio remains open causes internal background daemons to immediately respawn process instances, locking underlying AVD `.lock` files.
- **Fix:** Close active emulator views directly inside Android Studio GUI tooling. Rely on standard GUI stopping options inside Device Manager rather than external powershell process termination loops.

### 4. GPS Distance Returns Zero or Crashes
- **Cause:** Executing location checking logic inside emulators running incomplete location setups or missing active hardware coordinate tracking data.
- **Fix:** Open emulator settings panel options (three horizontal dots on external sidebars), select **Location**, and manually set custom single-point coordinate values before triggering application calculation passes. Alternatively, rely on the integrated codebase fallback path executing mock Bangalore coordinates automatically when hardware retrieval returns null.

---

# 15. Complete Viva Preparation (Questions & Answers)

Confidently answer invigilator questions during software engineering project evaluations using these precise developer explanations:

### Q1: What is the core architectural pattern used in this project?
> **Concise Answer:** Model-View-ViewModel (MVVM) integrated cleanly alongside Clean Architecture Repository patterns.  
> **Detailed Explanation:** Jetpack Compose acts as the UI View layer observing read-only state streams. ViewModels handle logic and execute database interactions asynchronously using Kotlin Coroutines. Repositories decouple data sources from UI components, and DAOs interact directly with the local Room database.  
> **Practical Example:** When a user searches for a sacred grove, the UI inputs text, updating a `StateFlow` inside `SearchViewModel`. The ViewModel queries `GroveRepository`, which executes a SQL `LIKE` query via `GroveDao`. The returned list flows back up reactively to update the search results list seamlessly.

### Q2: How does the application store data offline without network connectivity?
> **Concise Answer:** It uses Android Room Database seeded automatically from prepackaged local JSON asset files.  
> **Detailed Explanation:** Room provides an abstraction layer over standard SQLite. On the very first launch, a custom `RoomDatabase.Callback` triggers asynchronous file operations using GSON to read raw `groves_data.json` files directly from the APK's `assets/` folder, batch inserting complete records into local persistence tables. All runtime operations interact exclusively with these local tables.

### Q3: How is the Species Identification Scanner implemented? Does it use real machine learning?
> **Concise Answer:** No, it uses programmatic simulation logic to mimic machine learning classification.  
> **Detailed Explanation:** To keep the application highly responsive and lightweight for a project prototype, full machine learning models were bypassed. Tapping scan activates a coroutine timer that drives custom progress loading indicators over 2.4 seconds while programmatic logic randomly selects matching botanical records from local databases, calculating highly plausible simulated confidence score ranges (84% to 99%) dynamically.

### Q4: Explain how Jetpack Compose updates UI elements dynamically.
> **Concise Answer:** Through State Hoisting and UI Recomposition triggered by observed `StateFlow` updates.  
> **Detailed Explanation:** Compose UI components are standard functions rather than stateful objects. Views observe reactive StateFlow variables utilizing lifecycle-aware tools like `collectAsStateWithLifecycle()`. Whenever a ViewModel updates the underlying state value, the framework automatically identifies the specific downstream composable nodes reading that data and re-executes them, updating the visual output efficiently.

### Q5: How do you handle navigation between multiple application screens?
> **Concise Answer:** Using Navigation Compose managed via structured sealed classes.  
> **Detailed Explanation:** Routes are defined cleanly inside `Screen.kt` to enforce strong typing and prevent string matching typos. Transitions map explicit routes inside a global `NavHost` layout. Arguments (like target grove IDs) are passed safely by embedding them directly into URL-style route strings, which destination views parse directly from the incoming `NavBackStackEntry`.

### Q6: How does the app calculate the distance to nearby sacred groves?
> **Concise Answer:** Using device GPS coordinates processed through the mathematical Haversine formula.  
> **Detailed Explanation:** The app fetches the user's location using `FusedLocationProviderClient`. It then iterates through all saved database grove coordinates, calculating absolute spatial distance in meters using Android's built-in `Location.distanceBetween()` utility. Locations are sorted by proximity, and any site within 500 meters receives a specialized visual highlight.

---

# 16. Final Demonstration Guide (Step-by-Step Viva Script)

Use this complete step-by-step presentation script to demonstrate your application flawlessly to examiners:

### Step 1: Launch & Brand Overview
- **Action:** Open the app freshly on the emulator or physical device.
- **What to Say:** *"Good morning examiners. This is 'Devara-Kaadu – Sacred Grove Sentinel', a fully offline mobile platform built to preserve traditional ecological knowledge and document biodiversity across Karnataka's sacred groves. As the app launches, you see our custom animated theme initializing our underlying Room database."*

### Step 2: Dashboard Navigation
- **Action:** Dismiss onboarding passes. Arrive at `HomeScreen`. Scroll gently through the interface layout.
- **What to Say:** *"Our central dashboard follows Google's Material Design 3 guidelines. Notice our dynamic header metrics tracking total state-wide groves alongside our rotating 'Eco Fact of the Day' card. Let's open our primary directory."*
- **Action:** Click the **Grove Directory** card.

### Step 3: Database Search & Filtering
- **Action:** Tap filter chips (*Nagabana*, *Kans*). Type `"Sirsi"` into the search bar.
- **What to Say:** *"This directory reads directly from local Room tables using reactive StateFlow streams. As I type or tap filter tags, our ViewModel automatically updates our search query parameters, executing instant SQL queries to filter our data cleanly without network lag."*

### Step 4: Deep Dive Exploration
- **Action:** Tap the **Sirsi Bana** item card. Scroll down to show both informational sections.
- **What to Say:** *"Here on the detail view, notice how we separate traditional cultural heritage from modern environmental science. The top section documents local folklore and historical deity associations, while the bottom section details native species, water recharge metrics, and medicinal plants loaded dynamically using custom tags."*

### Step 5: Proximity Radar Demonstration
- **Action:** Navigate back to the home screen. Click the **Nearby Grove** card.
- **What to Say:** *"This module tracks physical distance parameters to surrounding sacred sites. Since we are inside an academic building, our location code gracefully triggers an explicit fallback path using central Bangalore coordinates, computing straight-line spatial distance parameters smoothly using Android's integrated Haversine math utilities."*

### Step 6: AI Scanner Simulation Execution
- **Action:** Navigate back to the home dashboard. Click the **Species Scan** card. Tap the primary **Scan Species** button.
- **What to Say:** *"To demonstrate our future roadmap for on-device species identification, we implemented a simulated AI module. Tapping scan drives an asynchronous coroutine simulation timer while localized logic maps our target data entity, generating highly plausible model confidence percentages dynamically."*

### Step 7: Gamified Stats Checking
- **Action:** Navigate back to the home dashboard. Click the **Guardian Mode** card.
- **What to Say:** *"Finally, to encourage real-world environmental participation, every successful scanning or reporting action updates our single-row persistent stats table. As you can see, our points have incremented, updating our visual progress indicator and unlocking our corresponding environmental achievement badges automatically."*

### Step 8: Architectural Summary
- **Action:** Return to the home screen. Conclude presentation passes.
- **What to Say:** *"In conclusion, by relying on an offline-first MVVM design powered by Jetpack Compose and Room Database, Devara-Kaadu successfully provides a robust, fast, and educational platform bridging cultural preservation with scientific documentation. Thank you. I am ready for your questions."*

---
**[ End of Handbook ]**
