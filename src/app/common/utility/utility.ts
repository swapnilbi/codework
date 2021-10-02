export class HttpHelper {
    
     static getUrl(url: string, queryParams: {})  {    
        for (const [key, value] of Object.entries(queryParams)) {                 
            url = url.replace(new RegExp(':'+key, "g"), value as string);
        }
        return url;
     }

   }