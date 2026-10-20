# Simple Voice Animations

NeoForge mod for Minecraft 1.21.1 that animates player heads while they speak using Simple Voice Chat.

## Features

- Pacman-style hinged head animation
- Vertical split-head animation
- Squish, scale, and nod animation styles
- Configurable animation intensity
- Configurable split height with a local skin preview
- Multiplayer preference synchronization
- Simple Voice Chat integration

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.264 or a compatible 1.21.1 release
- Simple Voice Chat for NeoForge 1.21.1

## Development

Build the mod:

```powershell
.\gradlew.bat build
```

Start the NeoForge development client:

```powershell
.\gradlew.bat runClient
```

The built JAR is written to `build/libs`.

In game, press `J` to open the animation settings. The settings screen allows selecting the animation style, changing intensity, and dragging the split-height marker over the player's skin.

## License

The original Fabric project is authored by Max Henkel. Check the upstream project's licensing terms before redistributing this port.
