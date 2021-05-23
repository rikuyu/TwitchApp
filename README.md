# Twitchゲーム動画視聴アプリ
Twitch内のライブ動画、クリップ動画を探して視聴できるアプリです。

![twitch](https://user-images.githubusercontent.com/51118613/117419121-2e602300-af57-11eb-9ccd-249a8a7e597b.jpg)

## 作成目的
Android Jetpack の学習を行うため。

## 使用技術等
- Retrofit
- Coroutines
- MVVM
- Room
- Fragment
- Navigation
- RecyclerView
- Glide

## 工夫点
- 操作方法が直感的に分かる UI
- Retrofit を用いた非同期通信において、Resouceクラスを利用し、エラーハンドリングを行った。[付録: ネットワーク ステータスの公開](https://developer.android.com/jetpack/guide?hl=ja)
- Fragment の状態が保存されるように CustomNavigator を実装した。
- 機内モード、Wifi未接続状態などのネットワーク接続状況を確認できるようにした。

## 作者
[rikuyu](https://github.com/rikuyu)
