export class HttpHelper {
    
     static getUrl(url: string, queryParams: {})  {    
        for (const [key, value] of Object.entries(queryParams)) {                 
            url = url.replace(new RegExp(':'+key, "g"), value as string);
        }
        return url;
     }
  
   }

export class Utility {
    
    static getHashCode(object: string)  {    
       var hash = 0;
       if(!object) {
          return hash;    
       }
       for (var i = 0; i < object.length; i++) {
           var character = object.charCodeAt(i);
           hash = ((hash<<5)-hash)+character;
           hash = hash & hash; // Convert to 32bit integer
       }       
       return hash;
    }

  }