# WORDLE Clone

---

🚧The game is still being developed 🚧

This is the game as of May 11th 2022

# Intro

---

The Wordle clone app plays just like the traditional wordle. It even has a score and  Except there are a couple of features

### 1. The previous letters tab

This is a tab just above the soft keyboard to show the user the previous words used

### 2. The replay button

---

# Technical Tools Used

## Architecture

This app used an MVVM architecture for the game

ViewBinding was also heavily used thoughout the project

## ⌨️ Text Entry

There was a single editText for the game that text would be input in. Each letter in that editText would be put in each of the textView boxes. Filters had to be used to make sure that the editText only accepted alphabetical letters and 

When any non-submitted squared were pressed, the keyboard would enter text into the correct square.

I used Livedata to determine which squares, the text would be entered in

### 🔗Helpful links for this component

[[Android Docs]: Handling input method visibility](https://developer.android.com/training/keyboard-input/visibility#kotlin)

[[Stack Overflow]: Using a single onClick method for multiple buttons](https://stackoverflow.com/questions/7873480/android-one-onclick-method-for-multiple-buttons)

[[Stack Overflow]: Making EditText invisible, but editable](https://stackoverflow.com/questions/18159263/android-hidden-but-select-able-edittext)

[[YouTube]: Restrict Input to Emoji](https://www.youtube.com/watch?v=LZppoEuviSw&t=354s&ab_channel=RahulPandey)

## 📹 Animation

This was definitely the hardest part of the app to get right, but this was important for making the app an engaging experience for the user.

### 🔗Helpful links for this component

[[Android Docs]: Property Animation Overview](https://developer.android.com/guide/topics/graphics/prop-animation)

[[Stack Overflow]: Multiple View Property Animators](https://stackoverflow.com/questions/46397561/multiple-viewpropertyanimators)

[[Stack Overflow]: Flipping and changing image in ImageView](https://stackoverflow.com/questions/37028694/flipping-and-changing-image-in-imageview)
