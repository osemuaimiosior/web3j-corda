web3j Integration for Corda
===========================

## Introduction

web3j-corda is a lightweight client library for working with CorDapps and interacting with different nodes on Corda networks.

![web3j-corda Network](docs/images/web3j-corda.png)

## Features
- Connect to a Corda node
- Query the available CorDapps on the node
- Generate CorDapp client wrappers to interact with the deployed CorDapps
- Generate integration tests using Docker containers to verify CorDapps in real Corda networks
- Generate sample projects with a CorDapp contract, workflow and client modules

## Quick start

### Using the web3j-corda CLI

There is a web3j-corda binary that allows you to easily:

* Generate a template CorDapp project and the respective client wrappers
* Generate client wrappers for existing CorDapps

You can install web3j-corda by running the following command in your terminal:

```shell
curl -L https://getcorda.web3j.io | bash
```

### Create a template CorDapp project

To generate a template CorDapp project with the client wrappers: 

```shell
web3j-corda new --name=<corDappName> --output-dir=<output-dir> --package-name=<packageName>
```
### Create CorDapp client wrappers

To generate a web3j-corda client wrappers to existing CorDapps: 

```shell
web3j-corda generate (--url=<openApiUrl> | --cordapps-dir=<corDapps-dir>) --output-dir=<output-dir> --package-name=<packageName>
```

For further instructions, head to the [Examples](https://corda.web3j.io) section in the docs.

## Getting started

Add the relevant dependency to your project:

### Maven

```xml
<dependency>
    <groupId>org.web3j.corda</groupId>
    <artifactId>web3j-corda-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation 'org.web3j.corda:web3j-corda-core:0.1.0'
}
```

## Connect to a Corda Node

Initialise a connection, and create our Corda client service:

```kotlin
val service = CordaService("http://localhost:9000/") // URL exposed by Corda OpenAPI connector
val corda = Corda.build(service)
```

Obtain all the nodes connected to the current node:

```kotlin
val nodes = corda.api.network.nodes.findAll()
```

To query the list of all running CorDapps:

```kotlin
val corDapps = corda.api.corDapps.findAll()
```

To start a flow there are two option depending on whether you want to use a generated CorDapp wrapper
or just the Corda API directly:

### Using Corda API

This way works but is not type-safe, so can lead to runtime exceptions:

```kotlin
// Initialise the parameters of the flow 
val params = InitiatorParameters("$1", "O=PartyA, L=London, C=GB", false)

val issue = corda.api
    .corDapps.findById("obligation-cordapp")
    .flows.findById("issue-obligation")

// Type-conversions with potential runtime exception!
var signedTx = issue.start(parameters).convert<SignedTransaction>()
```

### Using the Web3j CorDapp wrapper

By using a wrapper generated by the `web3j-corda` Command-Line Tool, 
you can interact with your CorDapp in a type-safe way:
```kotlin
// Initialise the parameters of the flow 
val params = InitiatorParameters("$1", "O=PartyA, L=London, C=GB", false)

// Start the flow with typed parameters and response
val signedTx = Obligation.load(corda.service).flows.issue.start(parameters)
```

## Documentation

The project documentation portal is available [here](https://corda.web3j.io).

To build and serve the docs locally, you will need to have [Pipenv](https://pipenv.readthedocs.io/en/latest/) installed. Then simply run:

```shell
pipenv install
pipenv run mkdocs serve
```

## Support

[Web3 Labs](https://www.web3labs.com) maintain web3j-corda. If you'd like to get in touch, please [email us](mailto:hi@web3labs.com?subject=web3j-corda).
