# AffinidiID

* [About the app](#about-the-app)
   * [Description](#description)
   * [Gallery](#gallery)
* [Technical details](#technical-details)
   * [Android](#android)
   * [Used APIs](#used-apis)
   * [Schemas](#schemas)


## About the app
### Description
**AffinidiID** is a VC wallet app built for learning purpose. Created to dive into the technical aspects of **Self-Sovereign Identity** and **Verifiable Credentials** [concepts](hhttps://academy.affinidi.com/an-in-depth-exploration-of-self-sovereign-identity-and-verifiable-credentials-1a3eb2296004) using Affinidi open APIs. The second objective was to practice with Clean Architecture.

Actually, it works as native android frontend close to Affinidi [wallet application](https://wallet.affinidi.com/) but with the possibility to issue a test credential. The last version covers the following use cases:

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
- Delete credentials from Affinidi Wallet

"Edit username" feature was deleted due to API issue. The original name becomes lost immediately after OTP is sent (without confirmation).

### Gallery

#### Manage Affinidi profile
<p align="left">
<img src="https://user-images.githubusercontent.com/22574399/154146941-588fd474-39cf-4e0b-a338-dbf9c74f34d7.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/154146971-99e5fbb8-3909-48ab-8183-222def88154a.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/179850718-82da1e1a-4699-49a5-96f0-7f3b3e3a062f.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/179852439-f1fb09a6-a9ed-4b85-82da-0d21b9ece41f.png" width="20%"/>
</p>

#### Issue and store credentials
<p align="left">
<img src="https://user-images.githubusercontent.com/22574399/179851073-5ff5c149-5ec1-40af-a7c9-6556fb357cba.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/179851209-e017dab4-f636-4237-8b5f-28f4b38f692d.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/179851482-81473c21-c246-4e40-a2f2-9e1643dee103.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/154147804-3ce1f6ee-bcfd-4534-8667-4b4c879bc335.png" width="20%"/>
</p>

#### Share and verify credentials
<p align="left">
<img src="https://user-images.githubusercontent.com/22574399/179851690-c8165a0f-b5eb-4c21-97a6-379472992ae5.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/154148769-62395099-391c-41ff-b81b-524556eaac63.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/154148903-21b9d058-98d3-4e5c-bd45-97f152a48636.png" width="20%"/>
<img src="https://user-images.githubusercontent.com/22574399/179852667-171244a5-531e-4e6b-8e55-496103b34019.png" width="20%"/>
</p>

## Technical details

### Android

- Attempts to use clean architecture approach. Presentation / data / domain layers each in a separate module.
- Single activity approach and [Navigation component](https://developer.android.com/guide/navigation) (with SafeArgs) to navigate across fragments.
- Kotlin coroutines for asynchronous operations.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [View binding](https://developer.android.com/topic/libraries/view-binding) is used to interact with views within fragments and recyclerview adapters.
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate) lib to make work with view binding simplier (avoid boilerplate and safe calls)
- [Retrofit](https://github.com/square/retrofit) for making API requests (plus [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)).
- [Room](https://developer.android.com/jetpack/androidx/releases/room) for credentials caching.
- [Timber](https://github.com/JakeWharton/timber) for logging.
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) for rounded images, [Coil](https://github.com/coil-kt/coil) to load SVG images into ImageView.
- Unit tests (junit4)

### Used APIs
In this application the [Affinidi APIs](https://build.affinidi.com/docs/api) are used to create Affinidi user account and interact with the wallet.

Also [DiceBear Avatars API](https://avatars.dicebear.com/) is used to generate unique avatars depending on the user's DID.

### Schemas
<p align="left">
<img src="https://user-images.githubusercontent.com/22574399/154149516-e73f75e8-9ab7-46b9-8427-4d840f95215f.png" width="100%"/>
</p>
