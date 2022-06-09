import Keycloak from 'keycloak-js'


// Setup Keycloak instance as needed
// Pass initialization options as required or leave blank to load from 'keycloakService.json'

// this is config, can also be file
// this client needs to be public
const keycloak = new Keycloak({
    url: "http://localhost:8090/auth",
    realm: "haushaltsapp",
    clientId: "react",
});

// const initKeycloak = (onAuthenticatedCallback) => {
//     console.log("initKeycloak is called")
//     keycloak.init({
//          onLoad: 'check-sso',
//          promiseType: 'native'
//
//
//         //silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
//         //pkceMethod: 'S256',
//     })
//         .then((authenticated) => {
//             if (!authenticated) {
//                 console.log("user is not authenticated..!");
//             }
//             onAuthenticatedCallback();
//         })
//         .catch(console.error);
// };
//
const doLogin = keycloak.login;
//
const doLogout = keycloak.logout;
//
const getToken = () => keycloak.token;
//
const isLoggedIn = () => !!keycloak.token;
//
const updateToken = (successCallback) =>
    keycloak.updateToken(5)
        .then(successCallback)
        .catch(doLogin);
//
const getUsername = () => keycloak.tokenParsed?.preferred_username;
//
const hasRole = (roles) => roles.some((role) => keycloak.hasRealmRole(role));
//
const KeycloakService = {
    //initKeycloak,
    doLogin,
    doLogout,
    isLoggedIn,
    getToken,
    updateToken,
    getUsername,
    hasRole,
};


export default keycloak
//export default KeycloakService

