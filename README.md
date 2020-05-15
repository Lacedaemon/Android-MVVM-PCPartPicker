# Android-MVVM-PCPartPicker

## About

For my senior capstone project, I wanted to build an app that I could see myself using.  Naturally, a PCPartPicker app came to mind.

My higher-level goals for the client-side of the project are as follows:

* Learn Kotlin
* Gain a deeper understanding of the MVVM development architecture
* Write clean, efficient code

## Dependencies

|Item|Description|
|---|---|
|[ls](https://github.com/Lacedaemon/ls)|The backend for this project|

## Status

### Complete

- [x] Implement reusable `LineItemFragment` with a `RecyclerView` to be populated with x amount of `CardView`'s
- [x] Implement data binding
- [x] Implement `FloatingActionButton` to add a partlist
- [x] Implement preliminary 'Clear all' partlists option
- [x] Implement `EditText` for partlist title in `AddEditPartListActivity`
- [x] Implement back button in `AddEditPartListActivity`
- [x] Define part types
- [x] Define `requestCode`'s

### Ongoing

* Continual additions/refinements to `ViewModel`/`Adapter`

### To-Do

- [ ] Implement `Activity` 'PartListInfoActivity'
- [ ] Implement `Activity` 'SelectPartActivity'
- [ ] Implement accurate LineItem data
- [ ] Implement FireStore operations
- [ ] Implement local database
- [ ] Save info entered into `AddEditPartListActivity`

## Wishlist

* Icons underneath partlists
* Fancier partlist info screens
* Swiping of partlists in `MainActivity`