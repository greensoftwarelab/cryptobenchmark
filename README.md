# Benchmark for Android crypto primitives

This project started with a failed attempt to make an elegant library that would automatically infer the providers, primitives and respective algorithms (with respective modes, params, etc.) of the device where it was being used and be able to execute them automatically. Given the difficulty encountered in being able to generalize the high configurability of each of the algorithms that can be used and the many inconsistencies found at the level of the algorithms that the devices declared to have and actually implemented, it was decided to abandon this idea and do something practical and easier to implement (that resulted in a boilerplate code hard to mantain and understand). Therefore, the Android application code contains many  that tried to do this but are not being used.


##Requirements:
- python3
- Android SDK

## Installation


Install pyenv
```
$ curl https://pyenv.run | bash
$ exec $SHELL
```
Install virtual virtualenv environment  (via python-pip):

```
$ python -m pip install --user virtualenv
```

Download the exact same version of python used for dev (assuming 3.8.2):

```
$ pyenv install 3.8.2
```

Replicate locally the dev virtualenv

```
$ cd generic_crypto_bot
$ virtualenv -p ~/.pyenv/versions/3.8.2/bin/python3.8 env/
```

Activate the virtual environment
```
$ source venv/bin/activate
```

Install python packages
'''
$ pip install -r requirements.txt
'''

### Android SDK

Install Android Studio or Android SDK.

NOTE: Do not open the Android project with Android Studio. Recent versions of Android Studio do not support the gradle version of the project and breaks eventually suggest changes that break the build.

## Setup

The settings for each benchmark are declared in the gradle.properties file. There, several variables are declared that are then translated into class variables of the test classes to parameterize the executions:
- KEY_LEN - key size
- INPUT_SIZE - input size in bytes
- N_TIMES - times each cipher is executed in each unit test
- PROVIDER - Crypto provider
- WARM_UP_TIME - warm-up time before each unit test
- COOL_DOWN_TIME - cool-down time after each unit test
- MODE - cipher mode
- PADDING - cipher padding
- ALGORITHM - algorithm to be tested
- WITH_KEY_SPEC - whether cipher key is generated using the KeySpec class (required for some ciphers)

The parameters can be manipulated directly through command line arguments of the benchmark.py script, which in turn can be executed with the benchmark.py script that contains all possible parameterizations for the algorithms present on the tested devices.

## Execution

### Via run_benchmarks.py

1. Define the primitives and params to benchmark (e.g. testDigest) and place it in the end of the file

2. run 
'''
$ ./run_benchmarks.py
'''
### Via benchmark.py

1. Define the configs via cmdline and run the script (see python3 benchmark.py --help)

'''
python3 benchmark.py -b -i -u -c MeasureSymmetricEncryptDecryptTest -nt $N_TIMES --n_test_times $N_TEST_TIMES -s $SLEEP_TIME -is $is -kl $keylen
'''

## Workflow

With each execution of benchmark.py, the gradle.properties config file is changed according to the parameterization via cmdline provided. In order for the configs to be absorbed by the code, it is necessary to do a new build for the config variables to be transformed into variables of the BuildConfig class. Then the new apks are signed and installed on the device, and pyanadroid is then used to run the benchmarks a total of n_times. At the end of the process the apk is installed.

## AppCode

- each primitive is benchmarked through an instrumented test file, which contains a unit test for each pair (algorithm, provider).
- each unit test is annotated with HunterDebug so that its execution is traced (the start and end of the method are temporally delimited through its registration in the device logs).
- each primitive has a functional interface <Primitive>Operator.java that allows us to always use the same code to execute each algorithm (see static methods of the MeasureTest.java class) and that makes comparisons fairer.

## FAQ

1. Why is it an Android app and not an Android library, since it has no UI?
A: Because instrumentation plugins are not working for libraries

2. If all algorithms of each primitive are capable of being invoked by the same functional interface, why is it necessary to define and invoke u, unit test to invoke each algorithm with each provider (each unit test being named accordingly)?

A: For error prevention and instrumentation tool limitation. There are some specific cases in which execution and parameterization via the functional interface are not exactly uniform. Also for error prevention, since the instrumentation only records the name of the method in the system logs, if the name of the method did not identify what was executed, it would sometimes be difficult to correctly identify the config executed.