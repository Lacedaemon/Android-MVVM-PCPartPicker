# Android-MVVM-PCPartPicker

## About

For my senior capstone project, I wanted to build an app that I could see myself using.  Naturally, a PCPartPicker app came to mind.

My higher-level goals for the client-side of the project are as follows:

* Learn Kotlin
* Gain a deeper understanding of the MVVM development architecture
* Write clean, efficient code

This app does not claim to adhere to MVVM one-hundred-and-one percent.  It does, however, strive to as best it can.

## Dependencies

|Item|Description|
|---|---|
|[ls](https://github.com/Lacedaemon/ls)|The backend for this project|

## Status

### To-Do

- [x] Implement accurate LineItem data
- [x] Implement total price where applicable
- [x] Implement editing of partlists
- [x] Implement FireStore operations
- [x] Implement local database
- [x] Save info entered into `AddEditPartListActivity`
- [x] Implement reusable `LineItemFragment` with a `RecyclerView` to be populated with x amount of `CardView`'s
- [x] Implement data binding
- [x] Implement `FloatingActionButton` to add a partlist
- [x] Implement preliminary 'Clear all' partlists option
- [x] Implement `EditText` for partlist title in `AddEditPartListActivity`
- [x] Implement back button in `AddEditPartListActivity`
- [x] Define part types
- [x] Define `requestCode`'s
- [x] Implement `Activity` 'PartListInfoActivity'
- [x] Implement `Activity` 'SelectPartActivity'

## Wishlist

* Icons underneath partlists
* Fancier partlist info screens
* Swiping of partlists in `MainActivity`
* Swipe to refresh
* Error handling
* More intelligence, e.g. part compatibility, efficient local/remote database querying, data handling between components