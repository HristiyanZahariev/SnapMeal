/**
 * System configuration for Angular samples
 * Adjust as necessary for your application needs.
 */
(function (global) {
   var ngVer = '@2.0.0-rc.5'; // lock in the angular package version; do not let it float to current!
  var routerVer = '@3.0.0-rc.1'; // lock router version
  var formsVer = '@0.3.0'; // lock forms version
  var routerDeprecatedVer = '@2.0.0-rc.2'; // temporarily until we update all the guides
  
  var angular2ModalVer = '@2.0.3';
  var plugin = 'bootstrap'; // js-native / vex
  
  global.angular2ModalVer = angular2ModalVer;
  System.config({
    paths: {
      // paths serve as alias
      'npm:': 'node_modules/'
    },
    // map tells the System loader where to look for things
    map: {
      // our app is within the app folder
      app: 'app',

      // angular bundles
      '@angular/material': 'npm:@angular/material/bundles/material.umd.js',
      '@angular/animations': 'npm:@angular/animations/bundles/animations.umd.js',
      '@angular/animations/browser': 'npm:@angular/animations/bundles/animations-browser.umd.js',
      '@angular/platform-browser/animations': 'npm:@angular/platform-browser/bundles/platform-browser-animations.umd.js',
      '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
      '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
      '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
      '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
      '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
      '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
      '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
      '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',
      '@angular2-material/progress-circle': 'node_modules/@angular2-material/progress-circle',
      // other libraries
      'rxjs':                      'npm:rxjs',
      'angular-in-memory-web-api': 'npm:angular-in-memory-web-api/bundles/in-memory-web-api.umd.js',
      "angular2-jwt": "node_modules/angular2-jwt/angular2-jwt.js",
      "ngx-rating": "node_modules/ngx-rating",
      'ng2-tag-input' : 'node_modules/ng2-tag-input/dist/ng2-tag-input.bundle.js',
      'angular2-modal': 'node_modules/angular2-modal',
      'angular2-modal/plugins/bootstrap': 'node_modules/angular2-modal/bundles/angular2-modal.bootstrap.umd.js',
      'moment': 'node_modules/moment/moment.js',
      'ng2-bootstrap': 'node_modules/ng2-bootstrap/bundles/ngx-bootstrap.umd.js'
    },
    // packages tells the System loader how to load when no filename and/or no extension
    packages: {
      app: {
        main: './main.js',
        defaultExtension: 'js'
      },
      "angular2-jwt": {
          "defaultExtension": "js"
      },

      "ngx-rating": { 
          "main": "index.js", "defaultExtension": "js" 
      },

    'ng2-tag-input': {
        "defaultExtension" : "js"
    },
    "progress-circle": { 
      main: 'progress-circle.js', 
      defaultExtension: 'js' 
    },
    'angular2-modal': { 
      defaultExtension: 'js', 
      main: '/bundles/angular2-modal.umd.js' 
    },
    rxjs: {
      defaultExtension: 'js'
    }
  }
  });
})(this);
