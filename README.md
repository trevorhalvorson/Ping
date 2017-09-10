# Ping

### Building

The APK is configured using [gradle project properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_properties_and_system_properties).

Available properties:
- `title`: Headline shown at the top of the app
- `copy`: Copy text
- `message`: The message the submitted phone number will receive
- `logo`: Public URL of an image
- `urlBase`: Base URL of where the `POST` request will be sent
- `urlPath`: URL path of where the `POST` request will be sent

Example:

`./gradlew assembleRelease task configure '-Ptitle="Sample Title"' '-Pcopy="Sample copy mesage"' '-Pmessage="Sample SMS message to send"' '-Plogo="https://sample.com/logo.png"' '-Purl_base="http://sample.com"' '-Purl_path="ping"'`

In this example a `POST` request with the body `{"message": "Sample SMS message to send"}` will be sent to `http://sample.com/ping`

The expected response is `{"success": true, "message": "error details if needed"}`

### Signing

Signing is configured in a `keystore.properties` file at the root of the project.
The required properties are:
- `storeFile`: Path to the `.jks` file
- `storePassword`
- `keyAlias`
- `keyPassword`
