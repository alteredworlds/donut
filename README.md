# CreditDonut

To build this project, please clone the repo and then 'Import project' into Android Studio 3

Code follows (a version of) the MVVM pattern, with Dagger2 used for Dependency Injection.

Note that this project does *not* use the Android Architecture Components ViewModel. 
Persistent state is handled by the model (Respository, uses HTTP cache) & the ViewModel is re-created on rotation.

Testing could be improved, but the architecture is intended to be highly testable.

A custom view is used to show the results. This was rather hurried & could be improved.

The ViewModel exposes `val inProgress: ObservableBoolean` which could be used to drive a progress indicator, this hasn't been hooked up to any UI component, but is unit tested.
