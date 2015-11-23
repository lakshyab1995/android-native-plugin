GreedyGame Android Native Integration Guide
===================

This is a complete guide to integrate GreedyGame plugin within your native android game. 

You can download Android library project named, [greedy-game-agent](https://github.com/GreedyGame/android-native-plugin/releases).

| Original       | Dynamic Theme      |
| ------------- | ----------- |
| ![Sun Original Theme](screen-shots/sun.png?raw=true "Sun Original Theme" ) | ![Moon Dynamically changed Theme](screen-shots/moon.png?raw=true "Moon Dynamically changed Theme" ) |

#### Steps

* Link android library project to build path
* Put your gameprofile id in Android Xml values as string
    ```xml
    <resources>
	    <string name="greedy_game_profile">11111111</string>
    </resources>
    ```
### Documentations
#### GreedyGameAgent
**Class Overview**

Contains high-level classes encapsulating the overall GreedyGame ad flow and model.

**Public Constructors**
##### `GreedyGameAgent(Activity gameActivity, IAgentListner greedyListner)`

Constructs a new instance of GreedyGame handler.

----------

**Method**

##### `public void init(String []Units, FETCH_TYPE)`
Lookup for new native campaign from server. 

* GameId - Unique game profile id from panel.greedygame.com
* Units - List of relative path of assets used in games. 
    Also register unit id can be used
* FETCH_TYPE - it can be FETCH_TYPE.DOWNLOAD_BY_PATH or FETCH_TYPE.DOWNLOAD_BY_ID, to fetch units by relative path or u_id
    
##### `public String activeTheme()`
Return Theme id of currently active and running theme

##### `public String getActivePath()`
 Return path of folder, where assets of activeTheme is stored.

----

#### FloatAdLayout - For Floating ads
**Class Overview**

Extended FrameLayout used to display FloatAd creatives

**Public Constructors**
##### `FloatAdLayout(Context context)`

Constructs a new instance of FloatAdLayout.

----------

**Method**

##### `public void fetchHeadAd(String unit_id) throws AgentInitNotCalledException`
Fetch floating AdHead unit and add view to current context. 

* unit_id - Float unit id from panel.greedygame.com (e.g 'float-123')
* AgentInitNotCalledException - throws exception if called before calling GreedyGameAgent's init callback.
    

##### `public void fetchHeadAd(String unit_id, int diX, int diY) throws AgentInitNotCalledException`
Fetch floating AdHead unit and add view to current context. 

* unit_id - Float unit id from panel.greedygame.com (e.g 'float-123')
* diX, diY - Adjust dip cordinates in screen. 0,0 stands for top left.
* AgentInitNotCalledException - throws exception if called before calling GreedyGameAgent's init callback.
 
##### `public void removeAllHeadAd()`
Hide floating AdHead with unit-id
```java
/*** Fetching Float Ad unit ***/
floatAdlayout = new FloatAdLayout(context);
try {
    floatAdlayout.fetchHeadAd("float-363");
} catch (AgentInitNotCalledException e) {
    e.printStackTrace();
}
```

----
**Analytics Methods**
As the name suggest, put the following methods inside all branded activities.

##### `public void onResume()`
##### `public void onPause()`
##### `public void onResume(String activityName)`
##### `public void onPause(String activityName)`
##### `public void onCustomEvent(String eventName)`

For example
```java
@Override
protected void onResume(){
    super.onResume();
    ggAgent.onResume(activityName);
}
```
----
**Other Utilities Methods**

##### `public String get_verison()`
Return sdk version
    
##### `public void setDebug(boolean b)`
Set sdk into debug mode

---
#### interface IAgentListner
**Class Overview**
Is is used as callback listener argument for GreedyAgent class

**Methods**
 
##### `void onInit(OnINIT_EVENT response)`
     response value indicate
     * CAMPAIGN_NOT_AVAILABLE = using no campaign
     * CAMPAIGN_CACHED = campaign already cached
     * CAMPAIGN_FOUND = new campaign found to download

##### `void onDownload(boolean success)`
success true , If new branded contents are downloaded so that new scene can fetch assets from **getActivePath()**.


For example



```java
class GG_Listner implements IAgentListner{
    @Override
    public void onProgress(float progress) {
        //Use this for showing progress bar
        Log.i("GreedyGame Sample", "Downloaded = "+progress+"%");
    }
    
    @Override
    public void onDownload(boolean success) {
        if(success){
            isBranded = true;
        }
    }

    @Override
    public void onInit(OnINIT_EVENT response) {
        if(    response == OnINIT_EVENT.CAMPAIGN_CACHED || 
            response == OnINIT_EVENT.CAMPAIGN_FOUND){
            isBranded = true;
        }else{
            isBranded = false;
        }
    }
}
```

#### Manifest Requirement

```xml
<!-- GreedyGame SDK's requirements start -->
<activity
    android:name="com.greedygame.android.AdHeadActivity"
    android:theme="@style/Theme.Transparent" >
</activity>

<receiver 
    android:name="com.greedygame.android.GreedyRefReceiver" 
    android:enabled="true" 
    android:exported="true"
    android:priority="100">
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>
<!-- GreedyGame SDK's requirements end -->
```
---
### Some helper functions
To fetch drawable from android res

```java
public static Bitmap getBitmapByResid(GreedyGameAgent ggAgent, int resid){ 
    if(GreedyGameAgent.gameActivity == null){
        return null;
    }

    String resName = GreedyGameAgent.gameActivity.getApplicationContext().getResources().getResourceEntryName(resid);
    File file = new File(ggAgent.getActivePath() + "/" + resName+".png");
    if(file.exists()){
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    return null;
}
```

---
### For Hello Tutorial, goto [andorid-native-sample](andorid-native-sample)  

