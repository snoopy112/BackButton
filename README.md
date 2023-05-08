# BackButton
App adds system navigation button to Android 8.1 (Oreo).

Simple overlay service to provide Back button function by single tap, Home button function by double taps and Show Recent Apps function by long press.

To hide navigation bar use adb with your last value, e.g.:
```adb shell wm overscan 0,0,0,-70```
