import { api } from "boot/axios";

export function jwtLogin(data) {
  return api.post("/jwt/login", data);
}

export function jwtRefreshToken(data) {
  return api.post("/jwt/refresh-token", data);
}
