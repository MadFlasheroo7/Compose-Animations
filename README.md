![banner art](https://github.com/MadFlasheroo7/Compose-Animations/assets/57130085/109b9237-3fa5-48c5-80a3-8bddf256ead5)

> # Note 
> Wip migration to CMP (currently all screens migrated to support newer design system). 

# Compose Animations

Compose Animations is built with the primary goal to explore the capabilities of animation in
jetpack compose
and to learn and teach animation in a fun way.

if you like to read more about it check out the full
blog [here](https://blog.realogs.in/animating-jetpack-compose-ui/)

# Topics Covered [🚧 update topics]

- [Animate*AsState](app/src/main/java/com/example/animations/ui/screens/AnimateValueAsState.kt)
- [AnimatedTransition](app/src/main/java/com/example/animations/ui/screens/AnimatedTransition.kt)
- [AnimatedList](app/src/main/java/com/example/animations/ui/screens/AnimateList.kt)
- [AnimatedGesture](app/src/main/java/com/example/animations/ui/screens/AnimateGestures.kt)
- [BouncyRope](app/src/main/java/com/example/animations/ui/screens/BouncyRopes.kt)
- [InfiniteTransition](app/src/main/java/com/example/animations/ui/screens/InfiniteRotation.kt)
- [AnimateVisibility](app/src/main/java/com/example/animations/ui/screens/AnimateVisibility.kt)
- [AnimatedNavigation](app/src/main/java/com/example/animations/ui/screens/AniamtedNav.kt)

# Video [🚧 update video]

https://github.com/user-attachments/assets/300ffa7c-f1d6-446b-bb81-23fd1823f0f0

# TODO

With basic API usage established, this project's main objective is to integrate the latest Compose
APIs for animations. The aim is to simplify and accelerate the animation development process,
allowing us developers to design and preview animations without constant rebuilding. This means
customizing animations and generating their code at runtime for direct use, significantly reducing
iteration time(so yes will be migrating to CMP so we can directly copy paste animations on any platform).

- [ ] - CMP
- [ ] - animating canvas shapes
- [ ] - animate path
- [ ] - shape morphing
- [ ] - playground
- [ ] - shared transitions
- [ ] - improve list animations
- [ ] - M3 animations
- [ ] - more shaders
- [ ] - text animations
- [ ] - text field animation(insert and delete)
- [ ] - SVG animation
- [ ] - lottie animation
- [ ] - rive
- [ ] - 3D objects (Filament)
- [ ] - sensors based animations
- [ ] - multi module???
- [ ] - overhaul entire UI

# Multi-Module Project Structure (🚧 WIP - maybe)
```markdown
Compose-Animations[Animations]      # Root Project
├── app                             # Primary App Module
├── art                             # contains screenshots and videos of the library usage 
├── build-logic                     # contains conventional plugins to mitigate code duplication
│
├── default-apis                    # module for out of the box animation apis
├── community-built                 # module for community built animations
├── shaders                         # module for all shader related animations
│   ├── agsl                        # contains all agsl animations
│
├── filament                        # module for all filament related animations 
├── sensors                         # module for all sensors related animations
├── shapes                          # module for all shapes related animations
```

# Contribution

Compose Animation was built with the goal of public contributions so go find issues in the existing
code, improve over it
build new ones. Only "rule" per say if you are not the original author of the animation kindly
credit the original author.
and also read contribution guidelines to resolve build errors 😅.

[CONTRIBUTING.md](CONTRIBUTING.md)

> Note: This Project requires common modules to be built locally(for now) read CONTRIBUTING.md for
> more

# Credit

- Bouncy Rope is modified version of Rebecca Franks's animations you can check it
  out [here](https://github.com/riggaroo/compose-playtime)
- [sinasamaki](https://twitter.com/sinasamaki) for counter animation
- [gurupreet](https://x.com/_gurupreet) for compose cook book, check it
  out [here](https://github.com/Gurupreet/ComposeCookBook)
