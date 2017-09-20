# Ping

### Building

The APK is configured using [gradle project properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_properties_and_system_properties).

Available properties:
- `titleText`: Headline shown at the top of the app
- `titleTextSize`
- `titleTextColor`
- `imageUrl`: Public URL of an image
- `imageWidth`
- `imageHeight`
- `imageScaleType`: How your image should be scaled. One of `CENTER`, `CENTER_CROP`, `CENTER_INSIDE`, `FIT_CENTER`, `FIT_XY`.
[See the Android developer docs](https://developer.android.com/reference/android/widget/ImageView.ScaleType.html).
- `copyText`: Copy text displayed below your image
- `copyTextSize`
- `copyTextColor`
- `sendButtonText`: Text for the button used to submit the users' phone number
- `sendButtonTextColor`
- `sendButtonBackgroundColor`
- `phoneInputTextColor`
- `phoneInputBackgroundColor`
- `numPadTextColor`
- `numPadBackgroundColor`
- `backgroundColor`
- `pin`: The PIN required to open the UI configuration screen
- `message`: The message the submitted phone number will receive
- `urlBase`: Base URL of where the `POST` request will be sent
- `urlPath`: URL path of where the `POST` request will be sent

Example:

`./gradlew assembleRelease task configure '-PtitleText="Sample Title"' '-PcopyText="Sample copy mesage"' '-Pmessage="Sample SMS message to send"' '-PimageUrl="https://sample.com/imageUrl.png"' '-PurlBase="http://sample.com"' '-PurlPath="ping/android"'`

In this example a `POST` request with the body `{"message": "Sample SMS message to send"}` will be sent to `http://sample.com/ping/android`

The expected response is `{"success": true, "message": "error details if needed"}`

### Signing

Signing is configured in a `keystore.properties` file at the root of the project.
The required properties are:
- `storeFile`: Path to the `.jks` file
- `storePassword`
- `keyAlias`
- `keyPassword`
