# AffinidiID

> :warning: Since August 2023 Affinidi API stopped working. You can download [mock](https://github.com/alexandr7035/AffinidiID/releases/tag/v4.1-mock) build of the app.  
> Use `test@mail.com` login and `1234567Ab` password to get into the application.

 * [About the app](#about-the-app)
    * [Description](#description)
    * [Gallery](#gallery)
 * [Technical details](#technical-details)
    * [App architecture](#app-architecture)
    * [Android stack](#android-stack)
    * [Used APIs](#used-apis)

## About the app
### Description
**AffinidiID** is a VC wallet app built for learning purpose. Created to dive into the technical aspects of **Self-Sovereign Identity** and **Verifiable Credentials** [concepts](hhttps://academy.affinidi.com/an-in-depth-exploration-of-self-sovereign-identity-and-verifiable-credentials-1a3eb2296004) using Affinidi open APIs. The app was created to pactice with Clean Architecture.

Actually, it's a native android client close to Affinidi [wallet application](https://wallet.affinidi.com/) but with the possibility to issue a test credential. The last version covers the following use cases:

**User profile**
- Sign up
- Sign in
- View profile info (username, DID)
- Change password
- Logout
- Reset password

**Verifiable Credentials**
- Issue credentials
- Store credentials in Affinidi Wallet
- View credentials (list + details)
- Share credentials via QR code
- Verify credentials
- Delete credentials

### Gallery
Screenshots
<p src= align="left">

<img src="https://github.com/alexandr7035/AffinidiID/assets/22574399/3f838a3d-00b7-43c4-803d-2970f0928859" width="20%"/>
<img src="https://github.com/alexandr7035/AffinidiID/assets/22574399/f11c9273-6206-4558-bac9-50002638dee9" width="20%"/>
<img src="https://github.com/alexandr7035/AffinidiID/assets/22574399/2404424c-b103-4dd8-8d79-768114638f82" width="20%"/>
<img src="https://github.com/alexandr7035/AffinidiID/assets/22574399/fb44b11f-b986-46b5-b218-029338f24351" width="20%"/>

</p>

Demo GIF
<p align="left">
<img src="https://github.com/alexandr7035/AffinidiID/assets/22574399/0c858b44-e530-4379-bba5-1531e1bf6974" width="20%"/>
</p>

## Technical details

### App architecture

<p align="left">
<img src="https://user-images.githubusercontent.com/22574399/154149516-e73f75e8-9ab7-46b9-8427-4d840f95215f.png" width="100%"/>
</p>

### Android stack

- Attempts to learn clean architecture approach. Presentation / data / domain layers each in a separate module.
- Single activity and [Navigation component](https://developer.android.com/guide/navigation) (with SafeArgs) to navigate across fragments.
- Kotlin coroutines for asynchronous operations.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [View binding](https://developer.android.com/topic/libraries/view-binding) is used to interact with views within fragments and recyclerview adapters.
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate) lib to make work with view binding simplier (avoid boilerplate and safe calls)
- [Retrofit](https://github.com/square/retrofit) for making API requests (plus [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)).
- [Room](https://developer.android.com/jetpack/androidx/releases/room) for credentials caching.
- [Timber](https://github.com/JakeWharton/timber) for logging.
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) for rounded images, [Coil](https://github.com/coil-kt/coil) to load SVG images into ImageView.
- [ksprefs](https://github.com/cioccarellia/ksprefs) wrapper for SharedPreferences.
- [PermissionX](https://github.com/guolindev/PermissionX) lib for handling permissions.
- Unit tests (junit4)
- Kotlin for Gradle build scripts (build.gradle.kts)

### Used APIs
In this application the [Affinidi APIs](https://build.affinidi.com/docs/api) are used to create Affinidi user account and interact with the wallet.

Also [DiceBear Avatars API](https://avatars.dicebear.com/) is used to generate unique avatars depending on the user's DID.
