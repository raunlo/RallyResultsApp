import {Injectable} from "@angular/core";
import {environment} from "../environments/environment";

@Injectable()
export class AppConfig {
  private readonly TOKEN_KEY: string = "ACCESS_TOKEN_KEY"

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token)
  }

  getToken(): string {
    return localStorage.getItem(this.TOKEN_KEY)
  }

  deleteToken() {
    localStorage.removeItem(this.TOKEN_KEY)
  }

  getApiUrl(): string {
    return environment.api.url
  }

  getOAuthUrl(): string {
    return environment.oauth.url
  }

  getAuthClientSecret(): string {
    return environment.oauth.secret
  }
}
