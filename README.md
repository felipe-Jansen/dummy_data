# Partner tool API

## This is a tool that generates patients and their respectives events following entered rules.

It works in two ways:

- Informing preset
- Through random values

## Informing preset

```sh
./gradlew bootRun --args='en_US 10 2021-11-10 2021-11-15 preset=1'
```

| Parameter  | Meaning                          |
|------------|----------------------------------|
| en_US      | Language                         |
| 10         | Number of patients to be created |
| 2021-11-10 | Start date                       |
| 2021-11-15 | End date                         |
| preset=1   | Xml file that will be read       |

## Informing values on parameter

```sh
./gradlew bootRun --args='en_US 10 2021-11-10 2021-11-15 exercise=5 food=5 bolus=5&Type=FAST reading=5&Tag=MEAL_TAG_PRE_MEAL'
```

| Parameter                       | Meaning                                                         |
|---------------------------------|-----------------------------------------------------------------|
| en_US                           | Language                                                        |
| 10                              | Number of patients to be created                                |
| 2021-11-10                      | Start date                                                      |
| 2021-11-15                      | End date                                                        |
| exercise=5                      | Number of exercises to be created                               |
| food=5                          | Number of food to be created                                    |
| bolus=5&Type=FAST               | Number of bolus to be created, and bolus type that will be used |
| reading=5&Tag=MEAL_TAG_PRE_MEAL | Number of readings to be created and tag that will be used      |

However, if you want, you could leave the number of events empty, in this case 10 events will be generated, for instance:

```sh
exercise food
```
rather than
```sh
exercise=5 food=5
```
