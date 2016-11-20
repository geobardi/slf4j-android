# slf4j-android
slf4j 用の androidログ出力 adaptor です。  
ファイル名、行番号、メソッド名の出力が可能です。

### 使い方
build.gradle
```
repositories {
    maven { url 'http://geobardi.github.io/maven/' }
}
dependencies {
    compile 'jp.co.ois.android:slf4j-android:1.7.21'
}
```

使用クラス
```
public class MyApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(MyApplication.class);
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidLoggerConfig config = AndroidLoggerConfig.getInstance();
        config.setTag("hoge");
        if (BuildConfig.DEBUG) {
            config.setRootLevel(Log.DEBUG);
            config.setLevel("org.apache", Log.ERROR);
            config.setLevel("com.example", Log.VERBOSE);
        } else {
            config.setRootLevel(Log.INFO);
            config.setLevel("org.apache", Log.ERROR);
            config.setFormat("(%c) %m");
        }
        logger.info("version: {}, {}", BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME);
    }
}
```

