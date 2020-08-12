package com.livetv.android.utils.networking;

public class WebConfig {
    public static final String GetCodeURL = "https://android.superteve.com/ANXDRXOIXD/API/loginCode.php?request_code";
    public static final String LoginCodeURL = "https://android.superteve.com/ANXDRXOIXD/API/loginCode.php?dealer_code={CODE}";
    public static final String baseURL = "https://android.superteve.com/Sk7suqPN2S0zTNgdxT/";
    public static final String deleteMessageURL = "https://android.superteve.com/ANXDRXOIXD/API/tracking.php?username={USER}&seen";
    private static final String domain = "https://android.superteve.com";
    public static final String liveTVCategoriesURL = "https://android.superteve.com/ANXDRXOIXD/API/live_categorias.php";
    public static final String liveTVChannelsURL = "https://android.superteve.com/ANXDRXOIXD/API/live_canales.php?cve={CAT_ID}";
    public static final String loginURL = "https://android.superteve.com/ANXDRXOIXD/API/login.php?user={USER}&pass={PASS}&device_id={DEVICE_ID}&model={MODEL}&fw={FW}&country={COUNTRY}";
    public static final String removeUserURL = "https://android.superteve.com/ANXDRXOIXD/API/login.php?user={USER}&delete";
    public static final String setPassCodeURL = "https://android.superteve.com/ANXDRXOIXD/API/loginCode.php?password_code={CODE}&user={USER}&pass={PASS}";
    public static final String trackingURL = "https://android.superteve.com/ANXDRXOIXD/API/tracking.php?username={USER}&movie={MOVIE}";
    public static final String updateURL = "https://android.superteve.com/ANXDRXOIXD/API/upgrade_version.php?new_version";
    public static final String videoSearchURL = "https://android.superteve.com/ANXDRXOIXD/API/searchVideo.php?type={TYPE}&pattern={PATTERN}";
}
