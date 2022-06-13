export interface User {
    id : number,
	username : string,
	fullName : string,
	email : string,
	gender : string,
	roles : Array<string>
	active : boolean
  }