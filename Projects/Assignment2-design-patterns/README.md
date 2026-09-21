# Assignment 2: Creational Patterns

## Domain Theme
**Game World Kit**
- **Part A (Factory Method):** Spawning a single enemy type (`Goblin`, `Skeleton`, `Slime`) using subclasses of `EnemySpawner`.
- **Part B (Abstract Factory):** Constructing a fully consistent game environment family (`Enemy` + `Terrain` + `Soundtrack`) for biomes like `Forest` and `Ice`.

## How to Run

### Part A
```bash
javac -d bin src/factorymethod/*.java
java -cp bin factorymethod.Main

### Part B
```bash
javac -d bin src/abstractfactory/*.java src/abstractfactory/components/*.java src/abstractfactory/factories/*.java
java -cp bin abstractfactory.Main