// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: 'https://api-mdr-lapr5.herokuapp.com',
  //apiMDV: 'http://localhost:5000',
  apiMDV: 'https://api-mdv-lapr5.azurewebsites.net',
  mapboxKey: 'pk.eyJ1Ijoidml0b3JjcmlzdGEiLCJhIjoiY2tpbWFieGV5MGQyZjMzcXM2YWJ0ZG9sZCJ9.Ye-zvGFLeAyGSrlBoFswZQ'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
