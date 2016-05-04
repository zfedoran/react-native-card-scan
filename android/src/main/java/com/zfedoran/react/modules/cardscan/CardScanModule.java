package com.zfedoran.react.modules.cardscan;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.card.payment.CardType;

public class CardScanModule extends ReactContextBaseJavaModule {
    int MY_SCAN_REQUEST_CODE = 42;
    Activity mActivity       = null;
    Promise mPromise         = null;

    public CardScanModule(ReactApplicationContext reactContext, Activity activity) {
        super(reactContext);
        this.mActivity = activity;
    }

    @Override
    public String getName() {
        return "CardScan";
    }

    @ReactMethod
    public void scanCard(Promise promise) {
        this.mPromise = promise;

        Intent scanIntent = new Intent(this.mActivity, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GRAY);

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

        // matches the theme of your application
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        this.mActivity.startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        String resultStr;
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            CardType cardType     = scanResult.getCardType();

            WritableMap map = Arguments.createMap();

            map.putString ("formattedCardNumber"        , scanResult.getFormattedCardNumber());
            map.putString ("lastFourDigitsOfCardNumber" , scanResult.getLastFourDigitsOfCardNumber());
            map.putString ("redactedCardNumber"         , scanResult.getRedactedCardNumber());
            map.putBoolean("isExpiryValid"              , scanResult.isExpiryValid());
            map.putString ("cardholderName"             , scanResult.cardholderName);
            map.putString ("cardNumber"                 , scanResult.cardholderName);
            map.putInt    ("expiryMonth"                , scanResult.expiryMonth);
            map.putInt    ("expiryYear"                 , scanResult.expiryYear);
            map.putString ("postalCode"                 , scanResult.postalCode);
            map.putInt    ("cvvLength"                  , cardType.cvvLength());
            map.putString ("cardType"                   , cardType.getDisplayName("en"));

            this.mPromise.resolve(map);
        }
    }
}
