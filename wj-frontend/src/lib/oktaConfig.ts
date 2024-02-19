export const oktaConfig = {
    clientId: import.meta.env.VITE_OKTA_CLIENT_ID,
    issuer: import.meta.env.VITE_OKTA_ISSUER,
    redirectUri: import.meta.env.VITE_REDIRECT_URI,
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: false,
    brandName: 'Workout Journal',
    features: {registration: false},
    colors: {
        brand: '#288dff'
    }
}