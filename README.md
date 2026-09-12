# MagicSpawners

A fully rebranded, source-buildable MagicSpawners project reconstructed from the owner's compiled plugin configuration and behavior.

## Commands

- `/magicspawners help`
- `/magicspawners give <player> <entity> [amount]`
- `/magicspawners panel`
- `/magicspawners list`
- `/magicspawners info`
- `/magicspawners reload`

Aliases: `/ms`, `/magicspawner`, `/spawners`.

All permissions now use `magicspawners.*`. There are no `astral...` commands, permissions, plugin names, or license-key checks.

## Features

- Custom mob spawner items
- Spawner stacking
- Silk Touch breaking
- Virtual drop generation
- Loot storage GUI
- Collect all loot
- Vault sell-all
- Spawn egg type changing (permission-controlled)
- Natural spawner conversion
- Persistent `data.yml`
- Spawner panel
- GitHub Actions build workflow

## Build

GitHub: **Actions → Build MagicSpawners → Run workflow**.
The compiled JAR is uploaded as the `MagicSpawners` artifact.


## v2.2 performance changes
- Bounded generation work per scheduler pass (`performance.max-spawners-per-generation-run`).
- Skips unloaded chunks instead of loading them for background generation.
- O(1) location lookups remain in place.
- Data is marked dirty and only saved when changed.
- Async disk writes use a main-thread snapshot so Bukkit/spawner state is never read from the async writer.
- Expensive Location/block checks happen only after cheap world/chunk checks.
- Player distance checks use squared coordinates without repeatedly allocating distance calculations.

For large servers, start with `max-spawners-per-generation-run: 100`. Lower it if spawner processing shows in timings; raise it gradually if generation feels too slow.
