# AffinidiID

* [Description](#description)
* [What is Affinidi Wallet](#what-is-affinidi-wallet)
* [Used APIs](#used-apis)
* [Implemented use cases](#implemented-use-cases)
* [Screenshots](#screenshots)
* [Technical stack](#technical-stack)

## Description
**AffinidiID** is a sample android app built with Affinidi API for learning purpose. Created to dive into the technical aspects of **Self-Sovereign Identity** and **Verifiable Credentials** [concepts](hhttps://academy.affinidi.com/an-in-depth-exploration-of-self-sovereign-identity-and-verifiable-credentials-1a3eb2296004).

## What is Affinidi Wallet
The Affinidi Wallet is a web-based responsive SSI [wallet application](https://wallet.affinidi.com/) for holders to request, store, share and manage their Veriiable Credentials.

## Used APIs
In this application the [Affinidi APIs](https://build.affinidi.com/docs/api) are used to create Affinidi user account and interact with the wallet.

Also [DiceBear Avatars API](https://avatars.dicebear.com/) is used to generate unique avatars depending on the user's DID.

## Implemented use cases
The first version (v0.1-alpha) covers the following use cases:
- Sign up
- Sign in
- View profile info (username, DID)
- Logout

## Screenshots

<p align="left">
<img src="doc/screenshot_sign_in.webp" width="23%"/>
<img src="doc/screenshot_sign_up.webp" width="23%"/>
<img src="doc/screenshot_profile.webp" width="23%"/>
<img src="doc/screenshot_logout.webp" width="23%"/>
</p>

## Technical stack
- Single activity approach and [Navigation component](https://developer.android.com/guide/navigation) (with SafeArgs) to navigate across fragments.
- Kotlin coroutines for asynchronous operations.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [View binding](https://developer.android.com/topic/libraries/view-binding) is used to interact with views within fragments and recyclerview adapters.
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate) lib to make work with view binding simplier (avoid boilerplate and safe calls)
- [Retrofit](https://github.com/square/retrofit) for making API requests (plus [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)). 
- [Timber](https://github.com/JakeWharton/timber) for logging.
- [Coil](https://github.com/coil-kt/coil) to load SVG images into ImageView.