// jsonServer baseURL
// export const baseURL = 'http://localhost:3000';
export const BASE_URL =
  process.env.NODE_ENV === 'production'
    ? 'https://api.thankyou-for.com'
    : 'https://api-dev.thankyou-for.com';

// export const apiClient: AxiosInstance = axios.create({
//   baseURL,
//   headers: {
//     'Content-Type': 'application/json; charset=utf-8',
//   },
// });

// apiClient.interceptors.response.use((response) => response.data);

// export const authorizationHeader = (accessToken: string) => ({
//   headers: { Authorization: `bearer ${accessToken}` },
// });

// export const multipartAuthorizationHeader = (accessToken: string) => ({
//   headers: {
//     // 'Content-Type': 'multipart/form-data',
//     // 'Content-Type': 'application/x-www-form-urlencoded',
//     Authorization: `bearer ${accessToken}`,
//     // enctype: 'multipart/form-data',
//     // contentType: 'multipart/form-data',
//     // 'Access-Control-Allow-Origin': '*',
//   },
// });
