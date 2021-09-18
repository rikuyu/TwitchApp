# Twitchゲーム動画が探せるアプリ
Twitch内のライブ動画、クリップ動画を探して視聴できるアプリです。

![t](https://user-images.githubusercontent.com/51118613/122635370-765d9280-d11e-11eb-9895-0f89c010d259.JPG)


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

## 備考
Twitch Developer アカウントを作成し、クライアントIDを取得しないとローカルで実行できません。

## 作者
[rikuyu](https://github.com/rikuyu)
