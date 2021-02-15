# TextSurface

A little animation framework which could help you to show message in a nice looking way.

<p align="center"><img src="docs/demo.gif"/></p>

### Usage

1. Create [`TextSurface`](library/src/main/java/su/levenetc/android/textsurface/TextSurface.kt) instance or add it in your layout.
2. Create [`Text`](library/src/main/java/su/levenetc/android/textsurface/Text.kt) instancies with [`Text.Builder`](library/src/main/java/su/levenetc/android/textsurface/Text.kt) defining appearance of text and position:

  ```Kotlin
  Text textDaai = Text.Builder("Daai")
  		.setSize(64)
  		.setAlpha(0)
  		.setColor(Color.WHITE)
  		.setPosition(Align.SURFACE_CENTER).build();
  ```
  
3. Create animations and pass them to the [`TextSurface`](library/src/main/java/su/levenetc/android/textsurface/TextSurface.kt) instance:
  ```Kotlin
  textSurface.play(
  		Sequential(
  				Slide.showFrom(Side.TOP, textDaai, 500),
  				Delay.duration(500),
  				Alpha.hide(textDaai, 1500)
  		)
  );
  ```
  
See full sample [`here`](app/src/main/java/su/levenetc/android/textsurface/sample/animations/complex.kt).
  
### Adjusting animations

- To play animations sequentially use [`Sequential.java`](library/src/main/java/su/levenetc/android/textsurface/animations/sets/Sequential.kt)
- To play animations simultaneously use [`Parallel.java`](library/src/main/java/su/levenetc/android/textsurface/animations/sets/Parallel.kt)
- Animations/effects could be combined like this:

  ```Kotlin
  Parallel(Alpha.show(textA, 500), ChangeColor.to(textA, 500, Color.RED))
  ```
  i.e. alpha and color of text will be changed simultaneously in 500ms

### Adding your own animations/effects
There're two basic classes which you could extend to add custom animation:
- [`AbstractSurfaceAnimation.kt`](library/src/main/java/su/levenetc/android/textsurface/animations/AbstractSurfaceAnimation.kt) to animate basic parameters like `alpha`, `translation`, `scale` and others. (See [`Alpha.kt`](library/src/main/java/su/levenetc/android/textsurface/animations/colors/Alpha.kt) or [`ChangeColor.kt`](library/src/main/java/su/levenetc/android/textsurface/animations/colors/ChangeColor.kt))
- [`TextEffect.kt`](library/src/main/java/su/levenetc/android/textsurface/animations/effects/TextEffect.kt) interface which could be used for more complex animations. (See [`Rotate3D.kt`](library/src/main/java/su/levenetc/android/textsurface/animations/effects/Rotate3D.kt) or [`ShapeReveal.kt`](library/src/main/java/su/levenetc/android/textsurface/animations/effects/reveal/ShapeRevealAnimation.kt))

### Proguard configuration
The framework is based on standard android animation classes which uses `reflection` extensively. To avoid obfuscation you need to exclude classes of the framework:
```
-keep class su.levenetc.android.textsurface.** { *; }
```

### Download
```Groovy
repositories {
    maven { url "https://jitpack.io" }
}
//...
dependencies {
    //...
    compile 'com.github.elevenetc:textsurface:2.0.0'
}
```
### Licence
http://www.apache.org/licenses/LICENSE-2.0
