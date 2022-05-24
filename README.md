# 🚧 WORDLE Clone 🚧

---

# 🌁 Screenshots

![Screenshot_20220524-135635.jpg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/28a61225-318d-4c44-8749-4f38d452d0ac/Screenshot_20220524-135635.jpg)

![Screenshot_20220524-135828.jpg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/836a333c-1190-4400-aaa7-4e26afc53a0e/Screenshot_20220524-135828.jpg)

# Intro

---

The Wordle clone app plays just like the traditional wordle. It even has a score and  Except there are a couple of features

### 1. The previous letters tab

This is a tab just above the soft keyboard to show the user the previous words used

### 2. The replay button

Instead of having to wait for the next day’s wordle, users can get access to another word instantly and play another game instantly

---

# Technical Tools Used

## Architecture

This app used an MVVM architecture for the game with liveData providing observable data for the view to use.

ViewBinding was also quite heavily used thoughout the project

Triggering view events from the viewModel was a little challenging but it was completed

### 🔗Helpful links for this component

[Handle ViewModel events](https://developer.android.com/topic/architecture/ui-layer/events#handle-viewmodel-events)

[Michael Ferguson: Sending ViewModel Events to the UI](https://proandroiddev.com/sending-view-model-events-to-the-ui-eef76bdd632c)

## ⌨️ Text Entry

There was a single editText for the game that text would be input in. Each letter in that editText would be put in each of the textView boxes. Filters had to be used to make sure that the editText only accepted alphabetical letters and 

When any non-submitted squared were pressed, the keyboard would enter text into the correct square.

I used Livedata to determine which squares, the text would be entered in

### 🔗Helpful links for this component

[Handling input method visibility](https://developer.android.com/training/keyboard-input/visibility#kotlin)

[Using a single onClick method for multiple buttons](https://stackoverflow.com/questions/7873480/android-one-onclick-method-for-multiple-buttons)

[Making EditText invisible, but editable](https://stackoverflow.com/questions/18159263/android-hidden-but-select-able-edittext)

[Restrict Input to Emoji](https://www.youtube.com/watch?v=LZppoEuviSw&t=354s&ab_channel=RahulPandey)

## 📹 Animation

This was definitely the hardest part of the app to get right, but this was important for making the app an engaging experience for the user.

### 🔗Helpful links for this component

[Android Docs: Property Animation Overview](https://developer.android.com/guide/topics/graphics/prop-animation)

[Multiple View Property Animators](https://stackoverflow.com/questions/46397561/multiple-viewpropertyanimators)

[Flipping and changing image in ImageView](https://stackoverflow.com/questions/37028694/flipping-and-changing-image-in-imageview)
