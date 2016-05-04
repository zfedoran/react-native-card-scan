## react-native-card-scan
Wrapper around the card.io library for scanning payment cards.

http://card-io.github.io/card.io-Android-SDK/


### Manual install
#### iOS

> TODO (no iOS support yet)

#### Android
1. `npm install react-native-card-scan@https://github.com/zfedoran/react-native-card-scan.git --save`
2. Open up `android/app/src/main/java/[...]/MainActivity.java
  - Add `import com.zfedoran.react.modules.cardscan.*;` to the imports at the top of the file
  - Add `mCardScanPackage = new CardScanPackage(this);` to the top of the `getPackages()` method
  - Add `mCardScanPackage` to the list returned by the `getPackages()` method
  - Add the following result handler to your MainActivity.java file

  ```Java
    private CardScanPackage mCardScanPackage = null;

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCardScanPackage.handleActivityResult(requestCode, resultCode, data);
    }
  ```

3. Append the following lines to `android/settings.gradle`:

    ```
    include ':react-native-card-scan'
    project(':react-native-card-scan').projectDir = new File(rootProject.projectDir,   '../node_modules/react-native-card-scan/android')
    ```

4. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

    ```
    compile project(':react-native-card-scan')
    ```


## Usage

```javascript

import { NativeModules } from 'react-native';

NativeModules.CardScan.scanCard()
    .then(function(result) { 
            console.log('card-scan result: ', result);
    });

```
