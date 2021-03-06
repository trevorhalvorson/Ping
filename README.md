# Ping

### Configuring

UI configuration is implemented using [Remixer](https://github.com/material-foundation/material-remixer-android).

[Configuration demo](assets/ping-configure-demo.mp4)

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
- `messagingUrlBase`: Base URL of the messaging service where the `POST` request will be sent
- `messagingUrlPath`: URL path of the messaging service where the `POST` request will be sent
- `builderUrlBase`: URL base of the app builder service where the `POST` request will be sent
- `builderUrlPath`: URL path of the app builder service where the `POST` request will be sent
- `email`: The email address the built APK will be sent

Example:

`./gradlew assembleRelease -PtitleText='"Sample Title"' -PtitleTextSize=40F -PtitleTextColor=0xFF000000 -PimageUrl='"https://avatars1.githubusercontent.com/u/30177?v=4&s=200"' -PimageWidth=250F -PimageHeight=250F -PimageScaleType='"CENTER_CROP"' -PcopyText='"Lorem ipsum"' -PcopyTextSize=20F -PcopyTextColor=0xFF000000 -PsendButtonText='"SEND"' -PsendButtonTextColor=0xFF000000 -PsendButtonBackgroundColor=0xFFCCCCCC -PphoneInputTextColor=0xFF000000 -PphoneInputBackgroundColor=0xFFFFFFFF -PnumPadTextColor=0xFF000000 -PnumPadBackgroundColor=0xFFFFFFFF -PbackgroundColor=0xFFFFFFFF -Ppin='"0000"' -Pmessage='"Sample SMS message to send"' -PmessagingUrlBase='"http://messaging-sample.com"' -PmessagingUrlPath='"ping/android"' -PbuilderUrlBase='"http://builder-sample.com"' -PbuilderUrlPath='"build"' -Pemail='"email@test.com"'`

Result:

[Example configuration](assets/ping-example-configuration-screenshot.png)

When the user enters the phone number "0000000000" and clicks the send button,
a `POST` request will be made to `http://messaging-sample.com/ping/android` with the body
`{"phoneNumber": "0000000000", "message": "Sample SMS message to send"}`

The expected response is `{"success": true, "message": "MESSAGE_ID"}`

### Signing

Signing can be configured in a `keystore.properties` file at the root of the project or by setting
system environment variables.

The required properties are:
- `PING_STORE_FILE`: Path to the `.jks` file
- `PING_STORE_PASSWORD`
- `PING_KEY_ALIAS`
- `PING_KEY_PASSWORD`
