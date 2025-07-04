import { api } from "boot/axios";

export function jwtLogin(data) {
  return api.post("/jwt/login", data);
}

export function jwtLoginCust(data) {
  console.log("jwtLoginCust data", data);
  return api.post("/jwt/login-cust", data);
}

export function jwtRefreshToken(data) {
  return api.post("/jwt/refresh-token", data);
}
