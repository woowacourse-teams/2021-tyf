// jsonServer baseURL
// export const baseURL = 'http://localhost:3000';
export const BASE_URL =
  process.env.NODE_ENV === 'production'
    ? 'https://api.thankyou-for.com'
    : 'https://api-dev.thankyou-for.com';
