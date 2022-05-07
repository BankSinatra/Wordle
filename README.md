# WORDLE Clone


🚧The game is still being developed 🚧

This is the game as of May 5th 2022

# Intro

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

Edit texts were used to find views based on a model stating which letter should be editable. Basically, there are invisible editTexts in the game and each letter in the editText is sent to each square for the game.

When any non-submitted squared were pressed, the keyboard would enter text into the correct square.

### 🔗Helpful links for this component

[Android Docs: Handling input method visibility](https://developer.android.com/training/keyboard-input/visibility#kotlin)

[Making EditText non-editable](https://stackoverflow.com/questions/9470171/edittext-non-editable)

[Using a single onClick method for multiple buttons](https://stackoverflow.com/questions/7873480/android-one-onclick-method-for-multiple-buttons)

[Making EditText invisible, but editable](https://stackoverflow.com/questions/18159263/android-hidden-but-select-able-edittext)
