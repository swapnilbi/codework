import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { LocalStorageService } from 'src/app/common/utility/local-storage';
import { UserProfile } from 'src/app/user/model/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

    jwtToken: string | null;    
    userProfile = new BehaviorSubject<UserProfile | null>(null);
    
    constructor(private localStorageService : LocalStorageService) {      
      this.jwtToken = this.getToken();          
      let userProfile =  this.localStorageService.get('user_profile');
      if(userProfile){
        this.userProfile.next(JSON.parse(userProfile));
      }
    }

    setToken(token: string) {
      if (token) {
        this.jwtToken = token;
        this.localStorageService.set('user_token',token);
      }
    }

    logout() {              
        this.localStorageService.remove('user_token');
        this.localStorageService.remove('user_profile');
        this.jwtToken = null;
        this.userProfile.next(null);
    }

    setUser(user: UserProfile) {
      if (user) {        
        this.userProfile.next(user);
        this.localStorageService.set('user_profile',JSON.stringify(user));
      }
    }

    getUser() {      
      return this.userProfile;
    }

    getToken() {
      if(!this.jwtToken){
         this.jwtToken = this.localStorageService.get('user_token');
      }
      return this.jwtToken;
    }

}
