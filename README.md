# Task Tracker CLI

A command-line task tracker built in Kotlin. Tasks are stored locally as JSON and can be added, updated, listed, and managed by status.

**Project:** [roadmap.sh Task Tracker](https://roadmap.sh/projects/task-tracker)

## Requirements

- Java 21+
- Gradle (wrapper included)

## Build

```bash
./gradlew installDist
```

The executable is installed at `app/build/install/task-cli/bin/task-cli`.

## Usage

```bash
task-cli add <description>
task-cli list
task-cli list <status>
task-cli update <id> <description>
task-cli delete <id>
task-cli mark-in-progress <id>
task-cli mark-done <id>
```

### Examples

```bash
task-cli add "Buy groceries"
task-cli add finish project report

task-cli list
task-cli list todo
task-cli list in-progress
task-cli list done

task-cli update <id> "Buy groceries and cook dinner"
task-cli mark-in-progress <id>
task-cli mark-done <id>
task-cli delete <id>
```

### Task statuses

- `todo`
- `in-progress`
- `done`

## Storage

Tasks are persisted to:

```
~/.task-tracker-cli/tasks.json
```

## Development

Run tests:

```bash
./gradlew test
```

Run the CLI without installing:

```bash
./gradlew run --args="list"
```
