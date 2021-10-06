# CountryInfo
Jetpack Compose application that let you search and see details of a country

<p align="center">
  <img width="300" alt="screen" src="https://user-images.githubusercontent.com/16867697/136271582-840bb55c-ed75-4f02-8a0e-be1eba5bf554.png">
  <img width="300" alt="screen" src="https://user-images.githubusercontent.com/16867697/136272243-57c885b6-814f-4cff-99a0-cd5248144778.png">
</p>

## Stack
 - **MVI**-like architecture
 - **Jetpack Compose** (also UI tests)
 - **Jetpack Navigation**
 - **Kotlin Flow**
 - **Apollo** client to communicate with the GraphQl APIs
 - **Koin** for dipendency injection
 - **Mockito && Google Truth && Turbine** for unit tests

## Structure
The application has one activity: `MainActivity`, from that activity the composition starts
The code is structured as follow:
 - **data** containes the service layer
 - **di** containes the koin's modules
 - **utils** containes some helpers
 - **ui**:
   - **theme** containes the theme configurations
   - **shared** containes shared composables
   - **screen** containes all the screens

Every screen is sctructured as follow:
 - **\<name\>Screen** the screen composable function
 - **\<name\>ViewModel** the viewModel of the screen
 - **components** composables used by the screen (also personalization of shared composables)

The navigation inside the app is managed in `CountryInfoNavHost`, used inside `MainActivity`
