ABOUT

Android project for a specialist doctor choice application with trust management and reputation.
The application is implemented using the P2P architecture for Social Mobile Computing Applications Digital Avatars.
  * The Digital Avatars framework includes an unique user profile for thrid party applications use and persistence needs.
  * It also includes a Complex Event Processing engine (Siddhi CEP) for distributed event driven scenarios available for the applications.

Trust and Reputation are handled using a Subjective Logic approach with direct functional and referral trust opinions.

REQUIREMENTS

* Prior to compile the application it is necessary to create a Firebase and a OneSignal Project to include the identifiers and server keys in the app/build.graddle.
* A google-services.json must be generated and included in the project.

USAGE

* Install the application and launch it. First it will require to log in with Google to obtain the user data.

* Once the user is logged, you can navigate to the app drawer and see the user data including the OneSignal ID, this ID is important for your friends to add yo as a contact in the app.

* Add your friends to request them a functional opinion on someone. You can use the stars rating to assign them a functional and referral trust opinion on them inside the scope of the application.
    The stars correspond to opinion values as follows:
        *     SBoolean(0, 1, 0, 0.5)
        **    SBoolean(0, 0.75, 0.25, 0.5)
        ***   SBoolean(0, 0, 1, 0.5)
        ****  SBoolean(0.75, 0, 0.25, 0.5)
        ***** SBoolean(1, 0, 0, 0.5)

* Now you are able to "play" the CEP engine and start the DoctorsApp with the button in Home layout.
