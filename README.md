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
            config.setFormat("(%F:%L) %M %m");
        } else {
            config.setRootLevel(Log.INFO);
            config.setLevel("org.apache", Log.ERROR);
            config.setFormat("(%c) %m");
        }
        logger.info("version: {}, {}", BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME);
    }
}
```

### フォーマット
setFormatでサポートしているものは以下になります。

| 記号 | 説明 |
| --- | --- |
| %F(※1) | ログ要求が発生したファイル名を出力する。 |
| %L(※1) | ログ要求が発生した行番号を出力する。 |
| %M(※1) | ログ要求が発生したメソッド名を出力する。 |
| %m | ロギングイベントで設定されたメッセージを出力する。 |
| %c | ログイベントのカテゴリー名(※2)を出力する。 |
| %% | %を出力する。 |

※1 これらを出力するときのパフォーマンスは良くありません。  
※2 カテゴリー名が xxx.yyy.ZZZ だった場合は ZZZ を出力する。

