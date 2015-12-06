# TextSurface

A little animation framework which could help you to show message in a nice looking way.

<p align="center"><img src="docs/demo.gif"/></p>

### Usage

1. Create [`TextSurface`](library/src/main/java/su/levenetc/android/textsurface/TextSurface.java) instance or add it in your layout.
2. Create [`Text`](library/src/main/java/su/levenetc/android/textsurface/Text.java) instancies with [`TextBuilder`](library/src/main/java/su/levenetc/android/textsurface/TextBuilder.java) defining appearance of text and position:

  ```Java
  Text textDaai = TextBuilder
  		.create("Daai")
  		.setSize(64)
  		.setAlpha(0)
  		.setColor(Color.WHITE)
  		.setPosition(Align.SURFACE_CENTER).build();
  ```
  
3. Create animations and pass them to the [`TextSurface`](library/src/main/java/su/levenetc/android/textsurface/TextSurface.java) instance:
  ```Java
  textSurface.play(
  		new Sequential(
  				Slide.showFrom(Side.TOP, textDaai, 500),
  				Delay.duration(500),
  				Alpha.hide(textDaai, 1500)
  		)
  );
  ```
  
See full sample [`here`](app/src/main/java/su/levenetc/android/textsurface/sample/checks/CookieThumperSample.java).
  
### Adjusting animations

- To play animations sequentially use [`Sequential.java`](library/src/main/java/su/levenetc/android/textsurface/animations/Sequential.java)
- To play animations simultaneously use [`Parallel.java`](library/src/main/java/su/levenetc/android/textsurface/animations/Parallel.java)
- Animations/effects could be combined like this:

  ```Java
  new Parallel(Alpha.show(textA, 500), ChangeColor.to(textA, 500, Color.RED))
  ```
  i.e. alpha and color of text will be changed simultaneously in 500ms

### Adding your own animations/effects
There're two basic classes which you could extend to add custom animation:
- [`AbstractSurfaceAnimation.java`](library/src/main/java/su/levenetc/android/textsurface/animations/AbstractSurfaceAnimation.java) to animate basic parameters like `alpha`, `translation`, `scale` and others. (See [`Alpha.java`](library/src/main/java/su/levenetc/android/textsurface/animations/Alpha.java) or [`ChangeColor.java`](library/src/main/java/su/levenetc/android/textsurface/animations/ChangeColor.java))
- [`ITextEffect.java`](library/src/main/java/su/levenetc/android/textsurface/interfaces/ITextEffect.java) interface which could be used for more complex animations. (See [`Rotate3D.java`](library/src/main/java/su/levenetc/android/textsurface/animations/Rotate3D.java) or [`ShapeReveal.java`](library/src/main/java/su/levenetc/android/textsurface/animations/ShapeReveal.java))

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
    compile 'com.github.elevenetc:textsurface:0.9.1'
}
```
### Licence
http://www.apache.org/licenses/LICENSE-2.0
