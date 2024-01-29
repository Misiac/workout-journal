export const oktaConfig = {
    clientId: import.meta.env.VITE_OKTA_CLIENT_ID,
    issuer: import.meta.env.VITE_OKTA_ISSUER,
    redirectUri: import.meta.env.VITE_REDIRECT_URI,
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
    brandName: 'Workout Journal',
    features: {registration: true},
    colors: {
        brand: '#288dff'
    }
}