export interface Challenge {
    id : number,
    name : string,
    shortDescription : string,
    longDescription : string,
    type? : string,    
    languagesAllowed? : string[],
    startDate : Date,
    endDate : Date,
    isRegistered : boolean,
    bannerImage? : string,
    createdAt? : Date     
}
